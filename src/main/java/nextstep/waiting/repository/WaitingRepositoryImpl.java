package nextstep.waiting.repository;

import lombok.RequiredArgsConstructor;
import nextstep.waiting.dao.WaitingDao;
import nextstep.waiting.domain.Waiting;
import nextstep.waiting.mapper.WaitingMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class WaitingRepositoryImpl implements WaitingRepository {

    private final WaitingDao waitingDao;

    @Override
    public List<Waiting> findByMemberId(Long memberId) {

        return waitingDao.findByMemberId(memberId).stream()
                .map(WaitingMapper.INSTANCE::entityToDomain)
                .collect(Collectors.toList())
                ;
    }
}
