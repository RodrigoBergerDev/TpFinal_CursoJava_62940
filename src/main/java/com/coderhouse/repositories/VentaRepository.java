package com.coderhouse.repositories;

import com.coderhouse.models.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {
}