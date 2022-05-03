package com.lovaas.center.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorListener;

import com.lovaas.center.controlador.ControladorVentanas;

import javax.swing.event.AncestorEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.SwingConstants;
import java.awt.Cursor;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class VentanaDashboard extends JFrame {
	// Funcionalidad APP
	private ControladorVentanas controlador;
	// Vista
	private JPanel bg;
	private MenuPanel menuLateral;
	// para los iconos
	private Path currentRelativePath;
	private String rutaImg;
	// Posicion mouse
	private int xMouse, yMouse;
	private JPanel panelTerapeutas;
	private JPanel panelProgramas;
	private JPanel panelInformes;
	private JLabel iconTerapeutas;
	private JLabel iconProgramas;
	private JLabel lblInformes;
	private JLabel lblProgramas;
	private JLabel lblTerapeutas;
	private JLabel iconInformes;
	private JLabel iconCitas;
	private JLabel lblLogout;
	private JLabel iconLogout;
	private JLabel lblCitas;
	private JLabel lblExit;
	private JPanel panelCitas;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaDashboard frame = new VentanaDashboard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaDashboard() {
		setResizable(false);
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		setLocationRelativeTo(null); // centrar el JFrame
		bg = new JPanel();
		bg.setBackground(new Color(255, 255, 240));
		bg.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(bg);
		bg.setLayout(null);

		menuLateral = new MenuPanel();
		// menuLateral.setOpaque(false);
		menuLateral.setBounds(0, 50, 277, 670);
		bg.add(menuLateral);
		menuLateral.setLayout(null);

		JLabel lblTitleApp = new JLabel("LÖVAAS\r\n CENTER");
		// Sacar ruta absoluta
		currentRelativePath = Paths.get("");
		rutaImg = currentRelativePath.toAbsolutePath().toString();
		ImageIcon logoApp = new ImageIcon(rutaImg + "/src/main/resources/img/logo_lovaas_blanco.png");
		// Redimensionar el icono
		Image image = logoApp.getImage();
		Image newImg = image.getScaledInstance(45, 45, java.awt.Image.SCALE_SMOOTH);
		logoApp = new ImageIcon(newImg);
		lblTitleApp.setIcon(logoApp);
		lblTitleApp.setForeground(Color.WHITE);
		lblTitleApp.setFont(new Font("Oswald SemiBold", Font.BOLD, 24));
		lblTitleApp.setBounds(10, 15, 257, 50);
		menuLateral.add(lblTitleApp);

		iconTerapeutas = new JLabel("");
		iconTerapeutas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				visibleTerapeutas();
			}
		});
		iconTerapeutas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// Sacar ruta absoluta
		ImageIcon terapeutaIcon = new ImageIcon(rutaImg + "/src/main/resources/img/terapeuta_icon.png");
		// Redimensionar el icono
		Image imgTp = terapeutaIcon.getImage();
		Image newImgTp = imgTp.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		terapeutaIcon = new ImageIcon(newImgTp);
		iconTerapeutas.setIcon(terapeutaIcon);
		iconTerapeutas.setBounds(20, 100, 30, 30);
		menuLateral.add(iconTerapeutas);

		iconProgramas = new JLabel("");
		iconProgramas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				visibleProgramas();
			}
		});
		iconProgramas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// Sacar ruta absoluta
		ImageIcon programasIcon = new ImageIcon(rutaImg + "/src/main/resources/img/programa_icon.png");
		// Redimensionar el icono
		Image imgPrg = programasIcon.getImage();
		Image newImgPrg = imgPrg.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		terapeutaIcon = new ImageIcon(newImgPrg);
		iconProgramas.setIcon(programasIcon);
		iconProgramas.setBounds(20, 150, 30, 30);
		menuLateral.add(iconProgramas);

		iconInformes = new JLabel("");
		iconInformes.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				visibleInformes();
			}
		});
		iconInformes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// Sacar ruta absoluta
		ImageIcon informesIcon = new ImageIcon(rutaImg + "/src/main/resources/img/informes_icon.png");
		// Redimensionar el icono
		Image imgInf = informesIcon.getImage();
		Image newImgInf = imgInf.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		informesIcon = new ImageIcon(newImgInf);
		iconInformes.setIcon(informesIcon);
		iconInformes.setBounds(20, 200, 30, 30);
		menuLateral.add(iconInformes);

		iconCitas = new JLabel("");
		iconCitas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				visibleCitas();
			}
		});
		iconCitas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// Sacar ruta absoluta
		ImageIcon citasIcon = new ImageIcon(rutaImg + "/src/main/resources/img/citas_icon.png");
		// Redimensionar el icono
		Image imgCts = citasIcon.getImage();
		Image newImgCts = imgCts.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		citasIcon = new ImageIcon(newImgCts);
		iconCitas.setIcon(citasIcon);
		iconCitas.setBounds(20, 252, 30, 30);
		menuLateral.add(iconCitas);

		lblTerapeutas = new JLabel("Terapeutas");
		lblTerapeutas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				visibleTerapeutas();
			}
		});
		lblTerapeutas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblTerapeutas.setForeground(Color.WHITE);
		lblTerapeutas.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblTerapeutas.setBounds(60, 95, 207, 40);
		menuLateral.add(lblTerapeutas);

		lblProgramas = new JLabel("Programas");
		lblProgramas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				visibleProgramas();
			}
		});
		lblProgramas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblProgramas.setForeground(Color.WHITE);
		lblProgramas.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblProgramas.setBounds(60, 145, 207, 40);
		menuLateral.add(lblProgramas);

		lblInformes = new JLabel("Informes");
		lblInformes.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				visibleInformes();
			}
		});
		lblInformes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblInformes.setForeground(Color.WHITE);
		lblInformes.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblInformes.setBounds(60, 195, 207, 40);
		menuLateral.add(lblInformes);

		lblCitas = new JLabel("Citas");
		lblCitas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				visibleCitas();
			}
		});
		lblCitas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblCitas.setForeground(Color.WHITE);
		lblCitas.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblCitas.setBounds(60, 247, 207, 40);
		menuLateral.add(lblCitas);

		lblLogout = new JLabel("Cerrar sesión");
		lblLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lblLogout.setForeground(Color.red);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblLogout.setForeground(Color.white);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				controlador.logout();
			}
		});
		lblLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblLogout.setForeground(Color.WHITE);
		lblLogout.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblLogout.setBounds(60, 622, 207, 40);
		menuLateral.add(lblLogout);

		iconLogout = new JLabel("");
		/**
		 * Metodo para cambiar el mismo icono por otro de color rojo al dejar el mouse
		 * sobre iconLogout
		 */
		iconLogout.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon logoutIcon = new ImageIcon(rutaImg + "/src/main/resources/img/logout_icon_red.png");
				// Redimensionar el icono
				Image imgLgt = logoutIcon.getImage();
				Image newImgLgt = imgLgt.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
				logoutIcon = new ImageIcon(newImgLgt);
				iconLogout.setIcon(logoutIcon);
			}

			/**
			 * Metodo para volver a poner el icono original quitar el mouse de iconLogout
			 */
			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon logoutIcon = new ImageIcon(rutaImg + "/src/main/resources/img/logout_icon.png");
				// Redimensionar el icono
				Image imgLgt = logoutIcon.getImage();
				Image newImgLgt = imgLgt.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
				logoutIcon = new ImageIcon(newImgLgt);
				iconLogout.setIcon(logoutIcon);
			}
		});
		iconLogout.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		// Sacar ruta absoluta
		ImageIcon logoutIcon = new ImageIcon(rutaImg + "/src/main/resources/img/logout_icon.png");
		// Redimensionar el icono
		Image imgLgt = logoutIcon.getImage();
		Image newImgLgt = imgLgt.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		logoutIcon = new ImageIcon(newImgLgt);
		iconLogout.setIcon(logoutIcon);
		iconLogout.setBounds(20, 627, 30, 30);
		menuLateral.add(iconLogout);

		JPanel barraAcciones = new JPanel();
		barraAcciones.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(192, 192, 192)));
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
		barraAcciones.setLayout(null);
		barraAcciones.setBackground(new Color(255, 250, 240));
		barraAcciones.setBounds(0, 0, 1280, 50);
		bg.add(barraAcciones);

		lblExit = new JLabel("X");
		lblExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
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
		lblExit.setHorizontalAlignment(SwingConstants.CENTER);
		lblExit.setForeground(Color.BLACK);
		lblExit.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblExit.setBounds(1230, 0, 50, 50);
		barraAcciones.add(lblExit);

		JLabel lblMinimizar = new JLabel("_");
		lblMinimizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblMinimizar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setExtendedState(ICONIFIED); // Minimiza la ventana
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				lblMinimizar.setForeground(Color.gray);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblMinimizar.setForeground(Color.black);
			}
		});
		lblMinimizar.setVerticalAlignment(SwingConstants.TOP);
		lblMinimizar.setHorizontalAlignment(SwingConstants.CENTER);
		lblMinimizar.setForeground(Color.BLACK);
		lblMinimizar.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblMinimizar.setBounds(1176, 0, 50, 50);
		barraAcciones.add(lblMinimizar);

		panelTerapeutas = new JPanel();
		panelTerapeutas.setBackground(new Color(255, 250, 240));
		panelTerapeutas.setBounds(275, 50, 1005, 670);
		bg.add(panelTerapeutas);

		panelProgramas = new JPanel();
		panelProgramas.setBackground(Color.YELLOW);
		panelProgramas.setBounds(275, 50, 1005, 670);
		bg.add(panelProgramas);

		panelInformes = new JPanel();
		panelInformes.setBackground(Color.MAGENTA);
		panelInformes.setBounds(275, 50, 1005, 670);
		bg.add(panelInformes);

		panelCitas = new JPanel();
		panelCitas.setBackground(Color.BLUE);
		panelCitas.setBounds(276, 50, 1005, 670);
		bg.add(panelCitas);
	}

	/**
	 * Metodo para hacer visible solo el panel de terapeutas
	 */
	protected void visibleTerapeutas() {
		panelTerapeutas.setVisible(true);
		panelInformes.setVisible(false);
		panelCitas.setVisible(false);
		panelProgramas.setVisible(false);
	}

	/**
	 * Metodo para hacer visible solo el panel de informes
	 */
	protected void visibleInformes() {
		panelTerapeutas.setVisible(false);
		panelInformes.setVisible(true);
		panelCitas.setVisible(false);
		panelProgramas.setVisible(false);
	}

	/**
	 * Metodo para hacer visible solo el panel de citas
	 */
	protected void visibleCitas() {
		panelTerapeutas.setVisible(false);
		panelInformes.setVisible(false);
		panelCitas.setVisible(true);
		panelProgramas.setVisible(false);
	}

	/**
	 * Metodo para hacer visible solo el panel de programas
	 */
	protected void visibleProgramas() {
		panelTerapeutas.setVisible(false);
		panelInformes.setVisible(false);
		panelCitas.setVisible(false);
		panelProgramas.setVisible(true);
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
}
