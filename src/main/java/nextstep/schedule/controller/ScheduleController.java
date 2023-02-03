package nextstep.schedule.controller;

import java.net.URI;
import java.util.List;
import nextstep.schedule.domain.Schedule;
import nextstep.schedule.dto.ScheduleRequest;
import nextstep.schedule.dto.ScheduleResponse;
import nextstep.schedule.mapper.ScheduleMapper;
import nextstep.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Autowired
    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/admin/schedules")
    public ResponseEntity createSchedule(@RequestBody ScheduleRequest scheduleRequest) {
        Long id = scheduleService.create(scheduleRequest.getThemeId(), scheduleRequest.getDate(),
                scheduleRequest.getTime());

        return ResponseEntity.created(URI.create("/schedules/" + id)).build();
    }

    @GetMapping("/schedules")
    public ResponseEntity<List<ScheduleResponse>> showReservations(@RequestParam Long themeId,
            @RequestParam String date) {
        List<Schedule> schedules = scheduleService.findByThemeIdAndDate(themeId, date);

        return ResponseEntity.ok().body(ScheduleMapper.INSTANCE.domainListToDtoResponseList(schedules));
    }

    @DeleteMapping("/admin/schedules/{id}")
    public ResponseEntity deleteReservation(@PathVariable Long id) {
        scheduleService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
