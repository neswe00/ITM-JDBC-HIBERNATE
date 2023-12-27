package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            session.beginTransaction();

            String sql = "CREATE TABLE IF NOT EXISTS users(" +
                    "id SERIAL PRIMARY KEY," +
                    "firstName CHARACTER VARYING(30)," +
                    "lastName CHARACTER VARYING(30)," +
                    "age INTEGER" +
                    ")";

            session.createSQLQuery(sql).executeUpdate();

            session.getTransaction().commit();
            System.out.println("Table users created successfully.");
        } catch (Exception e) {
            System.out.println("Error creating users table: " + e.getMessage());
        }

    }

    @Override
    public void dropUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery("DROP TABLE IF EXISTS users").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = null;
        Transaction transaction = null;
        try {
            // Получение сессии из HibernateSessionFactory
            session = getSessionFactory().getCurrentSession();

            // Начало транзакции
            transaction = session.beginTransaction();

            // Создание объекта пользователя
            User user = new User();
            user.setName(name);
            user.setLastName(lastName);
            user.setAge(age);

            // Сохранение пользователя в базу данных
            session.save(user);

            // Фиксация транзакции
            transaction.commit();

            System.out.println("Пользователь успешно добавлен в базу данных!");
        } catch (Exception e) {
            // Откат транзакции
            if (transaction != null) {
                transaction.rollback();
            }
            System.out.println("Ошибка при добавлении пользователя в базу данных: " + e.getMessage());
        } finally {
            // Закрытие сессии
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = getSessionFactory().openSession()) {
            return session.createQuery("from User", User.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }



    @Override
    public void cleanUsersTable() {
        try (Session session = getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
