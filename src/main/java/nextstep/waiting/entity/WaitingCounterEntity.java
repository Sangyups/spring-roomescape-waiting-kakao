package nextstep.waiting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WaitingCounterEntity {

    private Long scheduleId;
    private Long waitingCount;
}
