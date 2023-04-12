package bs.boardservice.controller;

import bs.boardservice.application.SessionConst;
import bs.boardservice.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class HomeController {

    @GetMapping("/")
    public String loginHome(@SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User loginUser,
                            Model model) {

        if (loginUser == null) {
            return "home";
        }

        model.addAttribute("user", loginUser);
        return "users/signin_completed";
    }
}

