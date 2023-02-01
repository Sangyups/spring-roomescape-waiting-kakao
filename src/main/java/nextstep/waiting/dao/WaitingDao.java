package nextstep.waiting.dao;

import lombok.RequiredArgsConstructor;
import nextstep.waiting.entity.WaitingEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WaitingDao {

    public final JdbcTemplate jdbcTemplate;

    private final RowMapper<WaitingEntity> rowMapper = (resultSet, rowNum) -> new WaitingEntity(
            resultSet.getLong("waiting.id"),
            resultSet.getLong("waiting.member_id"),
            resultSet.getLong("waiting.schedule_id"),
            resultSet.getLong("waiting.waiting_count"),
            resultSet.getBoolean("waiting.is_deleted")
    );

    public List<WaitingEntity> findByMemberId(Long memberId) {
        String sql = "SELECT id, member_id, schedule_id, waiting_count, is_deleted " +
                "FROM waiting " +
                "WHERE member_id = ? AND is_deleted = 0;";

        return jdbcTemplate.query(sql, rowMapper, memberId);
    }

    public int deleteById(Long id) {
        String sql = "UPDATE waiting SET is_deleted = 1 WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }
}