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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository("MessageDAO")
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
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Message> criteriaQuery = criteriaBuilder.createQuery(Message.class);
        Root<Message> from = criteriaQuery.from(Message.class);
        CriteriaQuery<Message> select = criteriaQuery.select(from);
        TypedQuery<Message> typedQuery = session.createQuery(select);
        List<Message> messages = typedQuery.getResultList();
        return messages;
    }

    public List<Message> getReceived(long id) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Message where receiver = :userId");
        query.setParameter("userId", id);
        return query.getResultList();
    }
}
