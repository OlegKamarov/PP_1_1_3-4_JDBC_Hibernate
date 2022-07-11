package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Properties;

import static jm.task.core.jdbc.util.Util.getSessionFactory;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    private Session currentSession() {
        return getSessionFactory().openSession();
    }

    @Override
    public void createUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS Users "
                    + "(id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(45) NOT NULL, "
                    + "lastName VARCHAR(45) NOT NULL, "
                    + "age TINYINT NOT NULL)";

            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            currentSession().getTransaction().rollback();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            String sql = "DROP TABLE if exists USERS";
            Query query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            currentSession().getTransaction().rollback();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            User user = new User(name, lastName, age);

            session.save(user);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            currentSession().getTransaction().rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            String hql = "User WHERE id = :id";
            User user = session.get(User.class, id);

            session.delete(user);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            currentSession().getTransaction().rollback();
        }
    }

    @Override
    public List<User> getAllUsers() {
        return currentSession().createQuery("from User", User.class).list();
    }

    @Override
    public void cleanUsersTable() {
        Session session = getSessionFactory().getCurrentSession();
        Transaction transaction = session.beginTransaction();

        session.createQuery("delete from User").executeUpdate();
        transaction.commit();
    }
}
