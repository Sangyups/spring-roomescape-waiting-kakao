package nextstep.schedule.repository;

import nextstep.schedule.domain.Reservation;
import nextstep.schedule.domain.ReservationList;
import nextstep.schedule.domain.WaitingCounter;
import org.springframework.dao.DuplicateKeyException;

public interface ReservationRepository {

    Long save(Reservation reservation) throws DuplicateKeyException;

    Reservation findById(Long id);

    ReservationList findByScheduleId(Long scheduleId);

    ReservationList findAllAcceptedByMemberId(Long memberId);

    ReservationList findAllWaitingByMemberId(Long memberId);

    int deleteById(Long id);

    void increaseWaitingCount(Long scheduleId);

    WaitingCounter findWaitingCounterByScheduleId(Long scheduleId);
}