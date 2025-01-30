package com.coderhouse.controllers;

import com.coderhouse.dtos.VentaRequestDTO;
import com.coderhouse.models.Venta;
import com.coderhouse.error.VentaException;
import com.coderhouse.services.VentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Venta", description = "Gestión de ventas")
@RestController
@RequestMapping("/api/venta")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @Operation(summary = "Obtener todas las ventas", description = "Obtener todos los comprobantes de ventas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ventas obtenidas exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping
    public ResponseEntity<List<Venta>> getAllVentas() {
        try {
            List<Venta> ventas = ventaService.getAllVentas();
            return ResponseEntity.ok(ventas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Operation(summary = "Obtener venta por ID", description = "Obtener comprobante de ventas por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comprobante obtenido exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @GetMapping("/{id}")
    public ResponseEntity<Venta> getVentaById(@PathVariable Long id) {
        try {
            Venta venta = ventaService.getVentaById(id);
            return ResponseEntity.ok(venta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (VentaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Operation(summary = "Crear una venta", description = "Crea un nuevo comprobante de venta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Venta creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @PostMapping
    public ResponseEntity<Venta> createVenta(@RequestBody VentaRequestDTO ventaRequestDTO) {
        try {
            Venta venta = ventaService.createVenta(ventaRequestDTO);
            return ResponseEntity.ok(venta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (VentaException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @Operation(summary = "Eliminar una venta por ID", description = "Elimina un comprobante de venta por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Venta eliminada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)) })
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVentaById(@PathVariable Long id) {
        try {
            ventaService.deleteVentaById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}