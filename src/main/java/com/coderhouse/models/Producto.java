package com.coderhouse.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un producto disponible en el sistema de e-commerce.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "productos")
@Schema(description = "Modelo de Producto", title = "Modelo de Producto")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Autoincremental
    private long id;

    private String nombre;

    private String descripcion;

    private double precio;

    private int stock;

   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(
          name = "producto_cliente",
     joinColumns = @JoinColumn(name = "producto_id"),
     inverseJoinColumns = @JoinColumn(name = "cliente_id")
     )

    @JsonIgnore
    private List<Cliente> clientes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Categoria categoria;

}