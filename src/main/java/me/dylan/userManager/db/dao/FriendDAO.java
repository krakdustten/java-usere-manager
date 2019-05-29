package me.dylan.userManager.db.dao;

import me.dylan.userManager.db.model.User;
import me.dylan.userManager.util.DataBaseUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("friendDAO")
public class FriendDAO {
    @Autowired
    private SessionFactory sessionFactory;
    public SessionFactory getSessionFactory() { return sessionFactory; }
    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    @Transactional
    public List<User> getAll() {
        return DataBaseUtils.getTableListWith(sessionFactory, new String[]{}, new Object[]{}, User.class);
    }
}
