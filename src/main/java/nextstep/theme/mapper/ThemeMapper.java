package nextstep.theme.mapper;

import java.util.List;
import nextstep.theme.domain.Theme;
import nextstep.theme.dto.ThemeResponse;
import nextstep.theme.entity.ThemeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ThemeMapper {

    ThemeMapper INSTANCE = Mappers.getMapper(ThemeMapper.class);

    default ThemeResponse domainToDto(Theme theme) {
        if (theme == null) {
            return null;
        }

        Long id = theme.getId();
        String name = theme.getName();
        String description = theme.getDescription();
        int price = theme.getPrice();

        return new ThemeResponse(id, name, description, price);
    }

    List<ThemeResponse> domainListToResponseDtoList(List<Theme> themes);

    ThemeEntity domainToEntity(Theme theme);

    Theme entityToDomain(ThemeEntity themeEntity);

    List<Theme> entityListToDomainList(List<ThemeEntity> themeEntities);
}
