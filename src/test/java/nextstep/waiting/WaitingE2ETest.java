package nextstep.waiting;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

import auth.dto.AccessTokenResponse;
import auth.dto.LoginRequest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import nextstep.AbstractE2ETest;
import nextstep.member.dto.MemberRequest;
import nextstep.schedule.dto.ScheduleRequest;
import nextstep.schedule.dto.WaitingRequest;
import nextstep.schedule.service.ReservationService;
import nextstep.theme.dto.ThemeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class WaitingE2ETest extends AbstractE2ETest {

    public static final String DATE = "2022-08-11";
    public static final String TIME = "13:00";
    private WaitingRequest waitingRequest;

    private Long themeId;

    @Autowired
    private ReservationService reservationService;

    @BeforeEach
    public void setUp() {
        super.setUp();
        ThemeRequest themeRequest = new ThemeRequest("테마이름", "테마설명", 22000);
        var themeResponse = RestAssured
                .given().log().all()
                .auth().oauth2(token.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(themeRequest)
                .when().post("/admin/themes")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        String[] themeLocation = themeResponse.header("Location").split("/");
        themeId = Long.parseLong(themeLocation[themeLocation.length - 1]);

        ScheduleRequest scheduleRequest = new ScheduleRequest(themeId, LocalDate.parse(DATE), LocalTime.parse(TIME));
        var scheduleResponse = RestAssured
                .given().log().all()
                .auth().oauth2(token.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(scheduleRequest)
                .when().post("/admin/schedules")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();
        String[] scheduleLocation = scheduleResponse.header("Location").split("/");
        Long scheduleId = Long.parseLong(scheduleLocation[scheduleLocation.length - 1]);

        waitingRequest = new WaitingRequest(1L);
    }

    @DisplayName("자신의 예약 대기열을 불러온다.")
    @Test
    void getMyWaiting() {
        createExampleWaiting();
        createExampleWaiting();

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(token.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservation-waitings/mine")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        assertThat(response.body()).isNotNull();
    }

    @DisplayName("예약 대기열을 생성한다.")
    @Test
    void createWaiting() {
        createExampleWaiting();

        AccessTokenResponse newToken = createMemberToken();

        RestAssured
                .given().log().all()
                .auth().oauth2(newToken.getAccessToken())
                .body(waitingRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservation-waitings")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        RestAssured
                .given().log().all()
                .auth().oauth2(newToken.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservation-waitings/mine")
                .then().log().all()
                .body("size()", is(1))
                .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("예약 대기열이 없다면 바로 예약을 생성한다.")
    @Test
    void createReservationIfNotWaiting() {
        RestAssured
                .given().log().all()
                .auth().oauth2(token.getAccessToken())
                .body(waitingRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservation-waitings")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value())
                .extract();

        RestAssured
                .given().log().all()
                .auth().oauth2(token.getAccessToken())
                .param("themeId", themeId)
                .param("date", DATE)
                .when().get("/reservations/mine")
                .then().log().all()
                .body("size()", is(1));
    }

    @DisplayName("자신의 예약 대기열이 아니면 삭제할 수 없다.")
    @Test
    void deleteWaiting() {
        createExampleWaiting();
        AccessTokenResponse newToken = createMemberToken();

        RestAssured
                .given().log().all()
                .auth().oauth2(newToken.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservation-waitings/" + 1)
                .then().log().all()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .extract();
    }

    @DisplayName("예약 대기열을 삭제한다.")
    @Test
    void deleteWaitingOfOthers() {
        createExampleWaiting();
        createExampleWaiting();

        RestAssured
                .given().log().all()
                .auth().oauth2(token.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().delete("/reservation-waitings/" + 1)
                .then().log().all()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .extract();
    }

    @DisplayName("대기번호 증가 동시성 테스트")
    @Test
    void increaseWaitCount() throws InterruptedException {
        final int ITERATION = 300;
        final int POOL_COUNT = 10;
        ExecutorService threadPool = Executors.newFixedThreadPool(POOL_COUNT);
        for (int i = 0; i < ITERATION; i++) {
            Long memberId = Integer.toUnsignedLong(i);
            threadPool.execute(() -> {
                reservationService.create(1L, memberId);
            });
        }

        threadPool.shutdown();
        threadPool.awaitTermination(30, TimeUnit.SECONDS);

        var response = RestAssured
                .given().log().all()
                .auth().oauth2(token.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/reservation-waitings/mine")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();
    }

    private AccessTokenResponse createMemberToken() {
        MemberRequest memberBody = new MemberRequest(USERNAME + 10, PASSWORD, NAME, PHONE, ROLE);
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberBody)
                .when().post("/members")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());

        LoginRequest tokenBody = new LoginRequest(USERNAME + 10, PASSWORD);
        var response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(tokenBody)
                .when().post("/login/token")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract();

        return response.as(AccessTokenResponse.class);
    }

    private ExtractableResponse<Response> createExampleWaiting() {
        return RestAssured
                .given().log().all()
                .auth().oauth2(token.getAccessToken())
                .body(waitingRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/reservation-waitings")
                .then().log().all()
                .extract();
    }
}
