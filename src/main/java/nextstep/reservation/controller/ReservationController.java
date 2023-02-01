package nextstep.reservation.controller;

import auth.annotation.AuthRequired;
import nextstep.member.domain.Member;
import nextstep.reservation.domain.Reservation;
import nextstep.reservation.dto.ReservationRequest;
import nextstep.reservation.dto.ReservationResponse;
import nextstep.reservation.mapper.ReservationMapper;
import nextstep.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    public final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity createReservation(@AuthRequired Member member, @RequestBody ReservationRequest reservationRequest) {
        Long id = reservationService.create(reservationRequest.getScheduleId(), member.getId());

        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity<List<ReservationResponse>> getReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> reservations = reservationService.findAllByThemeIdAndDate(themeId, date);

        return ResponseEntity.ok().body(ReservationMapper.INSTANCE.domainListToDtoList(reservations));
    }

    @GetMapping("/mine")
    public ResponseEntity<List<ReservationResponse>> getMyReservations(@AuthRequired Member member) {
        List<Reservation> reservations = reservationService.findAllByMemberId(member.getId());

        return ResponseEntity.ok(ReservationMapper.INSTANCE.domainListToDtoList(reservations));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@AuthRequired Member member, @PathVariable Long id) {
        reservationService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
