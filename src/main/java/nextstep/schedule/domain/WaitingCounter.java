package nextstep.schedule.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WaitingCounter {

    private Long waitingCount;
    private Long scheduleId;
}
