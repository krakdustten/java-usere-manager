package me.dylan.userManager.db.dao;

import me.dylan.userManager.db.model.Message;
import me.dylan.userManager.db.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;


@Repository("userDAO")
public class UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<User> getAll() {
        /*Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> from = criteriaQuery.from(User.class);
        CriteriaQuery<User> select = criteriaQuery.select(from);
        TypedQuery<User> typedQuery = session.createQuery(select);
        List<User> users = typedQuery.getResultList();*/
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> from = criteriaQuery.from(User.class);
        CriteriaQuery<User> select = criteriaQuery.select(from);
        TypedQuery<User> typedQuery = session.createQuery(select);
        List<User> users = typedQuery.getResultList();
        return users;
    }

    @Transactional
    public int insertNew(User user) {
        return (int) (Integer) sessionFactory.getCurrentSession().save(user);
    }

    @Transactional
    public User get(long userId) {
        //return sessionFactory.getCurrentSession().get(User.class, userId);
        Session s = sessionFactory.getCurrentSession();
        CriteriaBuilder cb = s.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> ur = cq.from(User.class);
        Join<User, Message> umrj = ur.join("receivedMessages");
        Join<User, Message> umsj = ur.join("senderMessages");
        List<Predicate> conditions = new ArrayList<>();
        conditions.add(cb.equal(umrj.get("receiver"), userId));
        conditions.add(cb.equal(umsj.get("sender"), userId));
        conditions.add(cb.equal(ur.get("id"), userId));

        TypedQuery<User> tq = s.createQuery(cq.select(ur).where(conditions.toArray(new Predicate[0])));
        return tq.getSingleResult();
    }

    @Transactional
    public User get(String username) {
        Query query = sessionFactory.getCurrentSession().createQuery("from User where name = :username");
        query.setParameter("username", username);
        return (User) query.getSingleResult();
    }

    @Transactional
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    @Transactional
    public void remove(User user) {
        sessionFactory.getCurrentSession().delete(user);
    }
}
