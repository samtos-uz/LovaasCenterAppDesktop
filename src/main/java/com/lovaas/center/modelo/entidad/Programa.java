package com.lovaas.center.modelo.entidad;

import java.util.TreeMap;

import org.springframework.stereotype.Component;


@Component
public class Programa {
	private String nombre;
	private TreeMap<String, Object> unidades;

	public Programa() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public TreeMap<String, Object> getUnidades() {
		return unidades;
	}

	public void setUnidades(TreeMap<String, Object> unidades2) {
		this.unidades = unidades2;
	}

	@Override
	public String toString() {
		return "Programa [nombre=" + nombre + ", unidades=" + unidades + "]";
	}

}
