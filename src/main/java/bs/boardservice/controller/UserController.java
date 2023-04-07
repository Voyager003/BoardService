package bs.boardservice.controller;

import bs.boardservice.application.UserForm;
import bs.boardservice.application.service.UserService;
import bs.boardservice.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/auth/join")
    public String createForm(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "users/signup";
    }

    @PostMapping(value = "/auth/join")
    public String create(@Validated @ModelAttribute UserForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "users/signup";
        }

        User user = User.builder()
                .loginId(form.getLoginId())
                .password(form.getPassword())
                .name(form.getName()).build();

        userService.join(user);
        return "redirect:/";
    }

    @GetMapping("/auth/login")
    public String login() {
        return "signin";
    }
}
