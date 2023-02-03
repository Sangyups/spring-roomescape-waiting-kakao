package nextstep.schedule.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import nextstep.schedule.entity.WaitingEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WaitingDao {

    public final JdbcTemplate jdbcTemplate;

    private final RowMapper<WaitingEntity> rowMapper = (resultSet, rowNum) -> new WaitingEntity(
            resultSet.getLong("reservation_waiting.id"),
            resultSet.getLong("reservation_waiting.member_id"),
            resultSet.getLong("reservation_waiting.schedule_id"),
            resultSet.getLong("reservation_waiting.waiting_count"),
            resultSet.getBoolean("reservation_waiting.is_deleted")
    );

    public Long save(WaitingEntity waitingEntity) {
        String sql = "INSERT INTO reservation_waiting (member_id, schedule_id, waiting_count) VALUES (?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, waitingEntity.getMemberId());
            ps.setLong(2, waitingEntity.getScheduleId());
            ps.setLong(3, waitingEntity.getWaitingCount());
            return ps;
        }, keyHolder);

        return Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElse(-1L)
                ;
    }

    public WaitingEntity findById(Long id) {
        String sql = "SELECT id, member_id, schedule_id, waiting_count, is_deleted " +
                "FROM reservation_waiting " +
                "WHERE id = ? AND is_deleted = 0;";

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<WaitingEntity> findByMemberId(Long memberId) {
        String sql = "SELECT id, member_id, schedule_id, waiting_count, is_deleted " +
                "FROM reservation_waiting " +
                "WHERE member_id = ? AND is_deleted = 0;";

        return jdbcTemplate.query(sql, rowMapper, memberId);
    }

    public int deleteById(Long id) {
        String sql = "UPDATE reservation_waiting SET is_deleted = 1 WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }
}
