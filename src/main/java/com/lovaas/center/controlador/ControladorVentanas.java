package com.lovaas.center.controlador;

import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.ListModel;
import javax.swing.table.TableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ser.impl.MapEntrySerializer;
import com.lovaas.center.modelo.FirebaseInitializer;
import com.lovaas.center.modelo.entidad.Programa;
import com.lovaas.center.modelo.entidad.Terapeuta;
import com.lovaas.center.vista.VentanaDashboard;
import com.lovaas.center.vista.VentanaLogin;

@Controller
public class ControladorVentanas {

	private VentanaLogin ventanaLogin;
	private VentanaDashboard ventanaDashboard;
	@Autowired
	private FirebaseController fbc;

	public ControladorVentanas() {
	}

	/**
	 * Método de login
	 * 
	 * @param usuario
	 * @param password
	 * @return <b>0</b> si las credenciales son correctas, <b>1</b> si el usuario
	 *         está vacío, <b>2</b> si la contraseña está vacía y <b>3</> si los dos
	 *         campos están vacíos.
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public int iniciarSesion(String usuario, String password) throws InterruptedException, ExecutionException {
		int response = 0;
		switch (comprobarCampos(usuario, password)) {
		case 0:
			response = fbc.iniciarSesion(usuario, password) ? 0 : -1;
			return response;
		case 1:
			response = 1;
			return response;
		case 2:
			response = 2;
			return response;
		case 3:
			response = 3;
			return response;
		}
		System.out.println("Inició sesión: " + usuario + " - " + password);
		return response;
	}

	/**
	 * Método que comprueba si algún campo está vacio y devuelve una respuesta
	 * dependiendo del estado
	 * 
	 * @param usuario  nombre de usuario de tipo {@link String}
	 * @param password contraseña de usuario de tipo {@link String}
	 * @return <b>0</b> en caso de que los campos estén completos, <b>1</b> si el
	 *         usuario está vacío, <b>2</b> si la contraseña está vacía y <b>3</> si
	 *         los dos campos están vacíos.
	 */
	private int comprobarCampos(String usuario, String password) {
		int response = 0;
		if (usuario.isEmpty()) {
			response = 1;
		} else if (password.isEmpty()) {
			response = 2;
		} else if (usuario.isEmpty() && password.isEmpty()) {
			response = 3;
		}
		return response;
	}

	public VentanaLogin getVentanaLogin() {
		return ventanaLogin;
	}

	public void setVentanaLogin(VentanaLogin ventanaLogin) {
		this.ventanaLogin = ventanaLogin;
	}

	public VentanaDashboard getVentanaDashboard() {
		return ventanaDashboard;
	}

	public void setVentanaDashboard(VentanaDashboard ventanaDashboard) {
		this.ventanaDashboard = ventanaDashboard;
	}

	/**
	 * Metodo que pide el modelo de datos a la {@link FirebaseController}
	 * 
	 * @param nombreTabla el nombre de la tabla ("coleccion")
	 * @return la tabla convertida de la coleccion
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public TableModel getTabla(String nombreTabla) throws InterruptedException, ExecutionException {
		TableModel model = fbc.getTabla(nombreTabla);
		return model;
	}

	/**
	 * Método para llamar al modelo de la aplicacion y dar de alta un terapeuta en
	 * la bd
	 * 
	 * @param terapeuta objeto de tipo {@link Terapeuta}
	 * @return 1 si se da de alta, 0 si ocurrio un error al darse de alta, -1 en
	 *         caso de que esté vacío Nombre o Apellidos del terapeuta
	 */

	public int altaTerapeuta(Terapeuta terapeuta) {
		int response = 0;
		// Verificación de campos para poder construir el id.
		if (terapeuta.getNombre().isEmpty() || terapeuta.getApellidos().isEmpty()) {
			response = -1;
		}
		response = fbc.altaTerapeuta(terapeuta) ? 1 : 0; // si se da de alta correctamente devuelve 1, 0 en caso
															// contrario
		return response;
	}

	public boolean altaPrograma(Programa programa) {
		boolean alta = fbc.altaPrograma(programa);
		return alta;
	}

	public void irVentanaDashboard() {
		ventanaLogin.setVisible(false);
		ventanaDashboard.setVisible(true);
	}

	public void logout() {
		ventanaDashboard.setVisible(false);
		ventanaLogin.setVisible(true);
	}

	public boolean actualizarTerapeuta(Terapeuta terapeuta) throws InterruptedException, ExecutionException {
		boolean update = fbc.actualizarTerapeuta(terapeuta);
		return update;
	}

	public boolean actualizarPrograma(Programa programa) throws InterruptedException, ExecutionException {
		boolean update = fbc.actualizarPrograma(programa);
		return update;
	}

	public void obtenerIdTerapeuta(Terapeuta terapeutaActual) throws InterruptedException, ExecutionException {
		fbc.obtenerIdTerapeuta(terapeutaActual);
	}

	public void obtenerIdPrograma(Programa programaActual) throws InterruptedException, ExecutionException {
		fbc.obtenerIdPrograma(programaActual);
	}

	public void eliminarTerapeuta(Terapeuta terapeutaActual) throws InterruptedException, ExecutionException {
		fbc.eliminarTerapeuta(terapeutaActual);
	}

	public void borrarPrograma(Programa programaActual) throws InterruptedException, ExecutionException {
		fbc.eliminarPrograma(programaActual);
	}

	public ListModel<String> getListModel(String nombreTabla) throws InterruptedException, ExecutionException {
		ListModel<String> listModel = fbc.getListModel(nombreTabla);
		return listModel;
	}

	public void obtenerUnidades(Programa programaActual) throws InterruptedException, ExecutionException {
		fbc.obtenerUnidades(programaActual);
	}

	/**
	 * Genera el modelo de JlistUnidades a partir de un {@link TreeMap} pasado por parametro
	 * @param unidades
	 * @return
	 */
	public ListModel generarModeloUnidades(TreeMap<String, Object> unidades) {
		DefaultListModel<String> model = new DefaultListModel<String>();
		for (Entry<String, Object> par : unidades.entrySet()) {
			String unidadCompleta = par.getKey() + ": " + par.getValue();
			model.addElement(unidadCompleta);
		}
		return model;
	}

}
