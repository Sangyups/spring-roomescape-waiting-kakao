package nextstep.waiting.service;

import auth.exception.NotAuthorizedException;
import lombok.RequiredArgsConstructor;
import nextstep.global.exception.NotExistEntityException;
import nextstep.schedule.repository.ScheduleRepository;
import nextstep.waiting.domain.Waiting;
import nextstep.waiting.repository.WaitingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class WaitingService {

    private final WaitingRepository waitingRepository;
    private final ScheduleRepository scheduleRepository;

    // TODO:
    //  1. 예약이 없는 스케줄에 대해서 예약 대기 신청을 할 경우 예약이 된다,
    //  2. 이미 예약한 스케줄에는 다시 예약할 수 없다
    @Transactional
    public Long create(Long scheduleId, Long memberId) {
        scheduleRepository.findById(scheduleId)
                .orElseThrow(NotExistEntityException::new);

        Long nowWaitingCount = increaseWaitingCountAndCreateWaiting(scheduleId);

        return waitingRepository.create(Waiting.of(scheduleId, nowWaitingCount, memberId));
    }

    @Transactional
    public Long increaseWaitingCountAndCreateWaiting(Long scheduleId) {
        waitingRepository.increaseWaitingCount(scheduleId);

        return waitingRepository.findByScheduleId(scheduleId).getWaitingCount();
    }

    public List<Waiting> findByMemberId(Long memberId) {

        return waitingRepository.findByMemberId(memberId);
    }

    public void deleteMyWaiting(Long id, Long memberId) {
        Waiting waiting = waitingRepository.findById(id)
                .orElseThrow(NotExistEntityException::new);
        if (!waiting.assignedTo(memberId)) {
            throw new NotAuthorizedException();
        }

        waitingRepository.deleteById(id);
    }
}
