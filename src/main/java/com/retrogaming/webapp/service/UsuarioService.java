package com.retrogaming.webapp.service;

import com.retrogaming.webapp.dto.LoginDTO;
import com.retrogaming.webapp.dto.RecuperarDTO;
import com.retrogaming.webapp.dto.RegistroDTO;
import com.retrogaming.webapp.mapper.UsuarioMapper;
import com.retrogaming.webapp.model.Usuario;
import com.retrogaming.webapp.repository.UsuarioRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository,
                          UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper     = usuarioMapper;
    }

    // DTO → Entity → guardar
    public RegistroDTO registrar(RegistroDTO dto) {
        Usuario u = usuarioMapper.toEntity(dto);
        u.setRol(dto.getRol().toLowerCase());
        return usuarioMapper.toDTO(usuarioRepository.save(u));
    }

    public Usuario login(LoginDTO dto) {
        Usuario user = usuarioRepository
                .findByUsuarioAndPassword(dto.getUser(), dto.getPass())
                .orElse(null);

        if (user == null) return null;

        if (!user.isActivo()) {
            throw new RuntimeException("Usuario bloqueado");
        }

        return user;
    }

    // Lista de entities → Lista de DTOs
    public List<RegistroDTO> listarTodos() {
        return usuarioMapper.toDTOList(usuarioRepository.findAll());
    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    public void toggleEstado(Long id) {
        Usuario user = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setActivo(!user.isActivo());
        usuarioRepository.save(user);
    }

    public void cambiarPassword(RecuperarDTO dto) {
        if (!dto.getPassword().equals(dto.getConfirm())) {
            throw new RuntimeException("Las contraseñas no coinciden");
        }

        Usuario user = usuarioRepository.findByUsuario(dto.getUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        user.setPassword(dto.getPassword());
        usuarioRepository.save(user);
    }
}