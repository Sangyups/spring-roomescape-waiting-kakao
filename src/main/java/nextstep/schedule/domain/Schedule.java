package nextstep.schedule.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Schedule {

    private Long id;
    private Long themeId;
    private LocalDate date;
    private LocalTime time;

    public static Schedule of(Long themeId, LocalDate date, LocalTime time) {

        return new Schedule(null, themeId, date, time);
    }
}
