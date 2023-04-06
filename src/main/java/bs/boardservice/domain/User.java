package bs.boardservice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "person")
@Getter
public class User {

    @Id @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String loginId;
    @Column(nullable = false)
    private String name;
    private String password;
    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    public User() {
    }

    @Builder
    public User(String loginId, String password, String name) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
    }
}
