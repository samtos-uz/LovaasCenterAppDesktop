package com.lovaas.center.modelo.entidad;

public class Programa {
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

	@Override
	public String toString() {
		return "Programa [nombre=" + nombre + ", fechaRealizacion=" + fechaRealizacion + ", porcentajeRealizado="
				+ porcentajeRealizado + "]";
	}

}
