package com.lovaas.center.controlador;

import java.util.concurrent.ExecutionException;

import javax.swing.JFrame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.lovaas.center.modelo.FirebaseInitializer;

@Controller
public class ControladorVentanas {

	private JFrame Ventana;
	@Autowired
	private FirebaseController fbc;

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

	public JFrame getVentana() {
		return Ventana;
	}

	public void setVentana(JFrame ventana) {
		Ventana = ventana;
	}

}
