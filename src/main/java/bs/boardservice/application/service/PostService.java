package bs.boardservice.application.service;

import bs.boardservice.domain.Post;
import bs.boardservice.domain.User;
import bs.boardservice.infrastructure.repository.PostRepository;
import bs.boardservice.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 게시글 등록
    @Transactional
    public Long registerPost(String title, String content, Long userId) {
        User user = userRepository.findOne(userId).orElseThrow();

        Post createdPost = Post.createPost(title, content, user);
        postRepository.save(createdPost);
        return createdPost.getId();
    }

    // 게시글 조회(단건, 전체)
    public Optional<Post> findOne(Long id) {
        return postRepository.findById(id);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    // 게시글 삭제
    @Transactional
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }
}
