package bs.boardservice.infrastructure.repository;

import bs.boardservice.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    // 회원 저장
    public void save(User user) {
        em.persist(user);
    }

    // 회원 조회
    public Optional<User> findOne(Long id) {
        User user = em.find(User.class, id);
        return Optional.ofNullable(user);
    }

    public List<User> findAll() {
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public Optional<User> findByLoginId(String loginId) {
        List<User> result = em.createQuery("select u from User u where u.loginId = :loginId", User.class)
                .setParameter("loginId", loginId)
                .getResultList();
        return result.stream().findAny();
    }
}
