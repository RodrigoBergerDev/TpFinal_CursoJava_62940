package com.coderhouse.dtos;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class VentaDTO {
	
	private Long clienteID;
	private List<Long> productosIds;

}
