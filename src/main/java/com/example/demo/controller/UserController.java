    package com.example.demo.controller;

    import com.example.demo.model.User;
    import com.example.demo.service.UserService;
    import org.springframework.web.bind.annotation.*;

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
                return "<h2>Здравствуйте, " + user.getName() + "!</h2>" +
                        "Баланс: " + user.getBalance().toString() + "<br>" +
                        "<button type=\"button\" onclick=\"alert('В разработке')\">Пополнить</button><br><br>" +
                        "<a href=\"/option/" + login + "?password=\"" + password + "\"><button type=\"button\">Настройки</button></a><br><br>" +
                        "<a href=\"/\"><button type=\"button\">Выход</button></a>";
            }else{
                return "<h1>Неверный логин или пароль!</h1>" + authorizationUser();
            }
        }

        @GetMapping("/option/{login}")
        public String getOption(@PathVariable String login) {
            return "<a href=\"/delete/" + login + "\"><button type=\"button\">Удалить аккаунт</button></a><br><br>" +
                    "<a href=\"/change_password/" + login + "\"><button type=\"button\">Сменить пароль</button></a><br><br><br>" +
                    "<a href=\"/\"><button type=\"button\">На главную</button></a>";
        }

        @GetMapping("/change_password/{login}")
        public String changePassword(@PathVariable String login) {
            return "<form method=\"post\" action=\"/change_password\" onsubmit=\"return checkPassword()\">" +
                    "<input type=\"hidden\" name=\"_method\" value=\"put\">" +
                    "<input type=\"hidden\" name=\"login\" value=\"" + login + "\">" +
                    "<label for=\"old_password\">Старый пароль: </label>" +
                    "<input type=\"password\" id=\"old_password\" name=\"oldPassword\" required><br><br>" +

                    "<label for=\"new_password\">Новый пароль: </label>" +
                    "<input type=\"password\" id=\"new_password\" name=\"newPassword\" required><br><br>" +

                    "<label for=\"confirm_password\">Подтвердить пароль: </label>" +
                    "<input type=\"password\" id=\"confirm_password\" name=\"confirmPassword\" required><br><br>" +

                    "<button type=\"submit\">Сменить</button>" +
                    "</form>";
        }


        @PutMapping("/change_password")
        public String changePassword(@RequestParam String login, @RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String confirmPassword) {
            return userService.changePassword(login, oldPassword, newPassword, confirmPassword) + "" +
                    "<br><br><a href=\"/\"><button>На главную</button></a>";
        }

        @GetMapping("/delete/{login}")
        public String deleteUser(@PathVariable String login) {
            return "<form method=\"post\" action=\"/delete\">" +
                    "<input type=\"hidden\" name=\"_method\" value=\"delete\">" +
                    "<input type=\"hidden\" required name=\"login\" value=\"" + login + "\">" +
                    "<label for=\"password\">Пароль: </label>" +
                    "<input id=\"password\" type=\"password\" name=\"password\" required>\t" +
                    "<button type=\"submit\">Подтвердить</button>" +
                    "</form><br><br>" +
                    "<a href=\"/\"><button type=\"button\">На главную</button></a>";
        }

        @DeleteMapping("/delete")
        public String deleteUserConfirm(@RequestParam String login, @RequestParam String password) {
            return userService.delete(login, password) + "<br><br><a href=\"/\"><button type=\"button\">На главную</button></a>";
        }
    }
