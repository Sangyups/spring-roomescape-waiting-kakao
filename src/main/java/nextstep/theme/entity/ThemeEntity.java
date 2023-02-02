package nextstep.theme.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ThemeEntity {

    private Long id;
    private String name;
    private String description;
    private int price;
}