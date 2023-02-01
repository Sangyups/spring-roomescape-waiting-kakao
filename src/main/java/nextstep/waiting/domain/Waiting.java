package nextstep.waiting.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
public class Waiting {

    private final Long id;
    private final Long scheduleId;
    private final Long waitingCount;
    private final Long memberId;
    private boolean isDeleted;

    public boolean assignedTo(Long memberId) {

        return Objects.equals(this.memberId, memberId);
    }
}
