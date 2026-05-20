package com.retrogaming.webapp.service;

import com.retrogaming.webapp.dto.LoginDTO;
import com.retrogaming.webapp.dto.RecuperarDTO;
import com.retrogaming.webapp.dto.RegistroDTO;
import com.retrogaming.webapp.entity.Usuario;
import com.retrogaming.webapp.repository.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario registrar(RegistroDTO dto) {

        Usuario u = new Usuario();

        u.setUsuario(dto.getUsuario());
        u.setCorreo(dto.getCorreo());
        u.setPassword(dto.getPassword());

        u.setRol(dto.getRol().toLowerCase());

        u.setActivo(true);

        return usuarioRepository.save(u);
    }

    public Usuario login(LoginDTO dto) {

        Optional<Usuario> opt =
                usuarioRepository.findByUsuarioAndPassword(
                        dto.getUser(),
                        dto.getPass()
                );

        if (opt.isPresent()) {

            Usuario user = opt.get();

            if (!user.isActivo()) {
                throw new RuntimeException("Usuario bloqueado");
            }

            return user;
        }

        return null;
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    public void toggleEstado(Long id) {

        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        user.setActivo(!user.isActivo());

        usuarioRepository.save(user);
    }

    public void cambiarPassword(RecuperarDTO dto) {

        if (!dto.getPassword().equals(dto.getConfirm())) {
            throw new RuntimeException("Las contraseñas no coinciden");
        }

        Usuario user = usuarioRepository.findByUsuario(dto.getUsuario())
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));

        user.setPassword(dto.getPassword());

        usuarioRepository.save(user);
    }
}