package nextstep.waiting.dao;

import lombok.RequiredArgsConstructor;
import nextstep.waiting.entity.WaitingCounterEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WaitingCounterDao {
    public final JdbcTemplate jdbcTemplate;
    private final RowMapper<WaitingCounterEntity> rowMapper = (resultSet, rowNum) -> new WaitingCounterEntity(
            resultSet.getLong("schedule_id"),
            resultSet.getLong("count")
    );


}
