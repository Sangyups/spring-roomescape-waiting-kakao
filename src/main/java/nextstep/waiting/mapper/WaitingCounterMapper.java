package nextstep.waiting.mapper;

import nextstep.waiting.domain.WaitingCounter;
import nextstep.waiting.entity.WaitingCounterEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WaitingCounterMapper {

    WaitingCounterMapper INSTANCE = Mappers.getMapper(WaitingCounterMapper.class);

    WaitingCounter entityToDomain(WaitingCounterEntity waitingCounterEntity);
}
