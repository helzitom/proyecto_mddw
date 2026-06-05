package com.retrogaming.webapp.controller;

import com.retrogaming.webapp.dto.BoletaDTO;
import com.retrogaming.webapp.dto.RegistroDTO;
import com.retrogaming.webapp.model.Usuario;
import com.retrogaming.webapp.service.BoletaService;
import com.retrogaming.webapp.service.UsuarioService;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsuarioService usuarioService;
    private final BoletaService boletaService;

    public AdminController(UsuarioService usuarioService,
                           BoletaService boletaService) {
        this.usuarioService = usuarioService;
        this.boletaService  = boletaService;
    }

    private boolean esAdmin(HttpSession session) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        return u != null && u.getRol().equalsIgnoreCase("ADMIN");
    }

    // =========================
    // PANEL PRINCIPAL (USUARIOS)
    // =========================
    @GetMapping
    public String admin(Model model, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/acceso";

        List<RegistroDTO> usuarios = usuarioService.listarTodos();
        long activos    = usuarios.stream().filter(RegistroDTO::isActivo).count();
        long bloqueados = usuarios.size() - activos;

        model.addAttribute("usuarios",      usuarios);
        model.addAttribute("totalUsuarios", usuarios.size());
        model.addAttribute("activos",       activos);
        model.addAttribute("bloqueados",    bloqueados);

        return "admin";
    }

    // =========================
    // TOGGLE USUARIO
    // =========================
    @PostMapping("/toggle/{id}")
    public String toggle(@PathVariable Long id, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/acceso";
        usuarioService.toggleEstado(id);
        return "redirect:/admin";
    }

    // =========================
    // ELIMINAR USUARIO
    // =========================
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/acceso";
        usuarioService.eliminar(id);
        return "redirect:/admin";
    }

    // =========================
    // BOLETAS ADMIN
    // =========================
    @GetMapping("/boletas")
    public String boletas(Model model, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/acceso";
        model.addAttribute("boletas", boletaService.listarTodas());
        return "admin-boletas";
    }

    @GetMapping("/boletas/{id}")
    public String detalleBoleta(@PathVariable Long id,
                                Model model,
                                HttpSession session) {
        if (!esAdmin(session)) return "redirect:/acceso";
        model.addAttribute("boleta", boletaService.buscarPorId(id));
        return "admin-boleta-detalle";
    }

    @PostMapping("/boletas/eliminar/{id}")
    public String eliminarBoleta(@PathVariable Long id, HttpSession session) {
        if (!esAdmin(session)) return "redirect:/acceso";
        boletaService.eliminar(id);
        return "redirect:/admin/boletas";
    }
}