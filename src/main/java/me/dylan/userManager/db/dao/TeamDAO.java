package me.dylan.userManager.db.dao;

import me.dylan.userManager.db.model.Team;
import me.dylan.userManager.db.model.TeamUser;
import me.dylan.userManager.db.model.UserFriend;
import me.dylan.userManager.util.DataBaseUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("teamDAO")
public class TeamDAO {
    @Autowired
    private SessionFactory sessionFactory;
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Team> getAll() {
        return DataBaseUtils.getTableListWith(sessionFactory, new String[]{}, new Object[]{}, Team.class);
    }

    @Transactional
    public List<TeamUser> getAllU(long user_id) {
        return DataBaseUtils.getTableListWith(sessionFactory, new String[]{"user"}, new Object[]{user_id}, TeamUser.class);
    }

    @Transactional
    public List<TeamUser> getAllT(long team_id) {
        return DataBaseUtils.getTableListWith(sessionFactory, new String[]{"team"}, new Object[]{team_id}, TeamUser.class);
    }

    @Transactional
    public Team get(String teamName) {
        return DataBaseUtils.getTableWith(sessionFactory, new String[]{"name"}, new Object[]{teamName}, Team.class);
    }
    @Transactional
    public TeamUser get(long user_id, long team_id) {
        return DataBaseUtils.getTableWith(sessionFactory, new String[]{"user", "team"}, new Object[]{user_id, team_id}, TeamUser.class);
    }

    @Transactional
    public long insertNew(Team team) {
        return (long) (Long) sessionFactory.getCurrentSession().save(team);
    }

    @Transactional
    public long insertNew(TeamUser teamUser) {
        return (long) (Long) sessionFactory.getCurrentSession().save(teamUser);
    }

    @Transactional
    public void update(Team team) {
        sessionFactory.getCurrentSession().update(team);
    }

    @Transactional
    public void update(TeamUser teamUser) {
        sessionFactory.getCurrentSession().update(teamUser);
    }

    @Transactional
    public void remove(Team team) {
        DataBaseUtils.removeTableWith(sessionFactory, new String[]{"team"}, new Object[]{team.getId()}, TeamUser.class);
        DataBaseUtils.removeTableWith(sessionFactory, new String[]{"id"}, new Object[]{team.getId()}, Team.class );
    }

    @Transactional
    public void remove(TeamUser teamUser) {
        DataBaseUtils.removeTableWith(sessionFactory, new String[]{"id"}, new Object[]{teamUser.getId()}, TeamUser.class );
    }
}
