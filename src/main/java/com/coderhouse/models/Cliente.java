package com.coderhouse.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clientes")
@Schema(description = "Modelo de Cliente", title = "Modelo de Cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Autoincremental
    @Schema(description = "ID del cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long Id;

    @Schema(description = "Nombre del cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "Rodrigo")
    private String nombre;

    @Schema(description = "Apellido del cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "Berger")
    private String apellido;

    @Column(unique = true, nullable = false)
    @Schema(description = "DNI del cliente", requiredMode = Schema.RequiredMode.REQUIRED, example = "30406337")
    private int dni;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    @Schema(description = "Fecha de creación del cliente", requiredMode = Schema.RequiredMode.AUTO, example = "2025-01-13T10:21:27.402135")
    private LocalDateTime createdAt;
}