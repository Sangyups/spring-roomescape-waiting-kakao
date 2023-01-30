package nextstep.reservation;

import auth.annotation.AuthRequired;
import nextstep.member.domain.Member;
import nextstep.member.mapper.MemberMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    public final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity createReservation(@AuthRequired Member member, @RequestBody ReservationRequest reservationRequest) {
        Long id = reservationService.create(MemberMapper.INSTANCE.abstractUserToDomain(member), reservationRequest);
        return ResponseEntity.created(URI.create("/reservations/" + id)).build();
    }

    @GetMapping
    public ResponseEntity readReservations(@RequestParam Long themeId, @RequestParam String date) {
        List<Reservation> results = reservationService.findAllByThemeIdAndDate(themeId, date);
        return ResponseEntity.ok().body(results);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteReservation(@AuthRequired Member member, @PathVariable Long id) {
        reservationService.deleteById(MemberMapper.INSTANCE.abstractUserToDomain(member), id);

        return ResponseEntity.noContent().build();
    }


}
