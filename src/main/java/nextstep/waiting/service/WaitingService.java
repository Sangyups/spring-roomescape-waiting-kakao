package nextstep.waiting.service;

import lombok.RequiredArgsConstructor;
import nextstep.waiting.domain.Waiting;
import nextstep.waiting.repository.WaitingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WaitingService {

    private final WaitingRepository waitingRepository;

    public Long create(Long scheduleId) {

        return 1L;
    }

    public List<Waiting> findByMemberId(Long memberId) {

        return waitingRepository.findByMemberId(memberId);
    }
}
