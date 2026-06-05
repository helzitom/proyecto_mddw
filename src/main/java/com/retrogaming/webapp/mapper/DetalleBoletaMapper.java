package com.retrogaming.webapp.mapper;

import com.retrogaming.webapp.dto.DetalleBoletaDTO;
import com.retrogaming.webapp.model.Boleta;
import com.retrogaming.webapp.model.DetalleBoleta;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DetalleBoletaMapper {

    // DTO → Entity
    public DetalleBoleta toEntity(DetalleBoletaDTO dto, Boleta boleta) {
        DetalleBoleta entity = new DetalleBoleta();
        entity.setNombreProducto(dto.getNombreProducto());
        entity.setPrecioUnitario(dto.getPrecioUnitario());
        entity.setCantidad(dto.getCantidad());
        entity.setSubtotal(dto.getSubtotal());
        entity.setBoleta(boleta);
        return entity;
    }

    // Entity → DTO
    public DetalleBoletaDTO toDTO(DetalleBoleta entity) {
        DetalleBoletaDTO dto = new DetalleBoletaDTO();
        dto.setNombreProducto(entity.getNombreProducto());
        dto.setPrecioUnitario(entity.getPrecioUnitario());
        dto.setCantidad(entity.getCantidad());
        dto.setSubtotal(entity.getSubtotal());
        return dto;
    }

    // Lista de DTOs → Lista de Entities (necesita la boleta padre)
    public List<DetalleBoleta> toEntityList(List<DetalleBoletaDTO> dtos, Boleta boleta) {
        return dtos.stream()
                .map(dto -> toEntity(dto, boleta))
                .toList();
    }

    // Lista de entities → Lista de DTOs
    public List<DetalleBoletaDTO> toDTOList(List<DetalleBoleta> entities) {
        return entities.stream()
                .map(this::toDTO)
                .toList();
    }
}