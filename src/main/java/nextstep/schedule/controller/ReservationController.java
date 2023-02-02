package nextstep.schedule.controller;

import auth.annotation.AuthRequired;
import lombok.RequiredArgsConstructor;
import nextstep.member.domain.Member;
import nextstep.schedule.domain.Reservation;
import nextstep.schedule.dto.ReservationRequest;
import nextstep.schedule.dto.ReservationResponse;
import nextstep.schedule.dto.WaitingResponse;
import nextstep.schedule.mapper.ReservationMapper;
import nextstep.schedule.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/reservations/mine")
    public ResponseEntity<List<ReservationResponse>> getMyReservations(@AuthRequired Member member) {
        List<Reservation> reservationList = reservationService.findAllAcceptedByMemberId(member.getId()).getReservationList();
        List<ReservationResponse> reservationResponses = reservationList.stream()
                .map(ReservationMapper.INSTANCE::domainToResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping("/reservation-waitings")
    public ResponseEntity<Void> createWaiting(@RequestBody ReservationRequest reservationRequest, @AuthRequired Member member) throws SQLIntegrityConstraintViolationException {
        reservationService.create(reservationRequest.getScheduleId(), member.getId());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/reservation-waitings/mine")
    public ResponseEntity<List<WaitingResponse>> getMyWaiting(@AuthRequired Member member) {
        List<Reservation> reservationList = reservationService.findAllWaitingByMemberId((member.getId())).getReservationList();
        List<WaitingResponse> waitingResponses = reservationList.stream()
                .map(ReservationMapper.INSTANCE::domainToWaitingResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(waitingResponses);
    }

    @DeleteMapping("/reservation-waitings/{id}")
    public ResponseEntity<Void> deleteMyWaiting(@PathVariable("id") Long id, @AuthRequired Member member) {
        reservationService.deleteMyWaiting(id, member.getId());

        return ResponseEntity.noContent().build();
    }

}
