package nextstep.schedule.domain;

import java.util.ArrayList;
import java.util.List;

public class ReservationList {

    private final List<Reservation> reservationList;

    public ReservationList(List<Reservation> reservationList) {
        this.reservationList = new ArrayList<>(reservationList);
    }

    public List<Reservation> getReservationList() {
        return new ArrayList<>(this.reservationList);
    }

    public boolean hasMember(Long memberId) {
        return this.reservationList.stream()
                .anyMatch(reservation -> reservation.assignedTo(memberId))
                ;
    }

    public boolean isEmpty() {
        return this.reservationList.isEmpty();
    }
}
