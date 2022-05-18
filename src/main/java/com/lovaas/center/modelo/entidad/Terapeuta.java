package com.lovaas.center.modelo.entidad;

import org.springframework.stereotype.Component;

import com.google.cloud.firestore.annotation.Exclude;

@Component
public class Terapeuta {
	private String id;
	private String nombre;
	private String apellidos;
	private String ciudad;
	private String telefono;

	public Terapeuta() {
	}

	public Terapeuta(String nombre, String apellidos, String ciudad, String telefono) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.ciudad = ciudad;
		this.telefono = telefono;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
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
		return "Terapeuta [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", ciudad=" + ciudad
				+ ", telefono=" + telefono + "]";
	}

}
