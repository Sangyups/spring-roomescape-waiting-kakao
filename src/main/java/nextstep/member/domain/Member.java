package nextstep.member.domain;

import auth.domain.AbstractUser;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Member extends AbstractUser {

    public Member(Long id, String username, String password, String name, String phone, Role role) {
        super(id, username, password, name, phone, role);
    }

    public Member(Long id, String username, String password, String name, String phone, String role) {
        this(id, username, password, name, phone, Role.of(role));
    }
}
