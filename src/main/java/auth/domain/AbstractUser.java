package auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

    @Getter
    @AllArgsConstructor
    public enum Role {

        ADMIN,
        USER,
        NONE,
        ;

        private static final Map<String, Role> roleMapper = new HashMap<>();

        static {
            Arrays.stream(Role.values())
                    .forEach(role -> roleMapper.put(role.name(), role));
        }

        public static Role of(String role) {

            return Objects.requireNonNull(roleMapper.get(role));
        }
    }
}
