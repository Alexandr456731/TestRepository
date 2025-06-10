    package com.example.demo.controller;

    import com.example.demo.repository.User;
    import com.example.demo.service.UserService;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

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
            return "<form method=\"post\" action=\"/authorization\">" +
                    "<input type=\"text\"name=\"login\" required hidden value=\"" + login + "\">" +
                    "<input type=\"password\"name=\"password\" required hidden value=\"" + password + "\">" +
                    "<button type=\"submit\">Назад</button>" +
                    "</form>";
        }
    }
