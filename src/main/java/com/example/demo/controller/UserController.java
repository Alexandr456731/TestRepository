    package com.example.demo.controller;

    import com.example.demo.exception.UserNotFoundException;
    import com.example.demo.model.User;
    import com.example.demo.service.UserService;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;

    @Controller
    public class UserController {
        private final UserService userService;
        public UserController(UserService userService) {
            this.userService = userService;
        }

        @GetMapping
        public String main() {
            return "main";
        }

        @GetMapping("/registration")
        public String registration() {
            return "registration";
        }

        @PostMapping("/registration")
        public String registrationUser(@ModelAttribute  User user, Model model) {
            userService.registration(user);
            model.addAttribute("message", "Успешно!");
            model.addAttribute("type", "Информация");
            return "info";
        }

        @GetMapping("/authorization")
        public String authorizationUser() {
            return "authorization";
        }

        @PostMapping("/authorization")
        public String authorizationUser(@RequestParam String login, @RequestParam String password, Model model) {
            User user = userService.authorization(login, password);

            if (user != null) {
                model.addAttribute("name", user.getName());
                model.addAttribute("balance", user.getBalance());
                model.addAttribute("login", user.getLogin());
                model.addAttribute("password", user.getPassword());
                return "mainUser";
            }else{
                throw new UserNotFoundException("Ошибка: Пользователь не существует");
            }
        }

        @GetMapping("/option/{login}")
        public String getOption(@PathVariable String login, Model model) {
            model.addAttribute("login", login);
            return "option";
        }

        @GetMapping("/change_password/{login}")
        public String changePassword(@PathVariable String login, Model model) {
            model.addAttribute("login", login);
            model.addAttribute("Сменить пароль");
            return "changePassword";
        }


        @PutMapping("/change_password")
        public String changePassword(@RequestParam String login, @RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String confirmPassword, Model model) {
            userService.changePassword(login, oldPassword, newPassword, confirmPassword);
            model.addAttribute("message", "Успешно!");
            model.addAttribute("type", "Информация");

            return "info";
        }

        @GetMapping("/delete/{login}")
        public String deleteUser(@PathVariable String login, Model model) {
            model.addAttribute("login", login);
            return "delete";
        }

        @DeleteMapping("/delete")
        public String deleteUserConfirm(@RequestParam String login, @RequestParam String password, Model model) {
            userService.delete(login, password);
            model.addAttribute("message", "Успешно!");
            model.addAttribute("type", "Информация");
            return "info";
        }
    }
