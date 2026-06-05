package com.retrogaming.webapp.service;

import com.retrogaming.webapp.dto.ProductoDTO;
import com.retrogaming.webapp.mapper.ProductoMapper;
import com.retrogaming.webapp.model.Producto;
import com.retrogaming.webapp.repository.ProductoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;

    public ProductoService(ProductoRepository productoRepository,
                           ProductoMapper productoMapper) {
        this.productoRepository = productoRepository;
        this.productoMapper     = productoMapper;
    }

    // Lista de entities → Lista de DTOs
    public List<ProductoDTO> listarTodos() {
        return productoMapper.toDTOList(productoRepository.findAll());
    }

    // Lista de entities por tipo → Lista de DTOs
    public List<ProductoDTO> listarPorTipo(String tipo) {
        return productoMapper.toDTOList(productoRepository.findByTipo(tipo));
    }

    // DTO → Entity → guardar
    public ProductoDTO guardar(ProductoDTO dto) {
        Producto entity = productoMapper.toEntity(dto);

        if (entity.getStock() == null) {
            entity.setStock(0);
        }

        return productoMapper.toDTO(productoRepository.save(entity));
    }

    // Entity → DTO
    public ProductoDTO buscarPorId(Long id) {
        Producto entity = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return productoMapper.toDTO(entity);
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}