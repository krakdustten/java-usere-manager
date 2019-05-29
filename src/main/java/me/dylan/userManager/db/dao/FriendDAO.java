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

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository("friendDAO")
public class FriendDAO{
    @Autowired
    private SessionFactory sessionFactory;
    public SessionFactory getSessionFactory() { return sessionFactory; }
    public void setSessionFactory(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    @Transactional
    public List<UserFriend> getAll() {
        return DataBaseUtils.getTableListWith(sessionFactory, new String[]{}, new Object[]{}, UserFriend.class);
    }

    @Transactional
    public UserFriend get(long user_id, long friend_id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UserFriend> criteriaQuery = criteriaBuilder.createQuery(UserFriend.class);
        Root<UserFriend> from = criteriaQuery.from(UserFriend.class);
        criteriaQuery = criteriaQuery.select(from);
        Predicate predicate =
                criteriaBuilder.or(
                        criteriaBuilder.and(
                                criteriaBuilder.equal(from.get("user1"), user_id),
                                criteriaBuilder.equal(from.get("user2"), friend_id)
                        ),
                        criteriaBuilder.and(
                                criteriaBuilder.equal(from.get("user1"), friend_id),
                                criteriaBuilder.equal(from.get("user2"), user_id)
                        )
                );
        criteriaQuery.where(predicate);
        TypedQuery<UserFriend> typedQuery = session.createQuery(criteriaQuery);
        List<UserFriend> friends = typedQuery.getResultList();
        return friends.isEmpty() ? null  : friends.get(0);
    }

    @Transactional
    public List<UserFriend> get(long user_id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<UserFriend> criteriaQuery = criteriaBuilder.createQuery(UserFriend.class);
        Root<UserFriend> from = criteriaQuery.from(UserFriend.class);
        criteriaQuery = criteriaQuery.select(from);
        Predicate predicate = criteriaBuilder.or(
                                criteriaBuilder.equal(from.get("user1"), user_id),
                                criteriaBuilder.equal(from.get("user2"), user_id)
                        );
        criteriaQuery.where(predicate);
        TypedQuery<UserFriend> typedQuery = session.createQuery(criteriaQuery);
        List<UserFriend> friends = typedQuery.getResultList();
        return friends;
    }

    @Transactional
    public UserFriend getOneWay(long user_id, long friend_id) {
        return DataBaseUtils.getTableWith(sessionFactory, new String[]{"user1", "user2"}, new Object[]{friend_id, user_id}, UserFriend.class);
    }

    @Transactional
    public long insertNew(UserFriend friend) {
        return (long) (Long) sessionFactory.getCurrentSession().save(friend);
    }

    @Transactional
    public void update(UserFriend friend) {
        sessionFactory.getCurrentSession().update(friend);
    }

    @Transactional
    public void remove(UserFriend friend) {
        DataBaseUtils.removeTableWith(sessionFactory,new String[]{"id"}, new Object[]{friend.getId()}, UserFriend.class );
    }
}
