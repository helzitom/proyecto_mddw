package com.retrogaming.webapp.service;

import com.retrogaming.webapp.entity.Producto;
import com.retrogaming.webapp.repository.ProductoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public List<Producto> listarPorTipo(String tipo) {
        return productoRepository.findByTipo(tipo);
    }

    public Producto guardar(Producto producto) {

        if (producto.getStock() == null) {
            producto.setStock(0);
        }

        return productoRepository.save(producto);
    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }
}