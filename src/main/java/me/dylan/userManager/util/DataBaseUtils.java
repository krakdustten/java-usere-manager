package me.dylan.userManager.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

public class DataBaseUtils {
    public static <T> T getTableWith(SessionFactory sessionFactory, String[] coln, Object[] data, Class<T> table){
        List<T> tbl = getTableListWith(sessionFactory, coln, data, table);
        return tbl.isEmpty() ? null  : tbl.get(0);
    }

    public static <T> List<T> getTableListWith(SessionFactory sessionFactory, String[] coln, Object[] data, Class<T> table){
        if(coln.length != data.length)
            return null;

        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(table);
        Root<T> from = criteriaQuery.from(table);
        criteriaQuery = criteriaQuery.select(from);
        if(coln.length == 1)
            criteriaQuery = criteriaQuery.where(criteriaBuilder.equal(from.get(coln[0]), data[0]));
        else if(coln.length > 1){
            Predicate predicate = criteriaBuilder.and(
                    criteriaBuilder.equal(from.get(coln[0]), data[0]),
                    criteriaBuilder.equal(from.get(coln[1]), data[1]));
            for(int i = 2; i < coln.length; i++) {
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.equal(from.get(coln[i]), data[i]));
            }
            criteriaQuery.where(predicate);
        }

        TypedQuery<T> typedQuery = session.createQuery(criteriaQuery);
        List<T> users = typedQuery.getResultList();
        return users;
    }

    public static <T> void removeTableWith(SessionFactory sessionFactory, String[] coln, Object[] data, Class<T> table){
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaDelete<T> delete = criteriaBuilder.createCriteriaDelete(table);
        Root<T> from = delete.from(table);
        if(coln.length == 1)
            delete.where(criteriaBuilder.equal(from.get(coln[0]), data[0]));
        else if(coln.length > 1){
            Predicate predicate = criteriaBuilder.and(
                    criteriaBuilder.equal(from.get(coln[0]), data[0]),
                    criteriaBuilder.equal(from.get(coln[1]), data[1]));
            for(int i = 2; i < coln.length; i++) {
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.equal(from.get(coln[i]), data[i]));
            }
            delete.where(predicate);
        }
        else
            return;

        session.createQuery(delete).executeUpdate();
    }
}