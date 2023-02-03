package nextstep.schedule.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import nextstep.schedule.domain.ReservationStatus;
import nextstep.schedule.entity.ReservationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class ReservationDao {

    public final JdbcTemplate jdbcTemplate;
    private final RowMapper<ReservationEntity> rowMapper = (resultSet, rowNum) -> new ReservationEntity(
            resultSet.getLong("reservation.id"),
            resultSet.getLong("reservation.schedule_id"),
            resultSet.getLong("reservation.member_id"),
            resultSet.getLong("reservation.waiting_count"),
            resultSet.getString("reservation.status")
    );

    @Autowired
    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(ReservationEntity reservationEntity) throws DuplicateKeyException {
        String sql = "INSERT INTO reservation (schedule_id, member_id, waiting_count, status) VALUES (?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, reservationEntity.getScheduleId());
            ps.setLong(2, reservationEntity.getMemberId());
            ps.setLong(3, reservationEntity.getWaitingCount());
            ps.setString(4, reservationEntity.getStatus());
            return ps;
        }, keyHolder);

        return Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .orElse(-1L)
                ;
    }

    public List<ReservationEntity> findAcceptedByMemberId(Long memberId) {
        String sql = "SELECT id, schedule_id, member_id, waiting_count,status " +
                "FROM reservation " +
                "WHERE member_id = ? AND status = ?;";

        return jdbcTemplate.query(sql, rowMapper, memberId, ReservationStatus.ACCEPTED.toString());
    }

    public List<ReservationEntity> findWaitingByMemberId(Long memberId) {
        String sql = "SELECT id, schedule_id, member_id, waiting_count,status " +
                "FROM reservation " +
                "WHERE member_id = ? AND status = ?;";

        return jdbcTemplate.query(sql, rowMapper, memberId, ReservationStatus.WAITING.toString());
    }

    public ReservationEntity findById(Long id) {
        String sql = "SELECT id, schedule_id, member_id, waiting_count, status " +
                "FROM reservation " +
                "WHERE id = ? AND status != ?;";

        return jdbcTemplate.queryForObject(sql, rowMapper, id, ReservationStatus.CANCELED.toString());
    }

    public List<ReservationEntity> findByScheduleId(Long scheduleId) {
        String sql = "SELECT id, schedule_id, member_id, waiting_count, status " +
                "FROM reservation " +
                "WHERE schedule_id = ? AND status != ?;";

        return jdbcTemplate.query(sql, rowMapper, scheduleId, ReservationStatus.CANCELED.toString());
    }

    public int deleteById(Long id) {
        String sql = "UPDATE reservation " +
                "SET status = ?" +
                "WHERE id = ?;";

        return jdbcTemplate.update(sql, ReservationStatus.CANCELED.toString(), id);
    }
}
