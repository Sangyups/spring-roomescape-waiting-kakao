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
            resultSet.getLong("waiting_count")
    );

    public WaitingCounterEntity findByScheduleId(Long scheduleId) {
        String sql = "SELECT schedule_id, waiting_count " +
                "FROM waiting_counter " +
                "WHERE schedule_id = ?;";

        return jdbcTemplate.queryForObject(sql, rowMapper, scheduleId);
    }

    public void increaseWaitingCount(Long scheduleId) {
        String sql = "UPDATE waiting_counter SET waiting_count = waiting_count + 1 WHERE schedule_id = ?; ";
        jdbcTemplate.update(sql, scheduleId);
    }
}
