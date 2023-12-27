package jm.task.core.jdbc.util;


import org.hibernate.SessionFactory;
import jm.task.core.jdbc.model.User;

import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.hibernate.Session;

import org.hibernate.Transaction;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class Util {
    private static final String URL = "jdbc:postgresql://127.0.0.1:5432/";
    private static final String USER = "admin";
    private static final String PASSWORD = "admin";

    public Connection getConnection() {
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Unable to load class.");
            e.printStackTrace();
        }

        //System.out.println("Creating database connection...");
        try {
            connection = DriverManager.getConnection(Util.URL, Util.USER, Util.PASSWORD);

        } catch (SQLException th) {
            System.out.println("Connection ERROR!");
            th.printStackTrace();
        }

        return connection;
    }
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                System.out.println("Исключение!" + e);
            }
        }
        return sessionFactory;
    }
}
