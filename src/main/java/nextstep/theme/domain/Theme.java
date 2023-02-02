package nextstep.theme.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Theme {

    private Long id;
    private String name;
    private String description;
    private int price;

    public static Theme of(String name, String description, int price) {

        return new Theme(null, name, description, price);
    }
}
