package com.retrogaming.webapp.config;

import com.retrogaming.webapp.interceptor.AdminInterceptor;
import com.retrogaming.webapp.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // ✅ TODOS deben estar logueados para estas rutas
        registry.addInterceptor(new AuthInterceptor())
                .excludePathPatterns(
                        "/",
                        "/acceso",
                        "/login",
                        "/registro",
                        "/logout",
                        "/recuperar",
                        "/roms",
                        "/css/**",
                        "/js/**",
                        "/img/**"
                );

        // ✅ SOLO ADMIN: listar y eliminar boletas
        registry.addInterceptor(new AdminInterceptor())
                .addPathPatterns(
                        "/admin/**",
                        "/productos/admin/**",
                        "/usuarios/**",
                        "/boletas",
                        "/boletas/eliminar/*"
                );
    }
}