package com.coderhouse.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "Productos")
public class Producto {

	@Id // Primary Key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String nombre;
	
	private int stock;
	
	private double precio;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "Ventas", 
			joinColumns = @JoinColumn(name = "producto_id"), 
			inverseJoinColumns = @JoinColumn(name = "cliente_id"))
	@JsonIgnore
	private List<Cliente> clientes = new ArrayList<>();

	@ManyToOne(fetch = FetchType.EAGER)
	private Categoria categoria;
	
}