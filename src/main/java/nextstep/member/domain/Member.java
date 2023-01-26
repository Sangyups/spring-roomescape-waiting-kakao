package nextstep.member.domain;

import auth.domain.UserDetails;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Member extends UserDetails {

    public Member(Long id, String name, String phone, String username, String password, String role) {
        super(id, name, phone, username, password, role);
    }
}
