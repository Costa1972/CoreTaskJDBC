package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import javax.transaction.Transaction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }


    @Override
    public void createUsersTable() {
        String sqlQuery = "CREATE TABLE IF NOT EXISTS `mydb`.`users` (\n" +
                "  `usersId` DOUBLE NOT NULL AUTO_INCREMENT,\n" +
                "  `usersName` VARCHAR(45) NOT NULL,\n" +
                "  `usersLastName` VARCHAR(45) NOT NULL,\n" +
                "  `usersAge` INT NULL,\n" +
                "  PRIMARY KEY (`usersId`));";
        try (Statement statement = getConnection().createStatement()) {
            statement.execute(sqlQuery);
            System.out.println("Таблица создана!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (getConnection() != null) {
                try {
                    getConnection().close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void dropUsersTable() {
        String sqlQuery = "DROP TABLE IF EXISTS users";
        try (Statement statement = getConnection().createStatement()) {
            statement.execute(sqlQuery);
            System.out.println("Таблица удалена!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            if (getConnection() != null) {
                try {
                    getConnection().close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sqlQuery = "INSERT INTO users(usersName, usersLastName, usersAge) values (?, ?, ?)";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
            System.out.println("User с именем " + name + " добавлен в БД.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (getConnection() != null) {
                    getConnection().close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction;
        Long l = id;
        String sqlQuery = "DELETE FROM users WHERE usersId = " + id;
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = getConnection().createStatement();
            statement.execute(sqlQuery);
            connection.commit();
            System.out.println("User с ID " + id + " удален из БД.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (getConnection() != null) {
                    getConnection().close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        String sqlQuery = "SELECT * FROM users";
        List<User> list = new ArrayList<>();
        try (Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                Byte age = resultSet.getByte(4);
                list.add(new User(id, name, lastName, age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (getConnection() != null) {
                    getConnection().close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        String sqlQuery = "TRUNCATE TABLE users";
        try (Statement statement = getConnection().createStatement()) {
            statement.execute(sqlQuery);
            System.out.println("Таблица очищена.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (getConnection() != null) {
                    getConnection().close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

