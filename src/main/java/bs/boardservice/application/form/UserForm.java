package bs.boardservice.application.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserForm {

    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9-_]{4,20}$", message = "아이디는 특수문자를 제외한 4~20자리여야 합니다.")
    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String loginId;

    @Pattern(regexp = "^[가-힣\\s]*$", message = "이름은 한글만 허용합니다.")
    @NotBlank(message = "이름은 필수 입력 값입니다.")
    private String name;

    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    @NotBlank(message = "패스워드는 필수 입력 값입니다.")
    private String password;
}
