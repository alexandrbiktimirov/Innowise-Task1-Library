package repository;

import model.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@SuppressWarnings("resource")
public class AuthorDao {
    private final SessionFactory sessionFactory;

    public AuthorDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private Session session() {
        return sessionFactory.getCurrentSession();
    }

    public Author findById(long id) {
        return session().find(Author.class, id);
    }

    public List<Author> findAll() {
        return session().createQuery("from Author", Author.class).list();
    }

    public Set<Author> findByIds(Set<Long> ids) {
        return new HashSet<>(session()
                .createQuery("from Author where id in (:ids)", Author.class)
                .setParameter("ids", ids)
                .getResultList());
    }

    public void create(Author author) {
        session().persist(author);
    }

    public void update(Author author) {
        session().merge(author);
    }

    public void delete(long id) {
        Author author = findById(id);
        if (author != null) {
            session().remove(author);
        }
    }
}
