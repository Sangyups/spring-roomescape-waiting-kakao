package nextstep.waiting.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Waiting {

    private final Long id;
    private final Long scheduleId;
    private final Long waitingCount;
    private final Long memberId;
    private boolean isDeleted;

    public void delete() {
        this.isDeleted = true;
    }
}
