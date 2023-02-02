package nextstep.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
