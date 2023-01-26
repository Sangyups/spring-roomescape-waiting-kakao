package nextstep.reservation;

import auth.annotation.AuthRequired;
import auth.domain.UserDetails;
import nextstep.member.mapper.MemberMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class ReservationController {

    public final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping("/reservations")
    public ResponseEntity createReservation(@AuthRequired UserDetails userDetails, @RequestBody ReservationRequest reservationRequest) {
        Long id = reservationService.create(MemberMapper.INSTANCE.userDetailsToDomain(userDetails), reservationRequest);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/reservations")
    public ResponseEntity readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity deleteReservation(@AuthRequired UserDetails userDetails, @PathVariable Long id) {
        reservationService.deleteById(MemberMapper.INSTANCE.userDetailsToDomain(userDetails), id);

        return ResponseEntity.noContent().build();
    }


}
