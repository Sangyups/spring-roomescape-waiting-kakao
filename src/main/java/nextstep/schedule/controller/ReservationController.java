package nextstep.schedule.controller;

import auth.annotation.AuthRequired;
import lombok.RequiredArgsConstructor;
import nextstep.member.domain.Member;
import nextstep.schedule.dto.ReservationRequest;
import nextstep.schedule.dto.ReservationResponse;
import nextstep.schedule.dto.WaitingResponse;
import nextstep.schedule.mapper.ReservationMapper;
import nextstep.schedule.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/reservations/mine")
    public ResponseEntity<List<ReservationResponse>> getMyReservations(@AuthRequired Member member) {
        List<ReservationResponse> reservationResponses = reservationService.findAllAcceptedByMemberId(member.getId()).stream()
                .map(ReservationMapper.INSTANCE::domainToResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(reservationResponses);
    }

    @PostMapping("/reservation-waitings")
    public ResponseEntity<Void> createWaiting(@RequestBody ReservationRequest reservationRequest, @AuthRequired Member member) {
        Long id = reservationService.create(reservationRequest.getScheduleId(), member.getId());

        return ResponseEntity.created(URI.create("/reservation-waitings/" + id)).build();
    }

    @GetMapping("/reservation-waitings/mine")
    public ResponseEntity<List<WaitingResponse>> getMyWaiting(@AuthRequired Member member) {
        List<WaitingResponse> waitingResponses = reservationService.findAllWaitingByMemberId(member.getId()).stream()
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
