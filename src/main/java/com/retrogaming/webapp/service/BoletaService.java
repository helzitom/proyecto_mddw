package com.retrogaming.webapp.service;

import com.retrogaming.webapp.dto.BoletaDTO;
import com.retrogaming.webapp.dto.DetalleBoletaDTO;
import com.retrogaming.webapp.entity.Boleta;
import com.retrogaming.webapp.entity.DetalleBoleta;
import com.retrogaming.webapp.repository.BoletaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class BoletaService {

    @Autowired
    private BoletaRepository boletaRepository;

    public List<Boleta> listarTodas() {
        return boletaRepository.findAll();
    }

    public Boleta guardar(BoletaDTO dto) {

        Boleta boleta = new Boleta();

        boleta.setNumero(dto.getNumero());
        boleta.setFecha(LocalDateTime.now());
        boleta.setClienteNombre(dto.getClienteNombre());
        boleta.setClienteDni(dto.getClienteDni());
        boleta.setMetodoPago(dto.getMetodoPago());
        boleta.setTotal(dto.getTotal());

        List<DetalleBoleta> detalles = new ArrayList<>();

        for (DetalleBoletaDTO item : dto.getDetalles()) {

            DetalleBoleta detalle = new DetalleBoleta();

            detalle.setNombreProducto(item.getNombreProducto());
            detalle.setPrecioUnitario(item.getPrecioUnitario());
            detalle.setCantidad(item.getCantidad());
            detalle.setSubtotal(item.getSubtotal());

            detalle.setBoleta(boleta);

            detalles.add(detalle);
        }

        boleta.setDetalles(detalles);

        return boletaRepository.save(boleta);
    }

    public Boleta buscarPorId(Long id) {
        return boletaRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        boletaRepository.deleteById(id);
    }
}