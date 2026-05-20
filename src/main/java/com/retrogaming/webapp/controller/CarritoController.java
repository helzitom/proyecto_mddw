package com.retrogaming.webapp.controller;

import com.retrogaming.webapp.dto.CarritoItem;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    // =========================
    // VER CARRITO
    // =========================
    @GetMapping
    public String verCarrito(HttpSession session, Model model) {

        List<CarritoItem> carrito =
                (List<CarritoItem>) session.getAttribute("carrito");

        // ✔️ SIEMPRE inicializar para evitar NullPointer / 500
        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute("carrito", carrito);
        }

        model.addAttribute("carrito", carrito);

        return "carrito";
    }

    // =========================
    // AGREGAR PRODUCTO
    // =========================
    @PostMapping("/agregar")
    public String agregarProducto(
            @RequestParam String nombre,
            @RequestParam BigDecimal precio,
            @RequestParam(required = false) String imagen,
            HttpSession session
    ) {

        List<CarritoItem> carrito =
                (List<CarritoItem>) session.getAttribute("carrito");

        // ✔️ evitar null session list
        if (carrito == null) {
            carrito = new ArrayList<>();
            session.setAttribute("carrito", carrito);
        }

        // ✔️ fallback imagen
        if (imagen == null || imagen.isBlank()) {
            imagen = "/img/default.png";
        }

        boolean encontrado = false;

        for (CarritoItem item : carrito) {

            // ✔️ evita NullPointer en nombre
            if (item.getNombre() != null &&
                    item.getNombre().equals(nombre)) {

                item.setCantidad(
                        (item.getCantidad() == null ? 0 : item.getCantidad()) + 1
                );

                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            CarritoItem item = new CarritoItem();
            item.setNombre(nombre);
            item.setPrecio(precio);
            item.setCantidad(1);
            item.setImagen(imagen);

            carrito.add(item);
        }

        session.setAttribute("carrito", carrito);

        return "redirect:/carrito";
    }

    // =========================
    // ELIMINAR PRODUCTO
    // =========================
    @GetMapping("/eliminar/{indice}")
    public String eliminarProducto(
            @PathVariable int indice,
            HttpSession session
    ) {

        List<CarritoItem> carrito =
                (List<CarritoItem>) session.getAttribute("carrito");

        if (carrito != null &&
                indice >= 0 &&
                indice < carrito.size()) {

            carrito.remove(indice);
            session.setAttribute("carrito", carrito);
        }

        return "redirect:/carrito";
    }

    // =========================
    // VACIAR CARRITO
    // =========================
    @PostMapping("/vaciar")
    public String vaciarCarrito(HttpSession session) {

        session.removeAttribute("carrito");

        return "redirect:/carrito";
    }
}