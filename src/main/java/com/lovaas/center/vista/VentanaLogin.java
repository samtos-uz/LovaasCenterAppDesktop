package com.lovaas.center.vista;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Component;

import com.lovaas.center.controlador.ControladorVentanas;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Component
public class VentanaLogin extends JFrame implements KeyListener {

	// Funcionalidad APP
	private ControladorVentanas controlador;
	// Vista
	private JPanel bg;
	private JPanel panelDerecho;
	private JLabel lblIniciarSesion;
	private JLabel lblUsuario;
	private JLabel lblContraseña;
	private JPasswordField passwordField;
	private JButton btnLogin;
	private JTextField txtUsuario;
	private Label bordePassCampo;
	private JPanel barraAcciones;
	private JLabel lblExit;
	private JPanel panelIzquierdo;
	private JLabel lblTitleApp;
	private JLabel lblLogoApp;
	private JLabel lblSubtitulo;
	// Posicion mouse
	private int xMouse, yMouse;
	private Label bordeUsuarioCampo;
	private JLabel lblErrorLogin;

	/*
	 * Getter y setter del controlador
	 */

	public ControladorVentanas getControlador() {
		return controlador;
	}

	public void setControlador(ControladorVentanas controlador) {
		this.controlador = controlador;
	}

	/**
	 * Create the frame.
	 */
	public VentanaLogin() {
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(this); // Evento para cuando el usuario pulse la tecla enter, se pulse el botón de login
		setFocusable(true);
		setBounds(100, 100, 1280, 720);
		setLocationRelativeTo(null); // centrar el JFrame
		bg = new JPanel();
		bg.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					// lanzar evento click en boton
					lblErrorLogin.setText("Enter pulsado");
					System.out.println("Enter pulsado");
				}
			}
		});
		bg.setBackground(new Color(255, 255, 240));
		bg.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(bg);
		bg.setLayout(null);

		panelIzquierdo = new JPanel();
		panelIzquierdo.setLayout(null);
		panelIzquierdo.setBackground(new Color(102, 204, 0));
		panelIzquierdo.setBounds(0, 0, 476, 720);
		bg.add(panelIzquierdo);

		lblTitleApp = new JLabel("FUNDACIÓN ERIK LÖVAAS\r\n");
		lblTitleApp.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitleApp.setForeground(new Color(0, 102, 0));
		lblTitleApp.setFont(new Font("Oswald SemiBold", Font.BOLD, 30));
		lblTitleApp.setBounds(10, 394, 456, 43);
		panelIzquierdo.add(lblTitleApp);

		lblLogoApp = new JLabel("");
		lblLogoApp.setBounds(113, 113, 250, 250);
		panelIzquierdo.add(lblLogoApp);
		// Sacar ruta absoluta
		Path currentRelativePath = Paths.get("");
		String rutaImg = currentRelativePath.toAbsolutePath().toString();
		ImageIcon logoApp = new ImageIcon(rutaImg + "/src/main/resources/img/logo_lovaas.png");
		// Redimensionar el icono
		Image image = logoApp.getImage();
		Image newImg = image.getScaledInstance(250, 250, java.awt.Image.SCALE_SMOOTH);
		logoApp = new ImageIcon(newImg);
		lblLogoApp.setIcon(logoApp);

		lblSubtitulo = new JLabel("para autismo");
		lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblSubtitulo.setForeground(new Color(0, 102, 0));
		lblSubtitulo.setFont(new Font("Roboto Condensed", Font.PLAIN, 24));
		lblSubtitulo.setBounds(10, 429, 456, 37);
		panelIzquierdo.add(lblSubtitulo);

		barraAcciones = new JPanel();
		barraAcciones.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				arrastrarMousse(e.getXOnScreen(), e.getYOnScreen());
			}
		});
		barraAcciones.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				xMouse = e.getX();
				yMouse = e.getY();
			}
		});
		barraAcciones.setBackground(new Color(255, 250, 240));
		barraAcciones.setLayout(null);
		barraAcciones.setBounds(0, 0, 1280, 50);
		bg.add(barraAcciones);

		lblExit = new JLabel("X");
		lblExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblExit.setForeground(Color.red);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblExit.setForeground(Color.black);
			}
		});
		lblExit.setBounds(1230, 0, 50, 50);
		lblExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
		barraAcciones.add(lblExit);
		lblExit.setHorizontalAlignment(SwingConstants.CENTER);
		lblExit.setForeground(Color.BLACK);
		lblExit.setFont(new Font("Tahoma", Font.PLAIN, 30));

		panelDerecho = new JPanel();
		panelDerecho.setBackground(new Color(255, 250, 240));
		panelDerecho.setBounds(476, 0, 804, 720);
		bg.add(panelDerecho);
		panelDerecho.setLayout(null);

		lblIniciarSesion = new JLabel("INICIAR SESIÓN");
		lblIniciarSesion.setHorizontalAlignment(SwingConstants.LEFT);
		lblIniciarSesion.setFont(new Font("Roboto Condensed", Font.BOLD, 38));
		lblIniciarSesion.setBounds(38, 135, 305, 46);
		panelDerecho.add(lblIniciarSesion);

		lblUsuario = new JLabel("USUARIO");
		lblUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		lblUsuario.setFont(new Font("Roboto Condensed", Font.PLAIN, 30));
		lblUsuario.setBounds(38, 202, 305, 42);
		panelDerecho.add(lblUsuario);

		txtUsuario = new JTextField();
		txtUsuario.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					iniciarSesion();
				}
			}
		});
		txtUsuario.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				bordeUsuarioCampo.setBackground(Color.gray);
				lblErrorLogin.setText("");

				if (txtUsuario.getText().equals("Ingrese su nombre de usuario")) {
					txtUsuario.setText("");
					txtUsuario.setForeground(Color.black);
				}
				if (String.valueOf(passwordField.getPassword()).equals("")) {
					passwordField.setText("●●●●●●");
					passwordField.setForeground(Color.gray);
				}
			}
		});
		txtUsuario.setBackground(new Color(255, 250, 240));
		txtUsuario.setFont(new Font("Roboto Condensed", Font.PLAIN, 18));
		txtUsuario.setText("Ingrese su nombre de usuario");
		txtUsuario.setForeground(Color.GRAY);
		txtUsuario.setBounds(38, 254, 720, 45);

		txtUsuario.setBorder(javax.swing.BorderFactory.createEmptyBorder());// Eliminar bordes
		panelDerecho.add(txtUsuario);
		txtUsuario.setColumns(10);

		lblContraseña = new JLabel("CONTRASEÑA");
		lblContraseña.setHorizontalAlignment(SwingConstants.LEFT);
		lblContraseña.setFont(new Font("Roboto Condensed", Font.PLAIN, 30));
		lblContraseña.setBounds(38, 315, 305, 42);
		panelDerecho.add(lblContraseña);

		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					iniciarSesion();
				}
			}
		});
		passwordField.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

				bordePassCampo.setBackground(Color.gray);
				lblErrorLogin.setText("");

				if (String.valueOf(passwordField.getPassword()).equals("●●●●●●")) {
					passwordField.setText("");
					passwordField.setForeground(Color.black);
				}
				if (txtUsuario.getText().equals("")) {
					txtUsuario.setText("Ingrese su nombre de usuario");
					txtUsuario.setForeground(Color.gray);
				}
			}
		});
		passwordField.setText("●●●●●●");
		passwordField.setToolTipText("");
		passwordField.setBackground(new Color(255, 250, 240));
		passwordField.setFont(new Font("Tahoma", Font.PLAIN, 18));
		passwordField.setForeground(Color.GRAY);
		passwordField.setBounds(38, 356, 720, 45);
		passwordField.setBorder(javax.swing.BorderFactory.createEmptyBorder());// Eliminar bordes
		panelDerecho.add(passwordField);

		btnLogin = new JButton("ENTRAR");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iniciarSesion();
			}
		});
		btnLogin.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnLogin.setBackground(new Color(0, 214, 0));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnLogin.setBackground(new Color(0, 128, 0));
			}
		});
		btnLogin.setBackground(new Color(0, 128, 0));
		btnLogin.setFont(new Font("Roboto Condensed", Font.BOLD, 22));
		btnLogin.setForeground(new Color(255, 250, 240));
		btnLogin.setBounds(38, 441, 150, 50);
		btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panelDerecho.add(btnLogin);

		bordeUsuarioCampo = new Label("");
		bordeUsuarioCampo.setBackground(Color.GRAY);
		bordeUsuarioCampo.setBounds(38, 302, 720, 2);
		panelDerecho.add(bordeUsuarioCampo);

		bordePassCampo = new Label("");
		bordePassCampo.setBackground(Color.GRAY);
		bordePassCampo.setBounds(38, 403, 720, 2);
		panelDerecho.add(bordePassCampo);

		lblErrorLogin = new JLabel("");
		lblErrorLogin.setForeground(Color.RED);
		lblErrorLogin.setHorizontalAlignment(SwingConstants.LEFT);
		lblErrorLogin.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblErrorLogin.setBounds(38, 521, 328, 27);
		panelDerecho.add(lblErrorLogin);
	}

	/**
	 * Método que saca la información de los campos usuario, contraseña y se los
	 * pasa al controlador para realizar la petición de autenticación
	 */
	protected void iniciarSesion() {
		String usuario = txtUsuario.getText();
		String password = String.valueOf(passwordField.getPassword());
		int response = -1;
		try {
			response = controlador.iniciarSesion(usuario, password);
			respuestaLogin(response);
		} catch (InterruptedException | ExecutionException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Método que recibe la respuesta del Servicio y dependiendo la respuesta
	 * informa al usuario
	 * 
	 * @param response
	 */
	protected void respuestaLogin(int response) {
		switch (response) {
		case 0:
			lblErrorLogin.setForeground(Color.green);
			bordePassCampo.setBackground(Color.gray);
			bordeUsuarioCampo.setBackground(Color.gray);
			lblErrorLogin.setText("Inicio de Sesión Correcto");
			controlador.irVentanaDashboard();
			break;
		case 1:
			bordeUsuarioCampo.setBackground(Color.red);
			lblErrorLogin.setForeground(Color.red);
			lblErrorLogin.setText("El usuario está vacío");
			break;
		case 2:
			bordePassCampo.setBackground(Color.red);
			lblErrorLogin.setForeground(Color.red);
			lblErrorLogin.setText("La contraseña está vacía");
			break;
		case 3:
			bordeUsuarioCampo.setBackground(Color.red);
			bordePassCampo.setBackground(Color.red);
			lblErrorLogin.setText("Los campos están vacíos");
			break;
		case -1:
			bordeUsuarioCampo.setBackground(Color.red);
			bordePassCampo.setBackground(Color.red);
			lblErrorLogin.setForeground(Color.red);
			lblErrorLogin.setText("Usuario y/o contraseña incorrectos");
			break;
		}
	}

	/**
	 * Método que se desencadena dentro de un evento mouseDragged en la barra de
	 * opciones de la ventana y se coloca en la nueva posición
	 * 
	 * @param x posición del mouse en pantalla eje x
	 * @param y posición del mouse en pantalla eje y
	 */
	protected void arrastrarMousse(int x, int y) {
		this.setLocation(x - xMouse, y - yMouse);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		System.out.println("KeyTyped: " + e.getKeyChar());
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			System.out.println("VENTANA - ENTER");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		System.out.println("KeyReleased: " + e.getKeyChar());
	}

}
