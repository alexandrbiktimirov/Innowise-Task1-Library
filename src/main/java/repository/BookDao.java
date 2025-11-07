package repository;

import model.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings("resource")
public class BookDao {
    private final SessionFactory sessionFactory;

    public BookDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Book findById(long id) {
        return session().find(Book.class, id);
    }

    public List<Book> findAll() {
        return session().createQuery("from Book", Book.class).list();
    }

    public void create(Book Book) {
        session().persist(Book);
    }

    public void update(Book Book) {
        session().merge(Book);
    }

    public void delete(long id) {
        session().remove(id);
    }
    
    private Session session(){
        return sessionFactory.getCurrentSession();
    }
    
//    public List<Book> findByBookId(Long BookId) {
//        String sql = "SELECT * FROM book WHERE Book_id = ? ORDER BY title";
//        return jdbcTemplate.query(sql, rowMapper, BookId);
//    }
//
//    public List<Book> findByGenreId(Long genreId) {
//        String sql = "SELECT * FROM book WHERE genre_id = ? ORDER BY title";
//        return jdbcTemplate.query(sql, rowMapper, genreId);
//    }
}