package com.kolia.dao;

import com.kolia.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    SessionFactory sessionFactory;

    private Session openSession()
    {
        return sessionFactory.getCurrentSession();
    }

    @Transactional
    @SuppressWarnings("unchecked")
    @Override
    public User findByLogin(String login) {

        List<User> users = new ArrayList<User>();

        users = openSession()
                .createQuery("from User where login=?")
                .setParameter(0, login)
                .list();

        if (users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    @Transactional
    @Override
    public User findById(Integer id) {
        return (User)openSession().get(User.class, id);
    }
}
