package nextstep.schedule.mapper;

import java.util.List;
import nextstep.schedule.domain.Reservation;
import nextstep.schedule.domain.ReservationList;
import nextstep.schedule.domain.ReservationStatus;
import nextstep.schedule.dto.ReservationResponse;
import nextstep.schedule.dto.WaitingResponse;
import nextstep.schedule.entity.ReservationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReservationMapper {

    ReservationMapper INSTANCE = Mappers.getMapper(ReservationMapper.class);

    default ReservationResponse domainToResponseDto(Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        Long id = reservation.getId();
        Long scheduleId = reservation.getScheduleId();
        Long memberId = reservation.getMemberId();
        ReservationStatus status = reservation.getStatus();

        return new ReservationResponse(id, scheduleId, memberId, status);
    }

    default WaitingResponse domainToWaitingResponseDto(Reservation reservation) {
        if (reservation == null) {
            return null;
        }

        Long id = reservation.getId();
        Long scheduleId = reservation.getScheduleId();
        Long memberId = reservation.getMemberId();
        Long waitingCount = reservation.getWaitingCount();
        String status = reservation.getStatus().toString();

        return new WaitingResponse(id, scheduleId, memberId, waitingCount, status);
    }

    Reservation entityToDomain(ReservationEntity reservationEntity);

    List<Reservation> entityListToDomainList(List<ReservationEntity> reservationEntities);

    default ReservationList domainListToReservationList(List<Reservation> reservations) {
        return new ReservationList(reservations);
    }

    default ReservationList entityListToReservationList(List<ReservationEntity> reservationEntities) {
        return domainListToReservationList(entityListToDomainList(reservationEntities));
    }

    ReservationEntity domainToEntity(Reservation reservation);
}
