package nextstep.schedule.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequest {

    private Long themeId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @DateTimeFormat(pattern = "kk:mm:ss")
    private LocalTime time;
}
