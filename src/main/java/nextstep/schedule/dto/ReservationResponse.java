package nextstep.schedule.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.schedule.domain.ReservationStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponse {

    private Long id;
    private Long scheduleId;
    @JsonIgnore
    private Long memberId;
    private ReservationStatus status;
}
