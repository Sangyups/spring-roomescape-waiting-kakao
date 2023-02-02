package nextstep.schedule.repository;

import nextstep.schedule.domain.Reservation;
import nextstep.schedule.domain.WaitingCounter;

import java.util.List;

public interface ReservationRepository {

    Long save(Reservation reservation);

    Reservation findById(Long id);

    List<Reservation> findByScheduleId(Long scheduleId);

    List<Reservation> findAllAcceptedByMemberId(Long memberId);

    List<Reservation> findAllWaitingByMemberId(Long memberId);

    int deleteById(Long id);

    void increaseWaitingCount(Long scheduleId);

    WaitingCounter findWaitingCounterByScheduleId(Long scheduleId);
}