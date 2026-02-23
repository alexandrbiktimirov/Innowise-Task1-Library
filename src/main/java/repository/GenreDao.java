package repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import model.Genre;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@SuppressWarnings("resource")
public class GenreDao {
    private final SessionFactory sessionFactory;

    public GenreDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Genre findById(long id) {
        return session().find(Genre.class, id);
    }

    public List<Genre> findAll() {
        CriteriaBuilder cb = session().getCriteriaBuilder();
        CriteriaQuery<Genre> cq = cb.createQuery(Genre.class);
        Root<Genre> genre = cq.from(Genre.class);
        cq.select(genre);

        return session().createQuery(cq).getResultList();
    }

    public Set<Genre> findByIds(Set<Long> ids) {
        return new HashSet<>(session()
                .createQuery("from Genre where id in (:ids)", Genre.class)
                .setParameter("ids", ids)
                .getResultList());
    }

    public void create(Genre Genre) {
        session().persist(Genre);
    }

    public void update(Genre Genre) {
        session().merge(Genre);
    }

    public void delete(long id) {
        Genre genre = findById(id);
        if (genre != null) {
            session().remove(genre);
        }
    }

    private Session session(){
        return sessionFactory.getCurrentSession();
    }
}
