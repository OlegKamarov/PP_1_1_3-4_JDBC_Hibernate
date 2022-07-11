package jm.task.core.jdbc;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;

public class Main {
    private static UserDao userDao = new UserDaoJDBCImpl();

    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        userDao.createUsersTable();

        userDao.saveUser("Вася", "Кочкин", (byte) 27);
        System.out.println("User с именем – Вася добавлен в базу данных");
        userDao.saveUser("Маша", "Ермолаева", (byte) 26);
        System.out.println("User с именем – Маша добавлен в базу данных");
        User user3 = new User("Эркюль", "Пуаро", (byte) 74);
        System.out.println("User с именем – Эркюль добавлен в базу данных");
        userDao.saveUser("Иван Петрович", "Павлов", (byte) 86);
        System.out.println("User с именем – Иван Петрович добавлен в базу данных");

        System.out.println(userDao.getAllUsers());
        userDao.cleanUsersTable();
        userDao.dropUsersTable();
    }
}
