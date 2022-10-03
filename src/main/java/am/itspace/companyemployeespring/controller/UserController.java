package am.itspace.companyemployeespring.controller;

import am.itspace.companyemployeespring.entity.User;
import am.itspace.companyemployeespring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public String usersPage(ModelMap modelMap) {
        List<User> all = userRepository.findAll();
        modelMap.addAttribute("users", all);
        return "users";
    }

    @GetMapping("/register")
    public String addUserPage() {
        return "register";
    }

    @PostMapping("/register")
    public String addUser(@ModelAttribute User user) {
        Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
        if (!byEmail.isPresent()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
        return "redirect:/";
    }
}