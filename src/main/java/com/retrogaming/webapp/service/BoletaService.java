package com.retrogaming.webapp.service;

import com.retrogaming.webapp.dto.BoletaDTO;
import com.retrogaming.webapp.mapper.BoletaMapper;
import com.retrogaming.webapp.model.Boleta;
import com.retrogaming.webapp.repository.BoletaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BoletaService {

    private final BoletaRepository boletaRepository;
    private final BoletaMapper boletaMapper;

    public BoletaService(BoletaRepository boletaRepository,
                         BoletaMapper boletaMapper) {
        this.boletaRepository = boletaRepository;
        this.boletaMapper     = boletaMapper;
    }

    // Lista de entities → Lista de DTOs
    public List<BoletaDTO> listarTodas() {
        return boletaMapper.toDTOList(boletaRepository.findAll());
    }

    // DTO → Entity → guardar
    public BoletaDTO guardar(BoletaDTO dto) {
        Boleta boleta = boletaMapper.toEntity(dto);
        return boletaMapper.toDTO(boletaRepository.save(boleta));
    }

    // Entity → DTO
    public BoletaDTO buscarPorId(Long id) {
        Boleta boleta = boletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Boleta no encontrada"));
        return boletaMapper.toDTO(boleta);
    }

    public void eliminar(Long id) {
        boletaRepository.deleteById(id);
    }
}