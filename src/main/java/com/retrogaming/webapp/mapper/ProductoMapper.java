package com.retrogaming.webapp.mapper;

import com.retrogaming.webapp.dto.ProductoDTO;
import com.retrogaming.webapp.model.Producto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductoMapper {

    // DTO → Entity (para persistir en MySQL)
    public Producto toEntity(ProductoDTO dto) {
        Producto entity = new Producto();
        entity.setId(dto.getId());           // null = INSERT, valor = UPDATE
        entity.setNombre(dto.getNombre());
        entity.setPrecio(dto.getPrecio());
        entity.setStock(dto.getStock());
        entity.setImagen(dto.getImagen());
        return entity;
    }

    // Entity → DTO (para enviar a la vista o al JSON)
    public ProductoDTO toDTO(Producto entity) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setPrecio(entity.getPrecio());
        dto.setStock(entity.getStock());
        dto.setImagen(entity.getImagen());
        return dto;
    }

    // Lista de entities → Lista de DTOs
    public List<ProductoDTO> toDTOList(List<Producto> entities) {
        return entities.stream()
                .map(this::toDTO)
                .toList();
    }
}