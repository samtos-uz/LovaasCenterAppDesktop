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
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */

	public int altaTerapeuta(Terapeuta terapeuta) throws InterruptedException, ExecutionException {

		int response = comprobarCamposTerapeuta(terapeuta);
		if (response == 1) {
			response = fbc.altaTerapeuta(terapeuta) ? 0 : -1; // alta correctamente 0, -1 en caso de fallo
		}
		return response;
	}

	/**
	 * Comprueba campos de {@link Terapeuta} antes de dar de alta, informa si algun
	 * campo del mismo está vacío
	 * 
	 * @param terapeuta
	 * @return 1 si los campos son correctos. 2, si el nombre esta vacio. 3 si los
	 *         apellidos estan vacios. 4 si la ciudad está vacia. 5 el telefono esta
	 *         vacio. 6 todos los campos están vacios.
	 */
	private int comprobarCamposTerapeuta(Terapeuta terapeuta) {
		int response = 1;
		if (terapeuta == null) {
			response = 6;
		} else if (terapeuta.getNombre().isEmpty() && terapeuta.getApellidos().isEmpty()
				&& terapeuta.getCiudad().isEmpty() && terapeuta.getTelefono().isEmpty()) {
			response = 6;
		} else if (terapeuta.getApellidos().isEmpty()) {
			response = 3;
		} else if (terapeuta.getCiudad().isEmpty()) {
			response = 4;
		} else if (terapeuta.getTelefono().isEmpty()) {
			response = 5;
		} else if (terapeuta.getNombre().isEmpty()) {
			response = 2;
		}
		return response;
	}

	/**
	 * Metodo que llama al modelo, para darlo de alta en bd. Primero comprueba sus
	 * campos
	 * 
	 * @param programa
	 * @return 1 si se da de alta, 0 si ocurrio en error en bd.
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public int altaPrograma(Programa programa) throws InterruptedException, ExecutionException {
		int response = comprobarCamposPrograma(programa);
		if (response == 1) {
			response = fbc.altaPrograma(programa) ? 0 : -1;
		}
		return response;
	}

	/**
	 * 
	 * @param programa
	 * @return 1, si los campos son correctos. 2 si el programa esta vacio. 3 Si el
	 *         nombre esta vacio
	 */
	private int comprobarCamposPrograma(Programa programa) {
		int response = 1;
		if (programa == null) {
			response = 2;
		} else if (programa.getNombre() == null) {
			response = 3;
		}
		return response;
	}

	public void irVentanaDashboard() {
		ventanaLogin.setVisible(false);
		ventanaDashboard.setVisible(true);
	}

	public void logout() {
		ventanaDashboard.setVisible(false);
		ventanaLogin.setVisible(true);
	}

	/**
	 * Metodo que llama al modelo para actualizar {@link Terapeuta} en bd. Primero
	 * comprueba que sus camos no esten vacios
	 * 
	 * @param terapeuta
	 * @return Si ocurrió algún fallo al verificar campos devuelve respuesta del
	 *         metodo comprobarCamposTerapeuta, 0 si se actualizó correctamente, -1
	 *         en caso contrario
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public int actualizarTerapeuta(Terapeuta terapeuta) throws InterruptedException, ExecutionException {
		int response = comprobarCamposTerapeuta(terapeuta);
		if (response == 1) {
			response = fbc.actualizarTerapeuta(terapeuta) ? 0 : -1;
		}
		return response;
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

	public boolean eliminarPrograma(Programa programaActual) throws InterruptedException, ExecutionException {
		boolean eliminado = fbc.eliminarPrograma(programaActual);
		return eliminado;
	}

	public ListModel<String> getListModel(String nombreTabla) throws InterruptedException, ExecutionException {
		ListModel<String> listModel = fbc.getListModel(nombreTabla);
		return listModel;
	}

	public void obtenerUnidades(Programa programaActual) throws InterruptedException, ExecutionException {
		fbc.obtenerUnidades(programaActual);
	}

	/**
	 * Genera el modelo de JlistUnidades a partir de un {@link TreeMap} pasado por
	 * parametro
	 * 
	 * @param unidades
	 * @return
	 */
	public ListModel obtenerModeloUnidades(TreeMap<String, Object> unidades) {
		DefaultListModel<String> model = new DefaultListModel<String>();
		for (Entry<String, Object> par : unidades.entrySet()) {
			String unidadCompleta = par.getKey() + ": " + par.getValue();
			model.addElement(unidadCompleta);
		}
		return model;
	}

	/**
	 * Metodo que recibe un string con clave valor y hace la división para
	 * convertirlo en un {@link TreeMap}
	 * 
	 * @param parUnidad en formato {@link String}
	 * @return el objeto {@link TreeMap} con clave - valor del {@link String} pasado
	 *         por parámetro
	 */
	public TreeMap<String, String> getClaveValor(String parUnidad) {
		TreeMap<String, String> unidadActual = new TreeMap<String, String>();
		String[] arrPar = parUnidad.split(": ");
		if (arrPar.length >= 2) {
			unidadActual.put(arrPar[0], arrPar[1]);
		}
		return unidadActual;
	}

	/**
	 * Convierte el {@link String} en un par Clave - Valor y lo agrega como Unidad
	 * al programa asociado
	 * 
	 * @param programaActual
	 * @param nombreUnidad
	 * @return <b>1</b>, si se asignó correctamente, <b>0</b>, si ocurrió algun
	 *         problema al asignar.
	 */
	public int asinarUnidadPrograma(Programa programaActual, String nombreUnidad) {
		int response = 1;
		boolean repetido = false;
		if (nombreUnidad == null) {
			response = 0;
		} else if (nombreUnidad.isEmpty()) {
			response = 0;
		}
		TreeMap<String, Object> unidades = programaActual.getUnidades();
		for (Object value : unidades.values()) {
			if (value.equals(nombreUnidad)) {
				repetido = true;
			}
		}
		if (!repetido) {
//			String id = String.valueOf(unidades.size() + 1); // id autoincremental
			unidades.put(nombreUnidad, nombreUnidad);
		}
		return response;
	}

	/**
	 * Llama al modelo para que actualice los campos del objeto en BD
	 * 
	 * @param programaActual
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public void actualizarUnidadesPrograma(Programa programaActual) throws InterruptedException, ExecutionException {
		fbc.actualizarUnidadesPrograma(programaActual);
	}

	/**
	 * Elimina una unidad pasada por parametro del programa
	 * 
	 * @param programaActual
	 * @param unidadActual
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public void eliminarUnidadPrograma(Programa programaActual, TreeMap<String, String> unidadActual)
			throws InterruptedException, ExecutionException {
		String key = unidadActual.firstKey();
		// Eliminamos campo del objeto actual
		TreeMap<String, Object> unidades = programaActual.getUnidades();
		unidades.remove(key);
		// Eliminamos en bd
		fbc.eliminarUnidadPrograma(programaActual, key);
	}

}
