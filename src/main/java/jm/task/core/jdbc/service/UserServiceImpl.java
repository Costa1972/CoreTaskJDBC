package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

import java.util.List;
public class UserServiceImpl implements UserService{

    private final UserDao udJ = new UserDaoJDBCImpl();
    private final UserDao udH = new UserDaoHibernateImpl();

    @Override
    public void createUsersTable() {
        udH.createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        udH.dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        udH.saveUser(name, lastName, age);
    }

    @Override
    public void removeUserById(long id) {
        udH.removeUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return udH.getAllUsers();
    }

    @Override
    public void cleanUsersTable() {
        udH.cleanUsersTable();
    }
}
