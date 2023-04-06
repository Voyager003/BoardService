package bs.boardservice.application.service;


import bs.boardservice.domain.User;
import bs.boardservice.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired UserRepository userRepository;

    @Test
    @DisplayName("회원 가입")
    public void UserServiceTest() throws Exception {

        // given
        User user = User.builder().loginId("userA").password("1234").name("rim").build();

        // when
        Long savedId = userService.join(user);

        // then
        assertThat(user).isEqualTo(userService.findOne(savedId).orElseThrow());
    }

    @Test
    @DisplayName("회원 중복 확인")
    public void UserDuplicate() throws Exception {

        //given
        User user1 = User.builder().loginId("userA").password("1234").name("rim").build();
        User user2 = User.builder().loginId("userA").password("1234").name("rim").build();

        //when
        userService.join(user1);
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> userService.join(user2));

        //then
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 아이디입니다.");
    }
}