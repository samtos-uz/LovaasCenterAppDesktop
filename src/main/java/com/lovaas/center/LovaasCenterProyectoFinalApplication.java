package com.lovaas.center;

import java.awt.EventQueue;
import java.util.List;

import javax.swing.table.TableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lovaas.center.controlador.ControladorVentanas;
import com.lovaas.center.controlador.FirebaseController;
import com.lovaas.center.modelo.entidad.Terapeuta;
import com.lovaas.center.vista.VentanaDashboard;
import com.lovaas.center.vista.VentanaLogin;

@SpringBootApplication
public class LovaasCenterProyectoFinalApplication implements CommandLineRunner {

	@Autowired
	private ControladorVentanas controladorVentanas;
	@Autowired
	private VentanaLogin ventanaLogin;
	@Autowired
	private VentanaDashboard ventanaDashboard;
	@Autowired
	private FirebaseController fbc;

	public static void main(String[] args) {
		SpringApplication.run(LovaasCenterProyectoFinalApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// inyectamos controlador a ventana y viceversa
		controladorVentanas.setVentanaLogin(ventanaLogin);
		controladorVentanas.setVentanaDashboard(ventanaDashboard);

		ventanaLogin.setControlador(controladorVentanas);
		ventanaDashboard.setControlador(controladorVentanas);
		// Hacemos visible la ventana
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ventanaLogin.setVisible(true);
					// ventanaDashboard.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// TableModel model = fbc.getTabla("terapeutas");
	}

}