package com.retrogaming.webapp.controller;

import com.retrogaming.webapp.entity.Producto;
import com.retrogaming.webapp.entity.Usuario;
import com.retrogaming.webapp.service.ProductoService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    // =========================
    // CATÁLOGOS USUARIO
    // =========================

    @GetMapping("/roms")
    public String roms(Model model) {
        model.addAttribute("productos",
                productoService.listarPorTipo("ROM"));
        return "roms";
    }

    @GetMapping("/emuladores")
    public String emuladores(Model model) {
        model.addAttribute("productos",
                productoService.listarPorTipo("EMULADOR"));
        return "emuladores";
    }

    @GetMapping("/consolas")
    public String consolas(Model model) {
        model.addAttribute("productos",
                productoService.listarPorTipo("CONSOLA"));
        return "consolas";
    }

    // =========================
    // ADMIN
    // =========================

    @GetMapping("/productos/admin")
    public String admin(Model model) {

        model.addAttribute("productos",
                productoService.listarTodos());

        model.addAttribute("producto", new Producto());

        return "productos-admin";
    }

    // =========================
    // GUARDAR (CREATE / UPDATE)
    // =========================

    @PostMapping("/productos/guardar")
    public String guardar(
            @RequestParam(required = false) Long id,
            @RequestParam String nombre,
            @RequestParam String tipo,
            @RequestParam BigDecimal precio,
            @RequestParam Integer stock,
            @RequestParam(value = "imagen", required = false) MultipartFile imagenFile
    ) throws Exception {

        if (stock == null || stock <= 0) {
            return "redirect:/productos/admin?error=stock";
        }

        if (precio == null || precio.doubleValue() <= 0) {
            return "redirect:/productos/admin?error=precio";
        }

        Producto p;

        // UPDATE
        if (id != null) {
            p = productoService.buscarPorId(id);
        } else {
            p = new Producto();
        }

        p.setNombre(nombre);
        p.setTipo(tipo);
        p.setPrecio(precio);
        p.setStock(stock);

        if (imagenFile != null && !imagenFile.isEmpty()) {
            p.setImagen(imagenFile.getBytes());
        }

        productoService.guardar(p);

        return "redirect:/productos/admin";
    }

    // =========================
    // EDITAR
    // =========================

    @GetMapping("/productos/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        model.addAttribute("productos",
                productoService.listarTodos());

        model.addAttribute("producto",
                productoService.buscarPorId(id));

        return "productos-admin";
    }

    // =========================
    // ELIMINAR
    // =========================

    @GetMapping("/productos/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {

        productoService.eliminar(id);

        return "redirect:/productos/admin";
    }

    // =========================
    // IMAGEN
    // =========================

    @GetMapping("/producto/imagen/{id}")
    @ResponseBody
    public byte[] verImagen(@PathVariable Long id) {

        Producto p = productoService.buscarPorId(id);

        return (p.getImagen() != null)
                ? p.getImagen()
                : new byte[0];
    }
}