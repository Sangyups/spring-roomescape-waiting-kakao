package nextstep.schedule.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public enum ReservationStatus {

    CANCELED,
    ACCEPTED,
    WAITING,
    ;

    private static final Map<String, ReservationStatus> statusMapper = new HashMap<>();

    static {
        Arrays.stream(ReservationStatus.values())
                .forEach(status -> statusMapper.put(status.name(), status));
    }

    public static ReservationStatus of(String status) {

        return Objects.requireNonNull(statusMapper.get(status));
    }
}
