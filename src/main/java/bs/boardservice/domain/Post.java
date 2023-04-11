package bs.boardservice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
public class Post {
    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime createdDate; // 게시물 작성 시간
    private LocalDateTime modifiedDate; // 게시물 수정 시간

    public Post() {
    }

    @Builder
    public Post(String title, String content, User user,  LocalDateTime createdDate) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.createdDate = createdDate;
    }

    // 게시물 작성
    public static Post createPost(String title, String content, User user) {
        return Post.builder()
                .title(title).content(content).user(user)
                .createdDate(LocalDateTime.now())
                .build();
    }
}
