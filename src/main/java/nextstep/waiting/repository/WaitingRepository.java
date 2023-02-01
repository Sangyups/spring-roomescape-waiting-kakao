package nextstep.waiting.repository;

import nextstep.waiting.domain.Waiting;

import java.util.List;

public interface WaitingRepository {

    List<Waiting> findByMemberId(Long memberId);
}
