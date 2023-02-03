package nextstep.schedule.repository;

import lombok.RequiredArgsConstructor;
import nextstep.schedule.dao.ReservationDao;
import nextstep.schedule.dao.WaitingCounterDao;
import nextstep.schedule.domain.Reservation;
import nextstep.schedule.domain.ReservationList;
import nextstep.schedule.domain.WaitingCounter;
import nextstep.schedule.mapper.ReservationMapper;
import nextstep.schedule.mapper.WaitingCounterMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepository {

    private final ReservationDao reservationDao;
    private final WaitingCounterDao waitingCounterDao;

    @Override
    public Long save(Reservation reservation) throws DuplicateKeyException {
        return reservationDao.save(ReservationMapper.INSTANCE.domainToEntity(reservation));
    }

    @Override
    public Reservation findById(Long id) {
        return ReservationMapper.INSTANCE.entityToDomain(reservationDao.findById(id));
    }

    @Override
    public ReservationList findByScheduleId(Long scheduleId) {
        return ReservationMapper.INSTANCE.entityListToReservationList(reservationDao.findByScheduleId(scheduleId));
    }

    @Override
    public ReservationList findAllAcceptedByMemberId(Long memberId) {
        return ReservationMapper.INSTANCE.entityListToReservationList(reservationDao.findAcceptedByMemberId(memberId));
    }

    @Override
    public ReservationList findAllWaitingByMemberId(Long memberId) {
        return ReservationMapper.INSTANCE.entityListToReservationList(reservationDao.findWaitingByMemberId(memberId));
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
