package nextstep.waiting.repository;

import lombok.RequiredArgsConstructor;
import nextstep.waiting.dao.WaitingCounterDao;
import nextstep.waiting.dao.WaitingDao;
import nextstep.waiting.domain.Waiting;
import nextstep.waiting.domain.WaitingCounter;
import nextstep.waiting.mapper.WaitingCounterMapper;
import nextstep.waiting.mapper.WaitingMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class WaitingRepositoryImpl implements WaitingRepository {

    private final WaitingDao waitingDao;
    private final WaitingCounterDao waitingCounterDao;

    @Override
    public Long create(Waiting waiting) {

        return waitingDao.save(WaitingMapper.INSTANCE.domainToEntity(waiting));
    }

    @Override
    public Optional<Waiting> findById(Long id) {

        return Optional.ofNullable(waitingDao.findById(id))
                .map(WaitingMapper.INSTANCE::entityToDomain)
                ;
    }

    @Override
    public List<Waiting> findByMemberId(Long memberId) {

        return waitingDao.findByMemberId(memberId).stream()
                .map(WaitingMapper.INSTANCE::entityToDomain)
                .collect(Collectors.toList())
                ;
    }

    @Override
    public int deleteById(Long id) {

        return waitingDao.deleteById(id);
    }

    @Override
    public void increaseWaitingCount(Long scheduleId) {
        waitingCounterDao.increaseWaitingCount(scheduleId);
    }

    @Override
    public WaitingCounter findByScheduleId(Long scheduleId) {

        return WaitingCounterMapper.INSTANCE.entityToDomain(waitingCounterDao.findByScheduleId(scheduleId));
    }
}
