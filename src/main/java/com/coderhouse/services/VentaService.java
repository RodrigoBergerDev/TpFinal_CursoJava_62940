package com.coderhouse.services;

import com.coderhouse.dtos.TimeApiResponseDTO;
import com.coderhouse.dtos.VentaRequestDTO;
import com.coderhouse.models.*;
import com.coderhouse.error.VentaException;
import com.coderhouse.repositories.ClienteRepository;
import com.coderhouse.repositories.ProductoRepository;
import com.coderhouse.repositories.ProductoVentaRepository;
import com.coderhouse.repositories.VentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Autowired
    ProductoVentaRepository productoVentaRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Venta> getAllVentas(){
        return ventaRepository.findAll();
    }

    public Venta getVentaById(Long id) throws VentaException{
        return ventaRepository.findById(id)
                .orElseThrow(() -> new VentaException("No se encontró venta con ID: " + id));
    }

    @Transactional
    public Venta createVenta(VentaRequestDTO ventaRequestDTO) throws VentaException {
        // Valido cliente
        Cliente cliente = clienteRepository.findById(ventaRequestDTO.getCliente().getClienteId())
                .orElseThrow(() -> new VentaException("Cliente no encontrado con ID: " + ventaRequestDTO.getCliente().getClienteId()));

        // Crear la venta
        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFecha(obtenerFechaActual());
        venta.setDetalles(new ArrayList<>());

        double precioTotal = 0;
        int cantidadProductos = 0;

        // Procesar cada línea de la venta
        for (VentaRequestDTO.Linea linea : ventaRequestDTO.getLineas()) {
            Producto producto = productoRepository.findById(linea.getProducto().getProductoId())
                    .orElseThrow(() -> new VentaException("Producto no encontrado con ID: " + linea.getProducto().getProductoId()));

            // Validar stock
            if (producto.getStock() < linea.getCantidad()) {
                throw new VentaException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // Reducir stock
            producto.setStock(producto.getStock() - linea.getCantidad());
            productoRepository.save(producto);

            // Crear una copia del producto en el momento de la venta
            ProductoVenta productoVenta = new ProductoVenta();
            productoVenta.setNombre(producto.getNombre());
            productoVenta.setDescripcion(producto.getDescripcion());
            productoVenta.setPrecio(producto.getPrecio());
            productoVenta.setStock(producto.getStock());
            productoVenta.setCategoria(producto.getCategoria());

            productoVentaRepository.save(productoVenta);

            // Crear detalle de venta
            VentaDetalle detalle = new VentaDetalle();
            detalle.setVenta(venta);
            detalle.setProductoVenta(productoVenta);
            detalle.setCantidad(linea.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());

            venta.getDetalles().add(detalle);

            // Calcular totales
            precioTotal += producto.getPrecio() * linea.getCantidad();
            cantidadProductos += linea.getCantidad();
        }

        venta.setPrecioTotal(precioTotal);
        venta.setCantidadProductos(cantidadProductos);

        return ventaRepository.save(venta);
    }

    private LocalDateTime obtenerFechaActual() {
        try {
            String url = "https://timeapi.io/api/Time/current/zone?timeZone=America/Argentina/Buenos_Aires";

            TimeApiResponseDTO responseDTO = restTemplate.getForObject(url, TimeApiResponseDTO.class);
            if (responseDTO != null) {
                return LocalDateTime.parse(responseDTO.getDateTime());
            }
        } catch (Exception e) {
            // Si falla, usar la fecha local
            return LocalDateTime.now();
        }
        return LocalDateTime.now();
    }


    @Transactional
    public Venta updateVenta(Long id, Venta ventaDetails){
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró venta con ID: " + id));
        venta.setCliente(ventaDetails.getCliente());
        venta.setDetalles(ventaDetails.getDetalles());

        return ventaRepository.save(venta);
    }

    public void deleteVentaById(Long id){
        if(ventaRepository.existsById(id)){
            ventaRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("No se encontro venta con ID: " + id);
        }
    }
}