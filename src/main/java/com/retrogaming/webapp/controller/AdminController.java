package com.retrogaming.webapp.controller;

import com.retrogaming.webapp.entity.Usuario;
import com.retrogaming.webapp.entity.Boleta;
import com.retrogaming.webapp.service.UsuarioService;
import com.retrogaming.webapp.service.BoletaService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BoletaService boletaService;

    // =========================
    // PANEL PRINCIPAL (USUARIOS)
    // =========================
    @GetMapping
    public String admin(Model model, HttpSession session) {

        Usuario usuario =
                (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null || !usuario.getRol().equalsIgnoreCase("ADMIN")) {
            return "redirect:/acceso";
        }

        List<Usuario> usuarios = usuarioService.listarTodos();

        long activos = usuarios.stream().filter(Usuario::isActivo).count();
        long bloqueados = usuarios.size() - activos;

        model.addAttribute("usuarios", usuarios);
        model.addAttribute("totalUsuarios", usuarios.size());
        model.addAttribute("activos", activos);
        model.addAttribute("bloqueados", bloqueados);

        return "admin";
    }

    // =========================
    // TOGGLE USUARIO
    // =========================
    @PostMapping("/toggle/{id}")
    public String toggle(@PathVariable Long id, HttpSession session) {

        Usuario usuario =
                (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null || !usuario.getRol().equalsIgnoreCase("ADMIN")) {
            return "redirect:/acceso";
        }

        usuarioService.toggleEstado(id);

        return "redirect:/admin";
    }

    // =========================
    // ELIMINAR USUARIO
    // =========================
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, HttpSession session) {

        Usuario usuario =
                (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null || !usuario.getRol().equalsIgnoreCase("ADMIN")) {
            return "redirect:/acceso";
        }

        usuarioService.eliminar(id);

        return "redirect:/admin";
    }

    // =========================================================
    // ===================== BOLETAS ADMIN =====================
    // =========================================================

    @GetMapping("/boletas")
    public String boletas(Model model, HttpSession session) {

        Usuario usuario =
                (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null || !usuario.getRol().equalsIgnoreCase("ADMIN")) {
            return "redirect:/acceso";
        }

        model.addAttribute(
                "boletas",
                boletaService.listarTodas()
        );

        return "admin-boletas";
    }

    @GetMapping("/boletas/{id}")
    public String detalleBoleta(@PathVariable Long id, Model model, HttpSession session) {

        Usuario usuario =
                (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null || !usuario.getRol().equalsIgnoreCase("ADMIN")) {
            return "redirect:/acceso";
        }

        model.addAttribute(
                "boleta",
                boletaService.buscarPorId(id)
        );

        return "admin-boleta-detalle";
    }

    @PostMapping("/boletas/eliminar/{id}")
    public String eliminarBoleta(@PathVariable Long id, HttpSession session) {

        Usuario usuario =
                (Usuario) session.getAttribute("usuarioLogueado");

        if (usuario == null || !usuario.getRol().equalsIgnoreCase("ADMIN")) {
            return "redirect:/acceso";
        }

        boletaService.eliminar(id);

        return "redirect:/admin/boletas";
    }
}