package nextstep.schedule.service;


import auth.exception.NotAuthorizedException;
import nextstep.global.exception.NotExistEntityException;
import nextstep.schedule.domain.Reservation;
import nextstep.schedule.domain.ReservationStatus;
import nextstep.schedule.domain.Schedule;
import nextstep.schedule.repository.ReservationRepository;
import nextstep.schedule.repository.ScheduleRepository;
import nextstep.theme.repository.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ScheduleRepository scheduleRepository;
    private final ThemeRepository themeRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, ScheduleRepository scheduleRepository, ThemeRepository themeRepository) {
        this.reservationRepository = reservationRepository;
        this.scheduleRepository = scheduleRepository;
        this.themeRepository = themeRepository;
    }

    // TODO:
    //  1. 예약이 없는 스케줄에 대해서 예약 대기 신청을 할 경우 예약이 된다,
    //  2. 이미 예약한 스케줄에는 다시 예약할 수 없다
    @Transactional
    public Long create(Long scheduleId, Long memberId) {
        scheduleRepository.findById(scheduleId)
                .orElseThrow(NotExistEntityException::new);

        Long currentWaitingCount = increaseWaitingCount(scheduleId);

        Reservation newReservation = Reservation.of(scheduleId, memberId, currentWaitingCount, ReservationStatus.WAITING);

        return reservationRepository.save(newReservation);
    }

    @Transactional
    public Long increaseWaitingCount(Long scheduleId) {
        reservationRepository.increaseWaitingCount(scheduleId);

        return reservationRepository.findWaitingCounterByScheduleId(scheduleId).getWaitingCount();
    }

    public List<Reservation> findAllByThemeIdAndDate(Long themeId, String date) {
        themeRepository.findById(themeId)
                .orElseThrow(NotExistEntityException::new);

        List<Schedule> scheduleList = scheduleRepository.findByThemeIdAndDate(themeId, date);

        return scheduleList.stream()
                .map(schedule -> reservationRepository.findByScheduleId(schedule.getId()))
                .flatMap(List::stream)
                .sorted(Comparator.comparingLong(Reservation::getId))
                .collect(Collectors.toList())
                ;
    }

    public List<Reservation> findAllAcceptedByMemberId(Long memberId) {

        return reservationRepository.findAllAcceptedByMemberId(memberId);
    }


    public List<Reservation> findAllWaitingByMemberId(Long memberId) {

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