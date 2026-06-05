package com.retrogaming.webapp.mapper;

import com.retrogaming.webapp.dto.BoletaDTO;
import com.retrogaming.webapp.model.Boleta;
import com.retrogaming.webapp.model.DetalleBoleta;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class BoletaMapper {

    private final DetalleBoletaMapper detalleBoletaMapper;

    public BoletaMapper(DetalleBoletaMapper detalleBoletaMapper) {
        this.detalleBoletaMapper = detalleBoletaMapper;
    }

    // DTO → Entity (para persistir en MySQL)
    public Boleta toEntity(BoletaDTO dto) {
        Boleta entity = new Boleta();
        entity.setNumero(dto.getNumero());
        entity.setFecha(LocalDateTime.now());
        entity.setClienteNombre(dto.getClienteNombre());
        entity.setClienteDni(dto.getClienteDni());
        entity.setMetodoPago(dto.getMetodoPago());
        entity.setTotal(dto.getTotal());

        List<DetalleBoleta> detalles = detalleBoletaMapper.toEntityList(dto.getDetalles(), entity);
        entity.setDetalles(detalles);

        return entity;
    }

    // Entity → DTO (para enviar a la vista o al JSON)
    public BoletaDTO toDTO(Boleta entity) {
        BoletaDTO dto = new BoletaDTO();
        dto.setNumero(entity.getNumero());
        dto.setClienteNombre(entity.getClienteNombre());
        dto.setClienteDni(entity.getClienteDni());
        dto.setMetodoPago(entity.getMetodoPago());
        dto.setTotal(entity.getTotal());
        dto.setDetalles(detalleBoletaMapper.toDTOList(entity.getDetalles()));
        return dto;
    }

    // Lista de entities → Lista de DTOs
    public List<BoletaDTO> toDTOList(List<Boleta> entities) {
        return entities.stream()
                .map(this::toDTO)
                .toList();
    }
}