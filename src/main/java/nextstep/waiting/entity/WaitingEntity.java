package nextstep.waiting.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WaitingEntity {

    private final Long id;
    private final Long memberId;
    private final Long scheduleId;
    private final Long waitingCount;
    private final boolean isDeleted;
}
