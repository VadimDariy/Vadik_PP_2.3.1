package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

@Controller
@RequestMapping
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String getIndex(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    @GetMapping(value = "/add")
    public String addForm(ModelMap model) {
        model.addAttribute("users", new User());
        return "add";
    }

    @PostMapping(value = "/add")
    public String addSumbit(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/";
    }

    @PostMapping(value = "/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/";
    }

    @GetMapping(value = "/edit")
    public String editPage(@RequestParam("id") Long id, ModelMap model) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PatchMapping(value = "/edit")
    public String editSubmit(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/";
    }
}


/*
 1.      Объявляем класс "public class UserController" с пометкой аннотации @Controller и @RequestMapping. Это будет
     означать, что Spring будет понимать, что данный класс является:

    1.1 Компонентом и частью механизма обработки запросов в Spring MVC (Model-View-Controller/Контроллер
        представления модели), который обрабатывает HTTP-запросы; (@Controller)
    1.2 Обработчиком поступающих определенных запросов, на что указывает аннотация @RequestMapping над классом, это
        говорит о том, что Spring будет сопостовлять поступающие запросы с адресами, указанными в аннотациях над
        методами этого класса, и соответствующий метод будет вызван для обработки этого запроса.

------------------------------------------------------------------------------------------------------------------------

 2. private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
         В этих сроках объявляем переменную "userService", которая будет инициализированна через конструктор
    "UserController(UserService userService)", где в параметре в качестве аргументов данный консструктор принимает
    конкретные данные из объекта, который автоматически создаётся в контейнере Spring Framework.

------------------------------------------------------------------------------------------------------------------------

 3. @GetMapping(value = "/")
    public String getIndex(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }
    3.1 @GetMapping:
        эта аннотация  указывает, что метод контроллера обрабатывает HTTP GET-запросы. Значение "/"" в скобках
        указывает на конкретный URL-адрес, по которому будет доступен этот метод. Таким образом, данный метод будет
        обрабатывать GET-запросы по корневому URL-адресу нашего веб-приложения.
        GET-запросы - метод HTTP-протокола для запроса данных с сервера. Они используются для получения данных, таких
        как веб-страницы, изображения или другие ресурсы, путем ввода URL-адреса в веб-браузер и нажатия Enter.

    3.2 public String addForm(ModelMap model):
        Этот метод принимает объект ModelMap в качестве параметра. В Spring Framework объект ModelMap используется для
        передачи данных от контроллера к представлению (view).

    3.3 model.addAttribute("users", userService.getAllUsers());
        Метод addForm добавляет новый объект User в ModelMap под именем "users".

    3.4 return "index";
        возвращает строку "add", которая представляет имя представления (view), которое будет отображено пользователю.

------------------------------------------------------------------------------------------------------------------------

 4. @GetMapping(value = "/add")
    public String addForm(ModelMap model) {
        model.addAttribute("users", new User());
        return "add";
    }
         Этот метод обрабатывает GET-запросы по адресу "/add". Когда пользователь делает GET-запрос на этот адрес, метод
    addForm(ModelMap model) будет вызван. Внутри этого метода создается новый объект User и добавляется в модель под
    именем "users". Затем метод возвращает строку "add", которая представляет собой имя представления (view), которое
    будет отображено пользователю.

------------------------------------------------------------------------------------------------------------------------

 5. @PostMapping(value = "/add")
    public String addSumbit(@ModelAttribute User user) {
        userService.addUser(user);
        return "redirect:/";
    }

         Этот метод обрабатывает POST-запросы по адресу "/add". Когда пользователь отправляет форму на этот адрес,
    содержащую данные пользователя, метод addSubmit(@ModelAttribute User user) будет вызван. Аннотация @ModelAttribute
    указывает на то, что объект User будет создан на основе данных, отправленных пользователем, и передан в этот метод.
    Затем вызывается метод userService.addUser(user) для добавления пользователя, и после этого происходит
    перенаправление на корневую страницу приложения.
         POST-запрос - это метод HTTP-протокола, который используется для отправки данных на сервер для создания или
    обновления ресурсов. Когда мы отправляем форму на веб-странице, браузер использует POST-запрос для отправки данных
    на сервер. Этот метод позволяет передавать большие объемы данных и различные типы данных, такие как изображения или
    файлы, на сервер для обработки.

------------------------------------------------------------------------------------------------------------------------

 6. @PostMapping(value = "/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/";
    }
         Этот метод обрабатывает POST-запросы по адресу "/delete". Когда пользователь отправляет запрос на удаление
    пользователя с определенным идентификатором, метод deleteUser(@RequestParam("id") Long id) будет вызван. Аннотация
    @RequestParam("id") указывает на то, что идентификатор пользователя будет передан в метод через параметр запроса с
    именем "id". Затем вызывается метод userService.deleteUserById(id) для удаления пользователя с указанным
    идентификатором, и после этого происходит перенаправление на корневую страницу приложения.

------------------------------------------------------------------------------------------------------------------------

 7. @GetMapping(value = "/edit")
    public String editPage(@RequestParam("id") Long id, ModelMap model) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }
         Этот метод обрабатывает GET-запросы по адресу "/edit". Когда пользователь делает GET-запрос на этот адрес с
    определенным идентификатором пользователя, метод editPage(@RequestParam("id") Long id, ModelMap model) будет вызван.
    Внутри этого метода ... ... получает пользователя с указанным идентификатором с помощью userService.getUserById(id)
    и добавляет его в модель под именем "user". Затем метод возвращает строку "edit", которая представляет собой имя
    представления (view), которое будет отображено пользователю.

------------------------------------------------------------------------------------------------------------------------

 8. @PatchMapping(value = "/edit")
    public String editSubmit(@ModelAttribute User user) {
        userService.updateUser(user);
        return "redirect:/";
    }
         Этот метод обрабатывает PATCH-запросы по адресу "/edit". Когда пользователь отправляет PATCH-запрос для
    обновления информации о пользователе, содержащую данные пользователя, метод editSubmit(@ModelAttribute User user)
    будет вызван. Аннотация @ModelAttribute указывает на то, что объект User будет создан на основе данных, отправленных
    пользователем, и передан в этот метод. Затем вызывается метод userService.updateUser(user) для обновления информации
    о пользователе, и после этого происходит перенаправление пользователя на корневую страницу приложения.
         PATCH-запрос - это метод HTTP-протокола, который используется для частичного обновления ресурса на сервере. Он
    аналогичен методу PUT, но в отличие от PUT, который заменяет все предыдущие данные ресурса, PATCH позволяет
    обновлять только определенные части ресурса. Это полезно, когда вы хотите обновить только определенные поля объекта
    на сервере, не затрагивая остальные данные.

------------------------------------------------------------------------------------------------------------------------

         По итогу данный класс обрабатывает HTTP запросы с помощью методов:
                 а) GET-запросы: запрашивает данные с сервера для их получения пользователем;
                 б) POST-запросы: отправляет данные для создания или обновления ресурсов;
                 в) PATCH-запрос: используется для частичного обновления ресурса на сервере;

     */