package repository;

import model.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookDao {
    private final JdbcTemplate jdbcTemplate;

    public BookDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Book findById(Long id) {
        String sql = "SELECT * FROM book WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    public List<Book> findAll() {
        String sql = "SELECT * FROM book ORDER BY title";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public List<Book> findByAuthorId(Long authorId) {
        String sql = "SELECT * FROM book WHERE author_id = ? ORDER BY title";
        return jdbcTemplate.query(sql, rowMapper, authorId);
    }

    public List<Book> findByGenreId(Long genreId) {
        String sql = "SELECT * FROM book WHERE genre_id = ? ORDER BY title";
        return jdbcTemplate.query(sql, rowMapper, genreId);
    }

    public void create(Book book) {
        String sql = "INSERT INTO book (title, author_id, description, genre_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthorId(), book.getDescription(), book.getGenreId());
    }

    public void update(Book book) {
        String sql = "UPDATE book SET title = ?, author_id = ?, description = ?, genre_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthorId(), book.getDescription(), book.getGenreId(), book.getId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private final RowMapper<Book> rowMapper = (rs, rowNum) -> {
        Book b = new Book();
        b.setId(rs.getLong("id"));
        b.setTitle(rs.getString("title"));
        b.setAuthorId(rs.getLong("author_id"));
        b.setDescription(rs.getString("description"));
        b.setGenreId(rs.getLong("genre_id"));
        return b;
    };
}