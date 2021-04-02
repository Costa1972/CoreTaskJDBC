package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.List;

import static jm.task.core.jdbc.util.Util.*;


public class UserDaoHibernateImpl implements UserDao {

    private Session session;
    private Transaction transaction;
    private Query query;
    private String script;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        script = "CREATE TABLE `mydb`.`users` (\n" +
                "  `userId` BIGINT(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `userName` VARCHAR(45) NOT NULL,\n" +
                "  `userLastName` VARCHAR(45) NOT NULL,\n" +
                "  `userAge` INT(3) NOT NULL,\n" +
                "  PRIMARY KEY (`userId`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;";
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        query = session.createSQLQuery(script);
        query.executeUpdate();
        System.out.println("Таблица создана.");
        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        script = "DROP TABLE IF EXISTS users";
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        query = session.createSQLQuery(script);
        query.executeUpdate();
        System.out.println("Таблица удалена!");
        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            System.out.println("User с именем " + name + " добавлен в БД.");
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.createSQLQuery("DELETE FROM users WHERE usersId = " + id);
            System.out.println("User с ID " + id + " удален из БД.");
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        List<User> userList = session.createQuery("FROM User").list();
        transaction.commit();
        session.close();
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        script = "TRUNCATE TABLE users";
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        query = session.createSQLQuery(script);
        query.executeUpdate();
        System.out.println("Таблица очищена.");
        transaction.commit();
        session.close();
    }
}
