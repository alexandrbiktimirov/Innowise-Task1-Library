package repository;

import model.Genre;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreDao {
    private final JdbcTemplate jdbcTemplate;

    public GenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Genre findById(Long id) {
        String sql = "SELECT * FROM genre WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<Genre> findAll() {
        String sql = "SELECT * FROM genre ORDER BY name";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void create(Genre genre) {
        String sql = "INSERT INTO genre (name) VALUES (?)";
        jdbcTemplate.update(sql, genre.getName());
    }

    public void update(Genre genre) {
        String sql = "UPDATE genre SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, genre.getName(), genre.getId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM genre WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private final RowMapper<Genre> rowMapper = (rs, rowNum) -> {
        Genre g = new Genre(null);
        g.setId(rs.getLong("id"));
        g.setName(rs.getString("name"));
        return g;
    };
}