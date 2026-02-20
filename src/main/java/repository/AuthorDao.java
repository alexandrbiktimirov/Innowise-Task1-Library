package repository;

import model.Author;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.stereotype.Repository;

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

    public long countAuthors(Set<Long> ids) {
        if (ids == null || ids.isEmpty()) return 0;

        var cb = session().getCriteriaBuilder();
        var cq = cb.createQuery(Long.class);
        var root = cq.from(Author.class);

        cq.select(cb.countDistinct(root.get("id"))).where(root.get("id").in(ids));

        return session().createQuery(cq).getSingleResult();
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
