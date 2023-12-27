package jm.task.core.jdbc;
import java.util.ArrayList;
import java.util.List;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.*;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        List<User> users = new ArrayList<>();
        users.add(new User("Piotr", "Piotrov", (byte) 45));
        users.add(new User("Ivan", "Piotrov", (byte) 44));
        users.add(new User("Gavrik", "Piotrov", (byte) 43));
        users.add(new User("Pavlik", "Piotrov", (byte) 42));

        for (User us : users) {
            userService.saveUser(us.getName(), us.getLastName(), (byte) us.getAge());
            System.out.println("User с именем – " + us.getName() + " добавлен в базу данных");
        }

        List<User> usersTable = userService.getAllUsers();
        for (User us : usersTable) {
            System.out.println(us);
        }

        userService.cleanUsersTable();
        //userService.dropUsersTable();
    }
}
