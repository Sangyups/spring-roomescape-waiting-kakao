package auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SuperBuilder
@Getter
@AllArgsConstructor
public class UserDetails {

    private final Long id;
    private final String name;
    private final String phone;
    private final String username;
    private final String role;
    private String password;

    public final boolean matchPassword(UserDetails target) {
        System.out.println(target.getPassword());
        System.out.println(this.password);
        System.out.println(PasswordUtil.matches(target.getPassword(), this.password));
        return PasswordUtil.matches(target.getPassword(), this.password);
    }

    public final void encryptPassword() {
        this.password = PasswordUtil.encrypt(this.password);
    }

    private static final class PasswordUtil {

        private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        private PasswordUtil() {
        }

        public static boolean matches(String rawPassword, String encryptedPassword) {

            return passwordEncoder.matches(rawPassword, encryptedPassword);
        }

        public static String encrypt(String password) {

            return passwordEncoder.encode(password);
        }
    }
}
