    package com.example.demo.controller;

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
            String text =  userService.registration(user);
            model.addAttribute("text", text);
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
                if (user.getIsBlocked()){
                    model.addAttribute("text", "Данный аккаунт заблокирован!");
                    return "wrongAuthorization";
                }

                model.addAttribute("name", user.getName());
                model.addAttribute("balance", user.getBalance());
                model.addAttribute("login", user.getLogin());
                model.addAttribute("password", user.getPassword());
                return "mainUser";
            }else{
                model.addAttribute("text", "Неверный логин или пароль!");
                return "wrongAuthorization";
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
            return "change_password";
        }


        @PutMapping("/change_password")
        public String changePassword(@RequestParam String login, @RequestParam String oldPassword, @RequestParam String newPassword, @RequestParam String confirmPassword, Model model) {
            model.addAttribute("text", userService.changePassword(login, oldPassword, newPassword, confirmPassword));
            return "info";
        }

        @GetMapping("/delete/{login}")
        public String deleteUser(@PathVariable String login, Model model) {
            model.addAttribute("login", login);
            return "delete";
        }

        @DeleteMapping("/delete")
        public String deleteUserConfirm(@RequestParam String login, @RequestParam String password, Model model) {
            model.addAttribute("text", userService.delete(login, password));
            return "info";
        }
    }
