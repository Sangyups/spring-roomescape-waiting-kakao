package nextstep.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nextstep.schedule.domain.ReservationStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WaitingResponse {

    private Long id;
    private Long scheduleId;
    private Long memberId;
    private Long waitingCount;
    private String status;
}
