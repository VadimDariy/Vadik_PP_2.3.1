package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {

    void addUser(User user);

    void deleteUserById(Long id);

    void updateUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);
}

/*       Разделение интерфейса и его реализации позволяет создавать гибкую архитектуру приложения. Код, использующий
    UserDao, не зависит от конкретной реализации UserDaoImpl. Это позволяет легко заменять одну реализацию другой без
    изменения кода, который использует UserDao.
*/