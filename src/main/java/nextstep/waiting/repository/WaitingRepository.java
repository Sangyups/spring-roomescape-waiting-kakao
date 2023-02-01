package nextstep.waiting.repository;

import nextstep.waiting.domain.Waiting;
import nextstep.waiting.domain.WaitingCounter;

import java.util.List;
import java.util.Optional;

public interface WaitingRepository {

    Long create(Waiting waiting);

    Optional<Waiting> findById(Long id);

    List<Waiting> findByMemberId(Long memberId);

    int deleteById(Long id);

    void increaseWaitingCount(Long scheduleId);

    WaitingCounter findByScheduleId(Long scheduleId);
}
