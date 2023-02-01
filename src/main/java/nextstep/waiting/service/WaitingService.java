package nextstep.waiting.service;

import auth.exception.NotAuthorizedException;
import lombok.RequiredArgsConstructor;
import nextstep.support.NotExistEntityException;
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

    public void deleteMyWaiting(Long memberId, Long id) {
        Waiting waiting = waitingRepository.findById(id)
                .orElseThrow(NotExistEntityException::new);
        if (!waiting.assignedTo(memberId)) {
            throw new NotAuthorizedException();
        }

        waitingRepository.deleteById(id);
    }
}
