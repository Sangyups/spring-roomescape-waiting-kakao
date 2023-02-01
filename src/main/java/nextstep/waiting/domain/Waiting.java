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
    private boolean deleted;

    public static Waiting of(Long scheduleId, Long waitingCount, Long memberId) {

        return Waiting.builder()
                .scheduleId(scheduleId)
                .waitingCount(waitingCount)
                .memberId(memberId)
                .build();
    }

    public boolean assignedTo(Long memberId) {

        return Objects.equals(this.memberId, memberId);
    }
}
