package com.retrogaming.webapp.interceptor;

import com.retrogaming.webapp.model.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        String ruta = request.getRequestURI();

        // RUTAS PÚBLICAS
        if (
                ruta.equals("/acceso") ||
                        ruta.equals("/login") ||
                        ruta.equals("/registro") ||
                        ruta.equals("/logout") ||
                        ruta.equals("/recuperar") ||
                        ruta.startsWith("/css") ||
                        ruta.startsWith("/js") ||
                        ruta.startsWith("/img")
        ) {

            return true;
        }

        Usuario usuario =
                (Usuario) request.getSession()
                        .getAttribute("usuarioLogueado");

        // SI NO HAY SESIÓN
        if (usuario == null) {

            response.sendRedirect("/acceso");

            return false;
        }

        return true;
    }
}