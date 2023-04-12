package bs.boardservice.controller;

import bs.boardservice.application.SessionConst;
import bs.boardservice.application.form.PostForm;
import bs.boardservice.application.service.PostService;
import bs.boardservice.domain.Post;
import bs.boardservice.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @GetMapping
    public String postList(Model model) {
        model.addAttribute("list", postService.findAll());
        return "board/boardmain";
    }

    @GetMapping("/{postId}")
    public String postView(@PathVariable Long postId, Model model) {

        Post post = postService.findOne(postId).orElseThrow();
        model.addAttribute("post", post);

        return "board/post";
    }

    @GetMapping("/register")
    public String registerForm(@ModelAttribute PostForm postForm) {
        return "board/registerform";
    }

    @PostMapping("/register")
    public String register(@Validated @ModelAttribute PostForm postForm,
                           BindingResult bindingResult,
                           @SessionAttribute(name = SessionConst.LOGIN_USER, required = false) User loginUser) {

        if (bindingResult.hasErrors()) {
            return "board/registerform";
        }

        postService.registerPost(postForm.getTitle(), postForm.getContent(), loginUser.getId());

        return "redirect:/post";
    }

    @GetMapping("/{postId}/edit")
    public String editForm(@PathVariable Long postId, Model model) {

        Post post = postService.findOne(postId).orElseThrow();

        PostForm postForm = new PostForm();
        postForm.setTitle(post.getTitle());
        postForm.setContent(post.getContent());

        model.addAttribute("postForm", postForm);
        model.addAttribute("postId", postId);

        return "board/editform";
    }

    @PostMapping("/{postId}/edit")
    public String edit(@PathVariable Long postId,
                       @Validated @ModelAttribute PostForm postForm,
                       BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "board/editform";
        }

        postService.updatePost(postId, postForm.getTitle(), postForm.getContent());

        return "redirect:/post/{postId}";
    }

    @PostMapping("/{postId}/delete")
    public String delete(@PathVariable Long postId) {
        postService.deleteById(postId);
        return "redirect:/post";
    }
}