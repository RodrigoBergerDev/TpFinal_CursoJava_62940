package com.coderhouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.coderhouse.dao.DaoFactory;
import com.coderhouse.models.Cliente;
import com.coderhouse.models.Producto;

@SpringBootApplication
public class TrabajoFinalApplication implements CommandLineRunner {

	@Autowired
	private DaoFactory dao;

	public static void main(String[] args) {
		SpringApplication.run(TrabajoFinalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		try {

			Producto producto1 = new Producto("Termo",3,3000);
			Producto producto2 = new Producto("Mate",2,2000);
			Producto producto3 = new Producto("Yerbera",6,3500);
			Producto producto4 = new Producto("Portatermo",10,15000);

		    Cliente cliente1 = new Cliente("Rodrigo", "Berger", 123456789, "L123456789");
		    Cliente cliente2 = new Cliente("Virginia", "Jozami", 234567890, "L234567890");
		    Cliente cliente3 = new Cliente("Ambar", "Berger", 345678901, "L345678901");
		    Cliente cliente4 = new Cliente("Luisa", "Tour", 456789012, "L456789012");

			dao.persistirCliente(cliente1);
			dao.persistirCliente(cliente2);
			dao.persistirCliente(cliente3);
			dao.persistirCliente(cliente4);

			dao.persistirProducto(producto1);
			dao.persistirProducto(producto2);
			dao.persistirProducto(producto3);
			dao.persistirProducto(producto4);

		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

	}

}