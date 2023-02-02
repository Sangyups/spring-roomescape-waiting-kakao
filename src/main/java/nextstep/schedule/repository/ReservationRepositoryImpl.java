package nextstep.schedule.repository;

import lombok.RequiredArgsConstructor;
import nextstep.schedule.dao.ReservationDao;
import nextstep.schedule.dao.WaitingCounterDao;
import nextstep.schedule.domain.Reservation;
import nextstep.schedule.domain.WaitingCounter;
import nextstep.schedule.mapper.ReservationMapper;
import nextstep.schedule.mapper.WaitingCounterMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationDao reservationDao;
    private final WaitingCounterDao waitingCounterDao;

    @Override
    public Long save(Reservation reservation) {

        return reservationDao.save(ReservationMapper.INSTANCE.domainToEntity(reservation));
    }

    @Override
    public Reservation findById(Long id) {
        return ReservationMapper.INSTANCE.entityToDomain(reservationDao.findById(id));
    }

    @Override
    public List<Reservation> findByScheduleId(Long scheduleId) {

        return ReservationMapper.INSTANCE.entityListToDomainList(reservationDao.findByScheduleId(scheduleId));
    }

    @Override
    public List<Reservation> findAllAcceptedByMemberId(Long memberId) {
        return ReservationMapper.INSTANCE.entityListToDomainList(reservationDao.findAcceptedByMemberId(memberId));
    }

    @Override
    public List<Reservation> findAllWaitingByMemberId(Long memberId) {
        return ReservationMapper.INSTANCE.entityListToDomainList(reservationDao.findWaitingByMemberId(memberId));
    }

    @Override
    public int deleteById(Long id) {
        return reservationDao.deleteById(id);
    }


    @Override
    public void increaseWaitingCount(Long scheduleId) {
        waitingCounterDao.increaseWaitingCount(scheduleId);
    }

    @Override
    public WaitingCounter findWaitingCounterByScheduleId(Long scheduleId) {

        return WaitingCounterMapper.INSTANCE.entityToDomain(waitingCounterDao.findByScheduleId(scheduleId));
    }


}
