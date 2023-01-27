package nextstep.reservation;

import auth.annotation.AuthRequired;
import auth.domain.AbstractUser;
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
    public ResponseEntity createReservation(@AuthRequired AbstractUser user, @RequestBody ReservationRequest reservationRequest) {
        Long id = reservationService.create(MemberMapper.INSTANCE.abstractUserToDomain(user), reservationRequest);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping("/reservations")
    public ResponseEntity readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity deleteReservation(@AuthRequired AbstractUser user, @PathVariable Long id) {
        reservationService.deleteById(MemberMapper.INSTANCE.abstractUserToDomain(user), id);

        return ResponseEntity.noContent().build();
    }


}
