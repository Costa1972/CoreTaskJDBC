package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) throws Exception{
        UserService userService = new UserServiceImpl();

        userService.dropUsersTable();
        userService.createUsersTable();
        userService.saveUser("Dmitriy", "Sergeev", (byte)19);
        userService.saveUser("Anton", "Checkhov", (byte) 45);
        userService.saveUser("Constantin", "Simonov", (byte) 27);
        userService.saveUser("Maria", "Osipova", (byte) 19);
        userService.removeUserById(4);
        System.out.println(userService.getAllUsers());

        //userService.cleanUsersTable();
    }
}
