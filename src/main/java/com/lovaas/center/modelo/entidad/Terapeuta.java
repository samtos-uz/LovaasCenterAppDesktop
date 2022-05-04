package com.lovaas.center.modelo.entidad;

public class Terapeuta {
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

	@Override
	public String toString() {
		return "Terapeuta [nombre=" + nombre + ", apellidos=" + apellidos + ", ciudad=" + ciudad + ", telefono="
				+ telefono + "]";
	}

}
