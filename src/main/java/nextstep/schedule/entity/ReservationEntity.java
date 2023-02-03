package nextstep.schedule.entity;

import lombok.Getter;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

@Getter
public class ReservationEntity {

    private final Long id;
    private final AggregateReference<ScheduleEntity, Long> scheduleId;
    private final Long memberId;
    private final Long waitingCount;
    private final String status;

    private ReservationEntity(Long id, AggregateReference<ScheduleEntity, Long> scheduleId, Long memberId,
            Long waitingCount, String status) {
        this.id = id;
        this.scheduleId = scheduleId;
        this.memberId = memberId;
        this.waitingCount = waitingCount;
        this.status = status;
    }

    public ReservationEntity(Long id, Long scheduleId, Long memberId, Long waitingCount, String status) {
        this(id, AggregateReference.to(scheduleId), memberId, waitingCount, status);
    }

    public Long getScheduleId() {
        return scheduleId.getId();
    }
}
