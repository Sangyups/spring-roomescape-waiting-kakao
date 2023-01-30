package auth.configuration;

import auth.domain.AbstractBaseUser;
import auth.interceptor.AdminInterceptor;
import auth.repository.AuthRepository;
import auth.resolver.LoginMemberArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    private final AuthRepository<? extends AbstractBaseUser> authRepository;

    public WebMvcConfiguration(AuthRepository<? extends AbstractBaseUser> authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/admin/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new LoginMemberArgumentResolver(authRepository));
    }
}