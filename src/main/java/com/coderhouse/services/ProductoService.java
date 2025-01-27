package com.coderhouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.models.Categoria;
import com.coderhouse.models.Producto;
import com.coderhouse.repositories.CategoriaRepository;
import com.coderhouse.repositories.ProductoRepository;

import jakarta.transaction.Transactional;

@Service
public class ProductoService {

	@Autowired
	private ProductoRepository productoRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public List<Producto> getAllProductos() {
		return productoRepository.findAll();
	}

	public Producto getProductoById(Long id) {
		return productoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

	}

	@Transactional
	public Producto createProducto(Producto producto) {
		return productoRepository.save(producto);
	}

	@Transactional
	public Producto updateProducto(Long id, Producto productoDetails) {
		Producto producto = productoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
		producto.setNombre(productoDetails.getNombre());
		producto.setStock(productoDetails.getStock());
		producto.setPrecio(productoDetails.getPrecio());
		return productoRepository.save(producto);
		

	}

	public void deleteProducto(Long id) {
		if (productoRepository.existsById(id)) {
			productoRepository.deleteById(id);
		} else {
			throw new IllegalArgumentException("Producto no encontrado");
		}
	}

	@Transactional
	public Producto asignarCategoriaAProducto(Long productoID, Long categoriaID) {
		Categoria categoria = categoriaRepository.findById(categoriaID)
				.orElseThrow(() -> new IllegalArgumentException("Categoria no encontrado"));
		Producto producto = productoRepository.findById(productoID)
				.orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
	}
}
	
