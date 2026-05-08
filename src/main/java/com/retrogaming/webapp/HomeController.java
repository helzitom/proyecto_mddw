package com.retrogaming.webapp;

import com.retrogaming.webapp.dto.Cliente;
import com.retrogaming.webapp.service.DataService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/roms")
    public String roms(Model model) {
        model.addAttribute("roms", DataService.getRoms());
        return "roms";
    }

    @GetMapping("/emuladores")
    public String emuladores(Model model) {
        model.addAttribute("plataformas", DataService.getEmuladores());
        return "emuladores";
    }

    @GetMapping("/consolas")
    public String consolas(Model model) {
        model.addAttribute("consolas", DataService.getConsolas());
        return "consolas";
    }

    @GetMapping("/carrito")
    public String carrito() {
        return "carrito";

    }

    @GetMapping("/boleta")
    public String boleta(Model model) {

        model.addAttribute("cliente", new Cliente());

        return "boleta";
    }

}