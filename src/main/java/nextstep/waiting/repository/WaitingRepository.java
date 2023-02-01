package nextstep.waiting.repository;

import nextstep.waiting.domain.Waiting;

import java.util.List;
import java.util.Optional;

public interface WaitingRepository {

    Optional<Waiting> findById(Long id);

    List<Waiting> findByMemberId(Long memberId);

    int deleteById(Long id);
}
