package me.dylan.userManager.db.dao;

import me.dylan.userManager.db.model.Message;
import me.dylan.userManager.util.DataBaseUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository("messageDAO")
public class MessageDAO {

    @Autowired
    private SessionFactory sessionFactory;
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Message> getAll() {
        return DataBaseUtils.getTableListWith(sessionFactory, new String[]{}, new Object[]{}, "sendTime", Message.class);
    }

    @Transactional
    public List<Message> get(long user_id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
        Root<Message> from = criteriaQuery.from(Message.class);
        criteriaQuery = criteriaQuery.select(from);
        Predicate predicate =
                criteriaBuilder.or(
                        criteriaBuilder.equal(from.get("receiver"), user_id),
                        criteriaBuilder.equal(from.get("sender"), user_id)
                );
        criteriaQuery.where(predicate);
        criteriaQuery.orderBy(criteriaBuilder.desc(from.get("sendTime")));
        TypedQuery<Message> typedQuery = session.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }

    @Transactional
    public Message get(long message_id, long user_id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
        Root<Message> from = criteriaQuery.from(Message.class);
        criteriaQuery = criteriaQuery.select(from);
        Predicate predicate =
                criteriaBuilder.and(
                        criteriaBuilder.equal(from.get("id"), message_id),
                        criteriaBuilder.or(
                                criteriaBuilder.equal(from.get("receiver"), user_id),
                                criteriaBuilder.equal(from.get("sender"), user_id)
                        )
                );
        criteriaQuery.where(predicate);
        TypedQuery<Message> typedQuery = session.createQuery(criteriaQuery);
        List<Message> friends = typedQuery.getResultList();
        return friends.isEmpty() ? null  : friends.get(0);
    }

    public List<Message> getReceived(long id) {
        return DataBaseUtils.getTableListWith(sessionFactory, new String[]{"receiver"}, new Object[]{id}, Message.class);
    }

    public List<Message> getSend(long id) {
        return DataBaseUtils.getTableListWith(sessionFactory, new String[]{"sender"}, new Object[]{id}, Message.class);
    }

    @Transactional
    public long insertNew(Message message) {
        return (long) (Long) sessionFactory.getCurrentSession().save(message);
    }

    @Transactional
    public void update(Message message) {
        sessionFactory.getCurrentSession().update(message);
    }

    @Transactional
    public void remove(Message message) {
        DataBaseUtils.removeTableWith(sessionFactory,new String[]{"id"}, new Object[]{message.getId()}, Message.class );
    }
}
