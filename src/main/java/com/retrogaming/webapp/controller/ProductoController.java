package com.retrogaming.webapp.controller;

import com.retrogaming.webapp.dto.ProductoDTO;
import com.retrogaming.webapp.service.ProductoService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Controller
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // =========================
    // CATÁLOGOS USUARIO
    // =========================
    @GetMapping("/roms")
    public String roms(Model model) {
        model.addAttribute("productos", productoService.listarPorTipo("ROM"));
        return "roms";
    }

    @GetMapping("/emuladores")
    public String emuladores(Model model) {
        model.addAttribute("productos", productoService.listarPorTipo("EMULADOR"));
        return "emuladores";
    }

    @GetMapping("/consolas")
    public String consolas(Model model) {
        model.addAttribute("productos", productoService.listarPorTipo("CONSOLA"));
        return "consolas";
    }

    // =========================
    // ADMIN
    // =========================
    @GetMapping("/productos/admin")
    public String admin(Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("producto",  new ProductoDTO());
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
            @RequestParam(required = false) String descripcion,
            @RequestParam(value = "imagen", required = false) MultipartFile imagenFile
    ) throws Exception {

        if (stock == null || stock <= 0)
            return "redirect:/productos/admin?error=stock";

        if (precio == null || precio.compareTo(BigDecimal.ZERO) <= 0)
            return "redirect:/productos/admin?error=precio";

        // Si es UPDATE, traer los datos actuales para no pisar imagen
        ProductoDTO dto;
        if (id != null) {
            dto = productoService.buscarPorId(id);
        } else {
            dto = new ProductoDTO();
        }

        dto.setNombre(nombre);
        dto.setTipo(tipo);
        dto.setPrecio(precio);
        dto.setStock(stock);
        dto.setDescripcion(descripcion);

        if (imagenFile != null && !imagenFile.isEmpty()) {
            dto.setImagen(imagenFile.getBytes());
        }

        productoService.guardar(dto);
        return "redirect:/productos/admin";
    }

    // =========================
    // EDITAR
    // =========================
    @GetMapping("/productos/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("productos", productoService.listarTodos());
        model.addAttribute("producto",  productoService.buscarPorId(id));
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
    public ResponseEntity<byte[]> verImagen(@PathVariable Long id) {
        ProductoDTO p = productoService.buscarPorId(id);

        if (p.getImagen() == null || p.getImagen().length == 0) {
            return ResponseEntity.notFound().build();
        }

        // Detectar PNG por firma de bytes
        String contentType = "image/jpeg";
        byte[] img = p.getImagen();
        if (img.length > 4 &&
                img[0] == (byte) 0x89 &&
                img[1] == (byte) 0x50) {
            contentType = "image/png";
        }

        return ResponseEntity.ok()
                .header("Content-Type", contentType)
                .body(img);
    }
}