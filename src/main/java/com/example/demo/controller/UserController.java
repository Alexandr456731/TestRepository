    package com.example.demo.controller;

    import com.example.demo.repository.User;
    import com.example.demo.service.UserService;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;
    import java.util.Optional;

    @RestController
    public class UserController {
        private final UserService userService;
        public UserController(UserService userService) {
            this.userService = userService;
        }

        @GetMapping
        public String main() {
            return "<a href=\"/registration\"><button type=\"button\">регитрация</button></a><br><br>" +
                    "<a href=\"/authorization\"><button type=\"button\">авторизация</button></a><br><br>";
        }

        @GetMapping("/registration")
        public String registration() {
            return "<form method=\"post\">" +
                    "<label for=\"login\">Логин: </label><input type=\"text\" id=\"login\" name=\"login\" required><br><br>" +
                    "<label for=\"password\">Пароль: </label><input type=\"password\" id=\"password\" name=\"password\"><br><br>" +
                    "<label for=\"name\">Имя: </label><input type=\"text\" id=\"name\" name=\"name\"><br><br>" +
                    "<label for=\"email\">Email: </label><input type=\"email\" id=\"email\" name=\"email\" required><br><br>" +
                    "<button type=\"submit\">Отправить</button><button type=\"reset\">Сбросить</button>" +
                    "</form>";
        }

        @PostMapping("/registration")
        public String registrationUser(@ModelAttribute  User user) {
            return userService.registration(user) + "<br><br><a href=\"/\"><button>На главную</button></a>";
        }

        @GetMapping("/authorization")
        public String authorizationUser() {
            return "<form method=\"post\" action=\"/authorization\">" +
                    "<label for=\"login\">Логин: </label><input type=\"text\" id=\"login\"  name=\"login\" required><br><br>" +
                    "<label for=\"password\">Пароль: </label><input type=\"password\" id=\"password\" name=\"password\" required><br><br>" +
                    "<button type=\"submit\">Отправить</button>" +
                    "</form>";
        }

        @PostMapping("/authorization")
        public String authorizationUser(@RequestParam String login, @RequestParam String password) {
            User user = userService.authorization(login, password);
            if (user != null) {
                return "Баланс: " + user.getBalance().toString() + "<br>" +
                        "<button type=\"button\" onclick=\"alert('В разработке')\">Пополнить</button><br><br>" +
                        "<a href='/option/" + login + "?password=\"" + password + "\"'><button type=\"button\">Настройки</button></a><br><br>" +
                        "<a href=\"/\"><button type=\"button\">Выход</button></a>";
            }else{
                return "<h1>Неверный логин или пароль!</h1>" + authorizationUser();
            }
        }

        @GetMapping("/option/{login}")
        public String getOption(@PathVariable String login, @RequestParam String password) {
            return "<a href=\"/delete/" + login + "\"><button type=\"button\">Удалить аккаунт</button><br><br><br></a>" +
                    "<a href=\"/\"><button type=\"button\">На главную</button></a>";
        }

        @GetMapping("/delete/{login}")
        public String deleteUser(@PathVariable String login) {
            return "<form method=\"post\" action=\"/delete/" + login + "\">" +
                    "<label for=\"password\">Пароль: </label>" +
                    "<input id=\"password\" type=\"password\" name=\"password\" required>\t" +
                    "<button type=\"submit\">Подтвердить</button>" +
                    "</form><br><br>" +
                    "<a href=\"/\"><button type=\"button\">На главную</button></a>";

        }

        @PostMapping("/delete/{login}")
        public String deleteUser(@PathVariable String login, @RequestParam String password) {
            return deleteUserConfirm(login, password);
        }

        @DeleteMapping("/delete/{login}")
        public String deleteUserConfirm(@PathVariable String login, @RequestParam String password) {
            return userService.delete(login, password) + "<br><br><a href=\"/\"><button type=\"button\">На главную</button></a>";
        }

        @GetMapping("/admin/test/{login}")
        public String correctTest(@PathVariable String login) {
            return "<form method=\"post\" action=\"/admin/" + login + "\">" +
                    "<label for=\"password\">Пароль: </label>" +
                    "<input id=\"password\" type=\"password\" name=\"password\" required>\t" +
                    "<button type=\"submit\">Подтвердить</button>" +
                    "</form><br><br>" +
                    "<a href=\"/\"><button type=\"button\">На главную</button></a>";

        }

        @GetMapping("/admin/{login}")
        public String correct(@PathVariable String login, @RequestParam String password) {
            return userService.correct(login, password);
        }
    }
