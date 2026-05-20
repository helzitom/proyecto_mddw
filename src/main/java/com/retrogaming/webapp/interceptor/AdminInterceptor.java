package com.retrogaming.webapp.interceptor;

import com.retrogaming.webapp.entity.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        Usuario usuario =
                (Usuario) request.getSession()
                        .getAttribute("usuarioLogueado");

        // SI NO EXISTE
        if (usuario == null) {

            response.sendRedirect("/acceso");

            return false;
        }

        // SI NO ES ADMIN
        if (!usuario.getRol()
                .equalsIgnoreCase("ADMIN")) {

            response.sendRedirect("/");

            return false;
        }

        return true;
    }
}