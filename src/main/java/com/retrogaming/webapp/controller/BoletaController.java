package com.retrogaming.webapp.controller;

import com.retrogaming.webapp.dto.BoletaDTO;
import com.retrogaming.webapp.dto.CarritoItem;
import com.retrogaming.webapp.dto.DetalleBoletaDTO;
import com.retrogaming.webapp.entity.Boleta;
import com.retrogaming.webapp.entity.Usuario;
import com.retrogaming.webapp.service.BoletaService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/boletas")
public class BoletaController {

    @Autowired
    private BoletaService boletaService;

    // =========================
    // LISTAR BOLETAS
    // =========================
    @GetMapping
    public String listar(HttpSession session, Model model) {

        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");

        if (u == null || !u.getRol().equals("admin")) {
            return "redirect:/";
        }

        model.addAttribute("boletas", boletaService.listarTodas());

        return "boleta-preview";
    }

    // =========================
    // NUEVA BOLETA
    // =========================
    @GetMapping("/nuevo")
    public String nuevo(HttpSession session, Model model) {

        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");

        if (u == null) return "redirect:/acceso";
        if (u.getRol().equalsIgnoreCase("ADMIN")) return "redirect:/boletas";

        // Tomar el carrito de la sesión
        List<CarritoItem> carrito =
                (List<CarritoItem>) session.getAttribute("carrito");

        if (carrito == null || carrito.isEmpty()) {
            return "redirect:/carrito";
        }

        // Calcular total
        BigDecimal total = carrito.stream()
                .map(i -> i.getPrecio().multiply(BigDecimal.valueOf(i.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);

        return "boleta-preview"; // ← la vista que ya tienes
    }

    // =========================
    // GUARDAR
    // =========================
    @PostMapping("/guardar")
    public String guardar(
            @RequestParam String nombre,
            @RequestParam String dni,
            @RequestParam String metodo,
            HttpSession session,
            Model model
    ) {
        Usuario u = (Usuario) session.getAttribute("usuarioLogueado");
        if (u == null) return "redirect:/acceso";

        List<CarritoItem> carrito =
                (List<CarritoItem>) session.getAttribute("carrito");

        if (carrito == null || carrito.isEmpty()) {
            return "redirect:/carrito";
        }

        BoletaDTO dto = new BoletaDTO();
        dto.setNumero("BOL-" + System.currentTimeMillis());
        dto.setClienteNombre(nombre);
        dto.setClienteDni(dni);
        dto.setMetodoPago(metodo);

        List<DetalleBoletaDTO> detalles = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CarritoItem item : carrito) {
            DetalleBoletaDTO d = new DetalleBoletaDTO();
            d.setNombreProducto(item.getNombre());
            d.setPrecioUnitario(item.getPrecio());
            d.setCantidad(item.getCantidad());
            BigDecimal subtotal = item.getPrecio()
                    .multiply(BigDecimal.valueOf(item.getCantidad()));
            d.setSubtotal(subtotal);
            total = total.add(subtotal);
            detalles.add(d);
        }

        dto.setTotal(total);
        dto.setDetalles(detalles);

        Boleta boleta = boletaService.guardar(dto);
        session.removeAttribute("carrito");

        model.addAttribute("boleta", boleta);
        return "boleta-exito"; // ← necesitas crear esta vista
    }

    // =========================
    // ELIMINAR
    // =========================
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {

        boletaService.eliminar(id);

        return "redirect:/admin/boletas";
    }
}