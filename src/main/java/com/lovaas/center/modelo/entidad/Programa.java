package com.lovaas.center.modelo.entidad;

import org.springframework.stereotype.Component;

import com.google.cloud.firestore.annotation.Exclude;

@Component
public class Programa {
	private String id;
	private String nombre;
	private String fechaRealizacion;
	private String porcentajeRealizado;

	public Programa() {
	}

	public Programa(String nombre, String fechaRealizacion, String porcentajeRealizado) {
		this.nombre = nombre;
		this.fechaRealizacion = fechaRealizacion;
		this.porcentajeRealizado = porcentajeRealizado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFechaRealizacion() {
		return fechaRealizacion;
	}

	public void setFechaRealizacion(String fechaRealizacion) {
		this.fechaRealizacion = fechaRealizacion;
	}

	public String getPorcentajeRealizado() {
		return porcentajeRealizado;
	}

	public void setPorcentajeRealizado(String porcentajeRealizado) {
		this.porcentajeRealizado = porcentajeRealizado;
	}

	@Exclude
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Programa [id=" + id + ", nombre=" + nombre + ", fechaRealizacion=" + fechaRealizacion
				+ ", porcentajeRealizado=" + porcentajeRealizado + "]";
	}

}
