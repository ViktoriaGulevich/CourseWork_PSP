package by.victoria.dao;

import by.victoria.model.entity.Role;
import by.victoria.model.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.persistence.NoResultException;
import java.util.Set;

public class UserDao {
    private static final SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();


    public User find(String username, String password) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        User user = null;
        try {
            user = session.createQuery("""
                            select u
                            from User u
                            where u.username = :username and u.password = :password
                            """, User.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            e.printStackTrace();
        }
        transaction.commit();
        session.close();

        return user;
    }

    public Object create(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
            try {
                Role role = session.createQuery("""
                        select r
                        from Role r
                        where r.name = 'USER'
                        """, Role.class)
                        .getSingleResult();
                user.setRoles(Set.of(role));
               Object  save = session.save(user);

                transaction.commit();
                session.close();

                return save;
            }catch (RuntimeException e){
                return  null;
            }
    }

    public void update(User user) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        session.update(user);

        transaction.commit();
        session.close();
    }
}
