package me.dylan.userManager.db.dao;

import me.dylan.userManager.db.model.Message;
import me.dylan.userManager.db.model.TeamUser;
import me.dylan.userManager.db.model.User;
import me.dylan.userManager.db.model.UserFriend;
import me.dylan.userManager.util.DataBaseUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
        return DataBaseUtils.getTableListWith(sessionFactory, new String[]{}, new Object[]{}, User.class);
    }

    @Transactional
    public User get(long userId) {
        return DataBaseUtils.getTableWith(sessionFactory, new String[]{"id"}, new Object[]{userId}, User.class);
    }

    @Transactional
    public User get(String username) {
        return DataBaseUtils.getTableWith(sessionFactory, new String[]{"name"}, new Object[]{username}, User.class);
    }

    @Transactional
    public User get(String username, String password) {
        return DataBaseUtils.getTableWith(sessionFactory, new String[]{"name", "password"}, new Object[]{username, password}, User.class);
    }

    @Transactional
    public User getCID(String username, String currentID) {
        User user = DataBaseUtils.getTableWith(sessionFactory, new String[]{"name", "currentID"}, new Object[]{username, currentID}, User.class);
        if(user != null)
            if(user.getValidUntilID().after(new Date()) && user.isConfirmed())
                return user;
        return null;
    }

    @Transactional
    public long insertNew(User user) {
        return (long) (Long) sessionFactory.getCurrentSession().save(user);
    }

    @Transactional
    public void update(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

    @Transactional
    public void remove(User user) {
        DataBaseUtils.removeTableWith(sessionFactory,new String[]{"receiver"}, new Object[]{user.getId()}, Message.class );
        DataBaseUtils.removeTableWith(sessionFactory,new String[]{"sender"}, new Object[]{user.getId()}, Message.class );
        DataBaseUtils.removeTableWith(sessionFactory,new String[]{"user"}, new Object[]{user.getId()}, TeamUser.class );
        DataBaseUtils.removeTableWith(sessionFactory,new String[]{"user1"}, new Object[]{user.getId()}, UserFriend.class );
        DataBaseUtils.removeTableWith(sessionFactory,new String[]{"user2"}, new Object[]{user.getId()}, UserFriend.class );
        DataBaseUtils.removeTableWith(sessionFactory,new String[]{"id"}, new Object[]{user.getId()}, User.class );
    }
}
