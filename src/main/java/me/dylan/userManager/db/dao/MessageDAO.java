package me.dylan.userManager.db.dao;

import me.dylan.userManager.db.model.Message;
import me.dylan.userManager.db.model.TeamUser;
import me.dylan.userManager.db.model.User;
import me.dylan.userManager.db.model.UserFriend;
import me.dylan.userManager.util.DataBaseUtils;
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
        return DataBaseUtils.getTableListWith(sessionFactory, new String[]{}, new Object[]{}, Message.class);
    }

    public List<Message> getReceived(long id) {
        return DataBaseUtils.getTableListWith(sessionFactory, new String[]{"receiver"}, new Object[]{id}, Message.class);
    }

    public List<Message> getSender(long id) {
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
