package nextstep.theme.repository;

import java.util.List;
import java.util.Optional;
import nextstep.theme.domain.Theme;

public interface ThemeRepository {

    Long save(Theme themeEntity);

    Optional<Theme> findById(Long id);

    List<Theme> findAll();

    int deleteById(Long id);
}
