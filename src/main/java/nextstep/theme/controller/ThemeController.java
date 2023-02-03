package nextstep.theme.controller;

import java.net.URI;
import java.util.List;
import nextstep.theme.domain.Theme;
import nextstep.theme.dto.ThemeRequest;
import nextstep.theme.dto.ThemeResponse;
import nextstep.theme.mapper.ThemeMapper;
import nextstep.theme.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThemeController {

    private final ThemeService themeService;

    @Autowired
    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }

    @PostMapping("/admin/themes")
    public ResponseEntity createTheme(@RequestBody ThemeRequest themeRequest) {
        Long id = themeService.create(themeRequest.getName(), themeRequest.getDescription(), themeRequest.getPrice());

        return ResponseEntity.created(URI.create("/themes/" + id)).build();
    }

    @GetMapping("/themes")
    public ResponseEntity<List<ThemeResponse>> showThemes() {
        List<Theme> themes = themeService.findAll();

        return ResponseEntity.ok().body(ThemeMapper.INSTANCE.domainListToResponseDtoList(themes));
    }

    @DeleteMapping("/admin/themes/{id}")
    public ResponseEntity deleteTheme(@PathVariable Long id) {
        themeService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
