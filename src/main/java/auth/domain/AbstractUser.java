package auth.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class AbstractUser extends AbstractBaseUser {

    private final String name;
    private final String phone;
    private final Role role;

    public AbstractUser(Long id, String username, String password, String name, String phone, Role role) {
        super(id, username, password);
        this.name = name;
        this.phone = phone;
        this.role = role;
    }
}
