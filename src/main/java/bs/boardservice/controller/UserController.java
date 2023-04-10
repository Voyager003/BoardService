package bs.boardservice.controller;

import bs.boardservice.application.LoginForm;
import bs.boardservice.application.SessionConst;
import bs.boardservice.application.UserForm;
import bs.boardservice.application.service.UserService;
import bs.boardservice.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

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
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "users/signin";
    }

    @PostMapping("/auth/login")
    public String login(@Validated @ModelAttribute("loginForm") LoginForm form,
                        BindingResult result, HttpServletRequest request) {

        if (result.hasErrors()) {
            return "users/signin";
        }

        User loginUser = userService.login(form.getLoginId(), form.getPassword());

        if (loginUser == null) {
            result.reject("loginFail", "아이디 혹은 비밀번호가 맞지 않습니다.");
            return "users/signin";
        }

        // 세션 유지 시, redirect
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_USER, loginUser);

        return "redirect:/";
    }

    @PostMapping("/auth/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
