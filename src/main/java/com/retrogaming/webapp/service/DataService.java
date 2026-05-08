package com.retrogaming.webapp.service;


import java.util.*;

public class DataService {

    // ===== ROMS =====
    public static List<Map<String, Object>> getRoms() {

        List<Map<String, Object>> categorias = new ArrayList<>();

        // ===== SNES =====
        Map<String, Object> snes = new HashMap<>();
        snes.put("nombre", "SNES");

        List<Map<String, Object>> snesJuegos = new ArrayList<>();
        snesJuegos.add(crearJuego("ActRaiser", 5.90, "/img/ActRaiser.png"));
        snesJuegos.add(crearJuego("Adventures of Batman", 5.90, "/img/adventures batman.png"));
        snesJuegos.add(crearJuego("Boxing Legends", 9.90, "/img/boxing.png"));
        snesJuegos.add(crearJuego("Luigi Adventure", 6.90, "/img/luigi adventure.png"));
        snesJuegos.add(crearJuego("Magic Boy", 4.90, "/img/Magic Boy.png"));

        snes.put("juegos", snesJuegos);
        categorias.add(snes);

        // ===== GBA =====
        Map<String, Object> gba = new HashMap<>();
        gba.put("nombre", "Game Boy Advance");

        List<Map<String, Object>> gbaJuegos = new ArrayList<>();
        gbaJuegos.add(crearJuego("Pokémon Pinball", 8.90, "/img/pokemon pinball.webp"));
        gbaJuegos.add(crearJuego("Dr Mario", 8.90, "/img/Dr Mario.webp"));
        gbaJuegos.add(crearJuego("Zelda A Link to the Past", 4.90, "/img/Zelda A link.webp"));
        gbaJuegos.add(crearJuego("Yoshi Island", 5.90, "/img/yoshi island.webp"));
        gbaJuegos.add(crearJuego("Doom 2", 8.90, "/img/Doom 2.webp"));

        gba.put("juegos", gbaJuegos);
        categorias.add(gba);

        Map<String, Object> psp = new HashMap<>();
        psp.put("nombre", "PlayStation Portable (PSP)");

        List<Map<String, Object>> pspJuegos = new ArrayList<>();
        pspJuegos.add(crearJuego("God of War: Chains of Olympus", 12.90, "/img/GOD_OF_WAR.JPG"));
        pspJuegos.add(crearJuego("Grand Theft Auto: Liberty City Stories", 11.90, "/img/GrandTheftAutoViceCity.JPG"));
        pspJuegos.add(crearJuego("Crisis Core: Final Fantasy VII", 15.90, "/img/CrisisFinalFantasyVII.JPG"));
        pspJuegos.add(crearJuego("Monster Hunter Freedom Unite", 14.90, "/img/MONSTER.JPG"));
        pspJuegos.add(crearJuego("Daxter", 9.90, "/img/Daxter.JPG"));

        psp.put("juegos", pspJuegos);
        categorias.add(psp);

        return categorias;
    }

    // ===== EMULADORES =====
    public static List<Map<String, Object>> getEmuladores() {

        List<Map<String, Object>> plataformas = new ArrayList<>();

        // ===== PC =====
        Map<String, Object> pc = new HashMap<>();
        pc.put("nombre", "PC");

        List<Map<String, Object>> emusPC = new ArrayList<>();
        emusPC.add(crearJuego("RetroArch", 20.00, "/img/RetroArch.png"));
        emusPC.add(crearJuego("Dolphin", 15.00, "/img/dolphin.jpeg"));
        emusPC.add(crearJuego("DeSmuME", 15.00, "/img/Desmume.png"));

        pc.put("items", emusPC);
        plataformas.add(pc);

        // ===== Android =====
        Map<String, Object> android = new HashMap<>();
        android.put("nombre", "Android");

        List<Map<String, Object>> emusAndroid = new ArrayList<>();
        emusAndroid.add(crearJuego("DraStic", 25.00, "/img/drastic.avif"));
        emusAndroid.add(crearJuego("mGBA", 15.00, "/img/mgba.png"));
        emusAndroid.add(crearJuego("ePSXe", 25.00, "/img/epsxe.png"));

        android.put("items", emusAndroid);
        plataformas.add(android);

        return plataformas;
    }

    // ===== CONSOLAS =====
    public static List<Map<String, Object>> getConsolas() {

        List<Map<String, Object>> consolas = new ArrayList<>();

        // ===== Nintendo 3DS =====
        Map<String, Object> n3ds = new HashMap<>();
        n3ds.put("nombre", "Nintendo 3DS");

        List<Map<String, Object>> juegos3ds = new ArrayList<>();
        juegos3ds.add(crearJuego("Mario Kart 7", 22.90, "/img/mario.webp"));
        juegos3ds.add(crearJuego("Megaman 5", 12.90, "/img/megaman.webp"));
        juegos3ds.add(crearJuego("General Rex", 24.90, "/img/general rex.webp"));
        juegos3ds.add(crearJuego("Donkey Kong", 21.90, "/img/donkey kong.webp"));
        juegos3ds.add(crearJuego("Madagascar", 11.90, "/img/madagascar.webp"));

        n3ds.put("juegos", juegos3ds);
        consolas.add(n3ds);

        // ===== PlayStation 1 =====
        Map<String, Object> ps1 = new HashMap<>();
        ps1.put("nombre", "PlayStation 1");

        List<Map<String, Object>> juegosPS1 = new ArrayList<>();
        juegosPS1.add(crearJuego("Crash Bandicoot", 18.90, "/img/Crash_bandicoot.JPG"));
        juegosPS1.add(crearJuego("Final Fantasy VII", 25.90, "/img/Final_Fantasy_VII_Box_Art.jpg"));
        juegosPS1.add(crearJuego("Resident Evil 2", 22.90, "/img/Resident_Evil_2.jpg"));
        juegosPS1.add(crearJuego("Tekken 3", 20.90, "/img/tekken.jpg"));
        juegosPS1.add(crearJuego("Metal Gear Solid", 27.90, "/img/Metal_Gear_Solid.jpg"));

        ps1.put("juegos", juegosPS1);
        consolas.add(ps1);

        // ===== Nintendo DS =====
        Map<String, Object> nds = new HashMap<>();
        nds.put("nombre", "Nintendo DS");

        List<Map<String, Object>> juegosDS = new ArrayList<>();
        juegosDS.add(crearJuego("PES 2010", 20.00, "/img/GOAT.JPG"));
        juegosDS.add(crearJuego("TNA Impact", 20.00, "/img/impact.webp"));
        juegosDS.add(crearJuego("Bejeweled 3", 20.00, "/img/bejeweled.webp"));
        juegosDS.add(crearJuego("Megamente", 20.00, "/img/megamente.webp"));
        juegosDS.add(crearJuego("Alvin y las Ardillas", 20.00, "/img/alvin.webp"));

        nds.put("juegos", juegosDS);
        consolas.add(nds);

        // ===== Arcade =====
        Map<String, Object> arcade = new HashMap<>();
        arcade.put("nombre", "Arcade");

        List<Map<String, Object>> juegosArcade = new ArrayList<>();
        juegosArcade.add(crearJuego("Pac-Man", 15.90, "/img/Pacman.jpg"));
        juegosArcade.add(crearJuego("Street Fighter II", 20.90, "/img/Street_Fither.jpg"));
        juegosArcade.add(crearJuego("Mortal Kombat", 22.90, "/img/Mortal_Kombat.jpg"));
        juegosArcade.add(crearJuego("Metal Slug", 19.90, "/img/Metal_Slug.jpg"));
        juegosArcade.add(crearJuego("Galaga", 14.90, "/img/Galaga.jpg"));

        arcade.put("juegos", juegosArcade);
        consolas.add(arcade);

        return consolas;
    }

    // ===== MÉTODO AUXILIAR =====
    private static Map<String, Object> crearJuego(String nombre, double precio, String imagen) {
        Map<String, Object> juego = new HashMap<>();
        juego.put("nombre", nombre);
        juego.put("precio", precio);
        juego.put("imagen", imagen);
        return juego;
    }
}