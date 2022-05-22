package com.distribuidos.corteelectoral.domain;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ResultsDTO {
    private String nombre_partido;
    private Integer cantidadVotos;
    private String status;
	public String getNombre_partido() {
		return nombre_partido;
	}
	public void setNombre_partido(String nombre_partido) {
		this.nombre_partido = nombre_partido;
	}
	public Integer getCantidadVotos() {
		return cantidadVotos;
	}
	public void setCantidadVotos(Integer cantidadVotos) {
		this.cantidadVotos = cantidadVotos;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
