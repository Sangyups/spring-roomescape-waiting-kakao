package nextstep.schedule.domain;

import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Reservation {

    private Long id;
    private Long scheduleId;
    private Long memberId;
    private Long waitingCount;
    private ReservationStatus status;

    public static Reservation of(Long scheduleId, Long memberId, Long waitingCount, ReservationStatus status) {

        return Reservation.builder()
                .scheduleId(scheduleId)
                .memberId(memberId)
                .status(status)
                .waitingCount(waitingCount)
                .build()
                ;
    }

    public boolean assignedTo(Long memberId) {

        return Objects.equals(this.memberId, memberId);
    }
}
