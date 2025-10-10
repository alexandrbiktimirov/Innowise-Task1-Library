package repository;

import model.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;

@Repository
public class AuthorDao {
    private final JdbcTemplate jdbcTemplate;

    public AuthorDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Author findById(Long id) {
        String sql = "SELECT * FROM author WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<Author> findAll() {
        String sql = "SELECT * FROM author";

        return jdbcTemplate.query(sql, rowMapper);
    }

    public void create(Author author) {
        String sql = "INSERT INTO author (first_name, last_name) VALUES (?, ?)";

        jdbcTemplate.update(sql, author.getFirstName(), author.getLastName());
    }

    public void update(Author author) {
        String sql = "UPDATE author SET first_name = ?, last_name = ? WHERE id = ?";

        jdbcTemplate.update(sql, author.getFirstName(), author.getLastName(), author.getId());
    }

    public void delete(Integer id) {
        String sql = "DELETE FROM author WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }

    private final RowMapper<Author> rowMapper = (resultSet, rowNum) -> {
        Author author = new Author();

        author.setId(resultSet.getLong("id"));
        author.setFirstName(resultSet.getString("first_name"));
        author.setLastName(resultSet.getString("last_name"));

        return author;
    };
}
