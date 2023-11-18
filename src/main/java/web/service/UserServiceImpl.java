package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        userDao.addUser(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        userDao.deleteUserById(id);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }
}

/*
         Этот класс UserServiceImpl представляет реализацию интерфейса UserService. Он обеспечивает доступ к операциям с
    пользователями, используя объекты UserDao для выполнения конкретных операций с БД. Методы этого класса позволяют
    добавлять, удалять, обновлять пользователей, а также получать всех пользователей или конкретного пользователя по
    идентификатору.

 1. @Service
    public class UserServiceImpl implements UserService:
                               в этой строке мы объявляем класс "UserServiceImpl", помечаем его аннотацией "@Service"
                               и реализуем в нём интерфейс "UserService", где:

           @Service - эта аннотация в Spring Framework используется для обозначения класса как сервиса. Класс,
                      помеченный аннотацией @Service, обычно представляет бизнес-логику приложения и выполняет
                      определенные задачи, такие как обработка данных, взаимодействие с базой данных, или другие
                      операции, необходимые для функционирования приложения. Эта аннотация позволяет Spring автоматически
                      обнаруживать класс как компонент и управлять его жизненным циклом, внедрять его в другие
                      компоненты и обеспечивать другие функциональные возможности, связанные с управлением сервисами.
          UserService - это интерфейс предоставляющий абстракцию для реализации абстрактных методов в классе
                        "UserServiceImpl".

------------------------------------------------------------------------------------------------------------------------

 2. private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }
         В этих строках мы объявляем переменную "userDao" и конструктор, через который будет инициализироваться эта
    перменная. Когда Spring увидет аннотацию @Service, то данный класс будет рассматриваться Spring как компонент.
    Поэтому Spring автоматически создаст и настроит объект типа UserDao в контексте внедрения зависимостей (dependency
    injection context) и передан в конструктор класса UserServiceImpl для инициализации переменной "userDao".

------------------------------------------------------------------------------------------------------------------------

 3. @Override
    @Transactional
    public void addUser(User user) {
        userDao.addUser(user);
    }
         Этот метод addUser(User user) в классе UserServiceImpl использует объект userDao для добавления нового
    пользователя в базу данных. Аннотация @Transactional указывает, что этот метод должен выполняться в рамках
    транзакции, что гарантирует, что либо все операции в этом методе будут выполнены успешно, либо ни одна из них не
    будет выполнена.

------------------------------------------------------------------------------------------------------------------------

 4. @Override
    @Transactional
    public void updateUser(User user) {
        userDao.updateUser(user);
    }
         Этот метод updateUser(User user) в классе UserServiceImpl использует объект userDao для обновления информации о
    пользователе в базе данных. Аннотация @Transactional указывает, что этот метод должен выполняться в рамках
    транзакции, что гарантирует ... ... целостность данных: либо все операции в этом методе будут выполнены успешно,
    либо ни одна из них не будет выполнена.

 5. @Override
    @Transactional
    public void deleteUserById(Long id) {
        userDao.deleteUserById(id);
    }
         Этот метод deleteUserById(Long id) в классе UserServiceImpl использует объект userDao для удаления
    пользователя из базы данных. Аннотация @Transactional указывает, что этот метод должен выполняться в рамках
    транзакции, что гарантирует ... ... целостность данных: либо все операции в этом методе будут выполнены успешно,
    либо ни одна из них не будет выполнена.

------------------------------------------------------------------------------------------------------------------------

 6. @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }
         Метод getAllUsers() в классе UserServiceImpl использует объект userDao для извлечения списка всех пользователей
    из базы данных. Аннотация @Transactional(readOnly = true) указывает, что этот метод должен выполняться в рамках
    транзакции только для чтения, что означает, что он не будет изменять данные в базе.

 7. @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }
         Метод getUserById(Long id) в классе UserServiceImpl использует объект userDao для получения пользователя из
    базы данных по его идентификатору.

-----------------------------------------------------------------------------------------------------------------------

         Когда метод помечен аннотацией @Transactional, это означает, что все операции, выполняемые внутри этого метода,
    будут образовывать одну транзакцию в базе данных. Транзакция - это логическая единица работы, которая гарантирует,
    что либо все операции внутри нее будут выполнены успешно, либо ни одна из них не будет выполнена, обеспечивая
    целостность данных.

    */