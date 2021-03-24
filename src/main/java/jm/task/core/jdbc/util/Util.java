package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb";
    private static final String USER = "root";
    private static String PASSWORD = "root";
    private static SessionFactory sessionFactory;

//    public static SessionFactory getSessionFactory() {
//        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
//        Metadata metadata = new MetadataSources(standardServiceRegistry).getMetadataBuilder().build();
//        return metadata.getSessionFactoryBuilder().build();
//    }
    public static SessionFactory getSessionFactory(){
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, URL);
                settings.put(Environment.USER, USER);
                settings.put(Environment.PASS, PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");                
                configuration.setProperties(settings);
                
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(serviceRegistryBuilder.build());
            } catch (Exception e) {
                System.err.println("Ошибка создания подключения. " + e);
            }
        }
        return sessionFactory;
    }
}
