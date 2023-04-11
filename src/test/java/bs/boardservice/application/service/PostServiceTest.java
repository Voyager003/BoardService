package bs.boardservice.application.service;

import bs.boardservice.domain.Post;
import bs.boardservice.domain.User;
import bs.boardservice.infrastructure.repository.PostRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired PostService postService;
    @Autowired PostRepository postRepository;
    @Autowired EntityManager em;

    @Test
    @DisplayName("회원가입 테스트")
    @Transactional
    public void registerTest() throws Exception {

        //given
        User user = User.builder().loginId("rome").build();
        em.persist(user);


        //when
        Long regieteredId = postService.registerPost("title", "content", user.getId());

        //then
        Post post = postRepository.findById(regieteredId).orElseThrow();

        assertThat(post.getTitle()).isEqualTo("title");
        assertThat(post.getContent()).isEqualTo("content");
        assertThat(post.getUser()).isEqualTo(user);
    }

}