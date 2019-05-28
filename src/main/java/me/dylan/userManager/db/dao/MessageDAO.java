package me.dylan.userManager.db.dao;

import me.dylan.userManager.db.model.Message;
import me.dylan.userManager.db.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Query;
import java.util.List;

public class MessageDAO {

    private SessionFactory sessionFactory;
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public Message get(long messageId) {
        return sessionFactory.getCurrentSession().get(Message.class, messageId);
    }

    @Transactional
    public List<Message> getFromUser(Long userId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Message where receiver = :userId or sender = :userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Transactional
    public List<Message> getFromSender(Long userId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Message where sender = :userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Transactional
    public List<Message> getFromReceiver(Long userId) {
        Query query = sessionFactory.getCurrentSession().createQuery("from Message where receiver = :userId");
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    private static MessageDAO self = new MessageDAO();
    public static MessageDAO get() {
        return self;
    }
}
