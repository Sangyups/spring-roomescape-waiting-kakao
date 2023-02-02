package nextstep.schedule.mapper;

import nextstep.schedule.domain.WaitingCounter;
import nextstep.schedule.entity.WaitingCounterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WaitingCounterMapper {

    WaitingCounterMapper INSTANCE = Mappers.getMapper(WaitingCounterMapper.class);

    WaitingCounter entityToDomain(WaitingCounterEntity waitingCounterEntity);
}
