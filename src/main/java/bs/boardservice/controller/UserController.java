package bs.boardservice.controller;

import bs.boardservice.application.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {

    @GetMapping("/auth/join")
    public String join(Model model) {
        model.addAttribute("UserDto", new UserDto());
        return "signup";
    }

    @GetMapping("/auth/login")
    public String login() {
        return "signin";
    }
}
