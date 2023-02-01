package nextstep.waiting.mapper;

import nextstep.waiting.domain.Waiting;
import nextstep.waiting.dto.WaitingResponse;
import nextstep.waiting.entity.WaitingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WaitingMapper {

    WaitingMapper INSTANCE = Mappers.getMapper(WaitingMapper.class);

    Waiting entityToDomain(WaitingEntity waitingEntity);

    default WaitingResponse domainToResponseDto(Waiting waiting) {
        if (waiting == null) {
            return null;
        }

        Long id = waiting.getId();
        Long scheduleId = waiting.getScheduleId();
        Long waitingCount = waiting.getWaitingCount();

        return new WaitingResponse(id, scheduleId, waitingCount);
    }
}
