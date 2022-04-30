package com.lovaas.center;

import java.awt.EventQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lovaas.center.controlador.ControladorVentanas;
import com.lovaas.center.vista.VentanaLogin;

@SpringBootApplication
public class LovaasCenterProyectoFinalApplication implements CommandLineRunner {

	@Autowired
	private ControladorVentanas controladorVentanas;
	@Autowired  
	private VentanaLogin ventanaLogin;

	public static void main(String[] args) {
		SpringApplication.run(LovaasCenterProyectoFinalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// inyectamos controlador a ventana y viceversa
		controladorVentanas.setVentana(ventanaLogin);
		ventanaLogin.setControlador(controladorVentanas);
		// Hacemos visible la ventana
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//VentanaLogin frame = new VentanaLogin();
					ventanaLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}