package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserService userService, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String adminPanel(Principal principal, Model model) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.findAll());
        return "admin";
    }

    @GetMapping("/getEditUser")
    @ResponseBody
    public User getEditUser(long id) {
        return userService.getUser(id);
    }

    @GetMapping("/add_new_user")
    public String addNewUser(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("role", user.getRoles());
        model.addAttribute("allRoles", roleService.findAll());
        return "new-user";
    }

    @PostMapping("/save_user")
    public String saveUser(@ModelAttribute("newUser") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/update")
    public String update(@ModelAttribute("editUser") User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete_user")
    public String deleteUser(@ModelAttribute("delUser") User user) {
        userService.deleteUser(user);
        return "redirect:/admin";
    }
}