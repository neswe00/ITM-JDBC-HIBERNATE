package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.util.List;

import static java.sql.DriverManager.getConnection;

public class UserDaoJDBCImpl extends Util implements UserDao    {
    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users(" +
                "Id SERIAL PRIMARY KEY," +
                "FirstName CHARACTER VARYING(30)," +
                "LastName CHARACTER VARYING(30)," +
                "Age INTEGER" +
                ")";

        try (Connection connection = getConnection();
             Statement stat = connection.createStatement()) {

            stat.executeUpdate(sql);
            System.out.println("Table was created!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Table wasn't created!");
        }


    }
    @Override
    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try (Connection connection = getConnection();
             Statement stat = connection.createStatement()) {

            stat.executeUpdate(sql);
            System.out.println("Table was dropped");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Table wasn't dropped");
        }

    }
    @Override
    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (FirstName, LastName, Age) VALUES(?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preStat = connection.prepareStatement(sql)) {

            //preStat.setLong(1, 1);
            preStat.setString(1, name);
            preStat.setString(2, lastName);
            preStat.setByte(3, age);

            preStat.executeUpdate();
            System.out.println("User was added!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    @Override
    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE ID=?";

        try (Connection connection = getConnection();
             PreparedStatement preStat = connection.prepareStatement(sql)) {

            preStat.setLong(1, id);

            preStat.executeUpdate();
            System.out.println("User was removed!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        String sql = "SELECT Id, FirstName, LastName, Age FROM users";

        try (Connection connection = getConnection();
             Statement stat = connection.createStatement()) {

            ResultSet resultSet = stat.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("Id"));
                user.setName(resultSet.getString("FirstName"));
                user.setLastName(resultSet.getString("LastName"));
                user.setAge(resultSet.getByte("Age"));

                userList.add(user);
            }
            System.out.println("List of users is ready!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }
    @Override
    public void cleanUsersTable() {
        String sql = "DELETE FROM users";
        try (Connection connection = getConnection();
             Statement stat = connection.createStatement()) {

            stat.executeUpdate(sql);
            System.out.println("Table was cleaned!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Table wasn't cleaned!");
        }

    }
}
