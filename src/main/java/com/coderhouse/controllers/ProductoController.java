package com.coderhouse.controllers;

import com.coderhouse.dtos.AsignaCategoriaProductoDTO;
import com.coderhouse.models.Producto;
import com.coderhouse.error.AsignacionCategoriaException;
import com.coderhouse.services.CategoriaService;
import com.coderhouse.services.ProductoService;
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

@Tag(name ="Producto", description = "Producto Management System")
@RestController
@RequestMapping("/api/producto")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary = "Get all productos", description = "Permite listar todos los productos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "400", description = "Parámetros incorrectos",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )})
    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos(){
        try {
            List<Producto> productos = productoService.getAllProductos();
            return ResponseEntity.ok(productos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get Producto By ID", description = "Permite traer un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "400", description = "Parámetros incorrectos",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )})
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id){
        try {
            Producto producto = productoService.getProductoById(id);
            return ResponseEntity.ok(producto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Create Producto", description = "Permite crear un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "400", description = "Parámetros incorrectos",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )})
    @PostMapping
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto){
        try {
            Producto createdProducto = productoService.saveProducto(producto);
            return ResponseEntity.ok(createdProducto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Update Producto By ID", description = "Permite actualizar un producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "400", description = "Parámetros incorrectos",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )})
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProductoById(@PathVariable Long id, @RequestBody Producto productoDetails){
        try {
            Producto producto = productoService.updateProducto(id, productoDetails);
            return ResponseEntity.ok(producto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Delete Producto By ID", description = "Permite eliminar un producto por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "400", description = "Parámetros incorrectos",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductoById(@PathVariable Long id){
        try {
            productoService.deleteProductoById(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Add Categoria to Producto", description = "Permite agregar categoría a producto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa"),
            @ApiResponse(responseCode = "400", description = "Parámetros incorrectos",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}
            )})
    @PostMapping("/asignar-categoria")
    public ResponseEntity<Producto> addCategoryToProduct(@RequestBody AsignaCategoriaProductoDTO asignacionCatetoriaProductoDTO){
        try {
            Producto updateProducto = productoService.addCategoryToProduct(
                    asignacionCatetoriaProductoDTO.getProductoId(),
                    asignacionCatetoriaProductoDTO.getCategoriaId()
                    );
            return ResponseEntity.ok(updateProducto);
        } catch (AsignacionCategoriaException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}