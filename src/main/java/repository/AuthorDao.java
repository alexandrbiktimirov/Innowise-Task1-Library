package repository;

import model.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings("resource")
public class AuthorDao {
    private final SessionFactory sessionFactory;

    public AuthorDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session session(){
        return sessionFactory.getCurrentSession();
    }

    public Author findById(long id) {
        return session().find(Author.class, id);
    }

    public List<Author> findAll() {
        return session().createQuery("from Author", Author.class).list();
    }

    public void create(Author author) {
        session().persist(author);
    }

    public void update(Author author) {
        session().merge(author);
    }

    public void delete(long id) {
        session().remove(id);
    }
}
