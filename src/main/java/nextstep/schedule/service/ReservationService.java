package nextstep.schedule.service;

import auth.exception.NotAuthorizedException;
import nextstep.global.exception.AlreadyReservedException;
import nextstep.global.exception.NotExistEntityException;
import nextstep.schedule.domain.Reservation;
import nextstep.schedule.domain.ReservationList;
import nextstep.schedule.domain.ReservationStatus;
import nextstep.schedule.repository.ReservationRepository;
import nextstep.schedule.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ScheduleRepository scheduleRepository) {
        this.reservationRepository = reservationRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public Long create(Long scheduleId, Long memberId) {
        final int TRY_OUT = 5;
        boolean exit = false;
        Long id = -1L;
        for (int i = 0; i < TRY_OUT && !exit; i++) {
            try {
                id = createTransaction(scheduleId, memberId);
            } catch (DuplicateKeyException ignored) {
            }
            exit = true;
        }
        return id;
    }

    @Transactional
    public Long createTransaction(Long scheduleId, Long memberId) throws DuplicateKeyException {
        scheduleRepository.findById(scheduleId)
                .orElseThrow(NotExistEntityException::new);

        ReservationList reservationList = reservationRepository.findByScheduleId(scheduleId);

        if (reservationList.hasMember(memberId)) {
            throw new AlreadyReservedException();
        }

        Long currentWaitingCount = 0L;
        ReservationStatus status = ReservationStatus.ACCEPTED;

        if (!reservationList.isEmpty()) {
            currentWaitingCount = increaseWaitingCount(scheduleId);
            status = ReservationStatus.WAITING;
        }

        Reservation newReservation = Reservation.of(scheduleId, memberId, currentWaitingCount, status);

        return reservationRepository.save(newReservation);
    }

    @Transactional
    public Long increaseWaitingCount(Long scheduleId) {
        reservationRepository.increaseWaitingCount(scheduleId);

        return reservationRepository.findWaitingCounterByScheduleId(scheduleId).getWaitingCount();
    }

    public ReservationList findAllAcceptedByMemberId(Long memberId) {

        return reservationRepository.findAllAcceptedByMemberId(memberId);
    }

    public ReservationList findAllWaitingByMemberId(Long memberId) {

        return reservationRepository.findAllWaitingByMemberId(memberId);
    }

    public void deleteMyWaiting(Long id, Long memberId) {
        Reservation reservation = reservationRepository.findById(id);

        if (!reservation.assignedTo(memberId)) {
            throw new NotAuthorizedException();
        }

        reservationRepository.deleteById(id);
    }
}
