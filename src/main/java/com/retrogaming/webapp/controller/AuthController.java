package com.retrogaming.webapp.controller;

import com.retrogaming.webapp.dto.LoginDTO;
import com.retrogaming.webapp.dto.RegistroDTO;
import com.retrogaming.webapp.model.Usuario;
import com.retrogaming.webapp.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // =========================
    // LOGIN
    // =========================
    @GetMapping("/acceso")
    public String acceso(Model model, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario != null) {
            return usuario.getRol().equalsIgnoreCase("ADMIN")
                    ? "redirect:/admin"
                    : "redirect:/";
        }

        model.addAttribute("loginDTO", new LoginDTO());
        return "acceso";
    }

    @PostMapping("/login")
    public String procesarLogin(
            @Valid @ModelAttribute("loginDTO") LoginDTO dto,
            BindingResult result,
            Model model,
            HttpSession session
    ) {
        if (result.hasErrors()) return "acceso";

        Usuario usuario = usuarioService.login(dto);

        if (usuario == null) {
            model.addAttribute("error", "Credenciales incorrectas");
            return "acceso";
        }

        session.setAttribute("usuarioLogueado", usuario);

        return usuario.getRol().equalsIgnoreCase("ADMIN")
                ? "redirect:/admin"
                : "redirect:/";
    }

    // =========================
    // REGISTRO
    // =========================
    @GetMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("registroDTO", new RegistroDTO());
        return "registro";
    }

    @PostMapping("/registro")
    public String guardarRegistro(
            @Valid @ModelAttribute("registroDTO") RegistroDTO dto,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("registroDTO", dto);
            return "registro";
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            model.addAttribute("registroDTO", dto);
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "registro";
        }

        try {
            usuarioService.registrar(dto);
        } catch (Exception e) {
            model.addAttribute("registroDTO", dto);
            model.addAttribute("error", e.getMessage());
            return "registro";
        }

        model.addAttribute("mensaje", "Usuario registrado correctamente");
        return "acceso";
    }

    // =========================
    // LOGOUT
    // =========================
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/acceso";
    }
}