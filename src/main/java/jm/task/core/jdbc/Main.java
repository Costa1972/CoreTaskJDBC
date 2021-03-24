package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        UserDaoHibernateImpl userDaoHibernate = new UserDaoHibernateImpl();


        userService.dropUsersTable();
        userService.createUsersTable();
        userDaoHibernate.saveUser("Vladimir", "Lenin", (byte) 100);
        userService.saveUser("Dmitriy", "Sergeev", (byte)19);
        userService.saveUser("Anton", "Checkhov", (byte) 45);
        userService.saveUser("Constantin", "Simonov", (byte) 27);
        System.out.println(userService.getAllUsers());
        userService.saveUser("Maria", "Osipova", (byte) 19);
        userService.cleanUsersTable();
        userService.cleanUsersTable();
    }
}
