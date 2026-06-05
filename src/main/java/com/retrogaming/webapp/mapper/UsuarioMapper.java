package com.retrogaming.webapp.mapper;

import com.retrogaming.webapp.dto.RegistroDTO;
import com.retrogaming.webapp.model.Usuario;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioMapper {

    // DTO → Entity (para persistir en MySQL)
    public Usuario toEntity(RegistroDTO dto) {
        Usuario entity = new Usuario();
        entity.setUsuario(dto.getUsuario());
        entity.setCorreo(dto.getCorreo());
        entity.setPassword(dto.getPassword());  // encriptar en el Service
        entity.setRol(dto.getRol());
        entity.setActivo(true);
        return entity;
    }

    // Entity → DTO (para enviar a la vista o al JSON)
    public RegistroDTO toDTO(Usuario entity) {
        RegistroDTO dto = new RegistroDTO();
        dto.setUsuario(entity.getUsuario());
        dto.setCorreo(entity.getCorreo());
        dto.setRol(entity.getRol());
        dto.setId(entity.getId());
        dto.setActivo(entity.isActivo());
        return dto;
    }

    // Lista de entities → Lista de DTOs
    public List<RegistroDTO> toDTOList(List<Usuario> entities) {
        return entities.stream()
                .map(this::toDTO)
                .toList();
    }
}