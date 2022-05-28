package com.lovaas.center.vista;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.lovaas.center.controlador.ControladorVentanas;
import com.lovaas.center.modelo.entidad.Programa;
import com.lovaas.center.modelo.entidad.Terapeuta;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingConstants;
import java.awt.Cursor;
import javax.swing.border.MatteBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

@Component
public class VentanaDashboard extends JFrame {
	// Funcionalidad APP
	@Autowired
	private ControladorVentanas controlador;
	// Vista
	private JPanel bg;
	private MenuPanel menuLateral;
	// iconos
	private Path currentRelativePath;
	private String rutaImg;
	// Entidades
	@Autowired
	private Terapeuta terapeutaActual;
	@Autowired
	private Programa programaActual;
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
	private JPanel crudTerapeutas;
	private JTable tableTerapeutas;
	private JTable tableInformes;
	private JTable tableCitas;

	// Pestaña en la que nos encontramos
	private String nombreTabla = "terapeutas";
	private JPanel crudProgramas;
	private JLabel lblListadoProgramas;
	private JScrollPane scrllProgramas;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtCiudad;
	private JTextField txtTelf;
	private JButton btnAlta;
	private JLabel lblRespuestaTerapeuta;
	private JLabel lblNombrePrograma;
	private JTextField txtNombrePrograma;
	private JButton btnAltaPrograma;
	private JButton btnActualizarTerapeuta;
	private JButton btnActualizarPrograma;
	private JButton btnBorrarTerapeuta;
	private JButton btnBorrarPrograma;
	private JList<String> jListProgramas;
	private JScrollPane scrllUnidades;
	private JList<String> jLIstUnidades;
	private JLabel lblListadoUnidades;
	private JButton btnActualizarUnidad;
	private JButton btnEliminarUnidad;

	public ControladorVentanas getControlador() {
		return controlador;
	}

	public void setControlador(ControladorVentanas controlador) {
		this.controlador = controlador;
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

		panelProgramas = new JPanel();
		panelProgramas.setBackground(new Color(255, 250, 240));
		panelProgramas.setBounds(275, 50, 1005, 670);
		bg.add(panelProgramas);
		panelProgramas.setLayout(null);

		crudProgramas = new JPanel();
		crudProgramas.setBounds(49, 260, 900, 380);
		crudProgramas.setLayout(null);
		panelProgramas.add(crudProgramas);

		lblListadoProgramas = new JLabel("Listado Programas");
		lblListadoProgramas.setForeground(Color.DARK_GRAY);
		lblListadoProgramas.setFont(new Font("Roboto", Font.BOLD, 18));
		lblListadoProgramas.setBounds(10, 10, 207, 40);
		crudProgramas.add(lblListadoProgramas);

		scrllProgramas = new JScrollPane();
		scrllProgramas.setBounds(10, 60, 300, 310);
		crudProgramas.add(scrllProgramas);

		jListProgramas = new JList<String>();
		jListProgramas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				
				String nombrePrograma = jListProgramas.getSelectedValue();
				txtNombrePrograma.setText(nombrePrograma);
				// setteo nombre (id) al programa para despues obtener sus unidades
				programaActual.setNombre(nombrePrograma);
				try {
					controlador.obtenerUnidades(programaActual);
				} catch (InterruptedException | ExecutionException e1) {
					e1.printStackTrace();
				}
				// Una vez obtenidas las unidades mostrarlas en su Jlist.
				jLIstUnidades.setModel(controlador.generarModeloUnidades(programaActual.getUnidades()));
			}
		});
		jListProgramas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrllProgramas.setViewportView(jListProgramas);

		scrllUnidades = new JScrollPane();
		scrllUnidades.setBounds(383, 60, 300, 310);
		crudProgramas.add(scrllUnidades);

		jLIstUnidades = new JList<String>();
		scrllUnidades.setViewportView(jLIstUnidades);

		lblListadoUnidades = new JLabel("Unidades de programa");
		lblListadoUnidades.setForeground(Color.DARK_GRAY);
		lblListadoUnidades.setFont(new Font("Roboto", Font.BOLD, 18));
		lblListadoUnidades.setBounds(383, 10, 207, 40);
		crudProgramas.add(lblListadoUnidades);

		JButton btnAnadirUnidad = new JButton("Añadir unidad");
		btnAnadirUnidad.setForeground(new Color(255, 250, 240));
		btnAnadirUnidad.setFont(new Font("Roboto Condensed", Font.BOLD, 16));
		btnAnadirUnidad.setBackground(Color.GRAY);
		btnAnadirUnidad.setBounds(711, 121, 150, 50);
		crudProgramas.add(btnAnadirUnidad);

		btnActualizarUnidad = new JButton("Actualizar unidad");
		btnActualizarUnidad.setForeground(new Color(255, 250, 240));
		btnActualizarUnidad.setFont(new Font("Roboto Condensed", Font.BOLD, 16));
		btnActualizarUnidad.setBackground(Color.GRAY);
		btnActualizarUnidad.setBounds(711, 177, 150, 50);
		crudProgramas.add(btnActualizarUnidad);

		btnEliminarUnidad = new JButton("Eliminar unidad");
		btnEliminarUnidad.setForeground(new Color(255, 250, 240));
		btnEliminarUnidad.setFont(new Font("Roboto Condensed", Font.BOLD, 16));
		btnEliminarUnidad.setBackground(Color.GRAY);
		btnEliminarUnidad.setBounds(711, 237, 150, 50);
		crudProgramas.add(btnEliminarUnidad);

		lblNombrePrograma = new JLabel("Nombre del programa:");
		lblNombrePrograma.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombrePrograma.setForeground(Color.BLACK);
		lblNombrePrograma.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblNombrePrograma.setBounds(49, 124, 195, 40);
		panelProgramas.add(lblNombrePrograma);

		txtNombrePrograma = new JTextField();
		txtNombrePrograma.setColumns(10);
		txtNombrePrograma.setBounds(246, 124, 195, 40);
		panelProgramas.add(txtNombrePrograma);

		btnAltaPrograma = new JButton("Alta");
		;
		btnAltaPrograma.setForeground(new Color(255, 250, 240));
		btnAltaPrograma.setFont(new Font("Roboto Condensed", Font.BOLD, 22));
		btnAltaPrograma.setBackground(new Color(0, 128, 0));
		btnAltaPrograma.setBounds(451, 119, 150, 50);
		panelProgramas.add(btnAltaPrograma);

		btnActualizarPrograma = new JButton("Actualizar");

		btnBorrarPrograma = new JButton("Borrar");
		btnBorrarPrograma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCamposPrograma();
				try {
					controlador.borrarPrograma(programaActual);
				} catch (InterruptedException | ExecutionException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnBorrarPrograma.setForeground(new Color(255, 250, 240));
		btnBorrarPrograma.setFont(new Font("Roboto Condensed", Font.BOLD, 22));
		btnBorrarPrograma.setBackground(Color.RED);
		btnBorrarPrograma.setBounds(796, 119, 150, 50);
		panelProgramas.add(btnBorrarPrograma);
		btnActualizarPrograma.setForeground(new Color(255, 250, 240));
		btnActualizarPrograma.setFont(new Font("Roboto Condensed", Font.BOLD, 22));
		btnActualizarPrograma.setBackground(Color.GRAY);
		btnActualizarPrograma.setBounds(625, 119, 150, 50);
		panelProgramas.add(btnActualizarPrograma);

		panelTerapeutas = new JPanel();
		panelTerapeutas.setBackground(new Color(255, 250, 240));
		panelTerapeutas.setBounds(275, 50, 1005, 670);
		bg.add(panelTerapeutas);
		panelTerapeutas.setLayout(null);

		crudTerapeutas = new JPanel();
		crudTerapeutas.setBounds(49, 260, 900, 380);
		panelTerapeutas.add(crudTerapeutas);
		crudTerapeutas.setLayout(null);

		JLabel lblListadoTerapeutas = new JLabel("Listado Terapeutas");
		lblListadoTerapeutas.setForeground(Color.DARK_GRAY);
		lblListadoTerapeutas.setFont(new Font("Roboto", Font.BOLD, 18));
		lblListadoTerapeutas.setBounds(10, 10, 207, 40);
		crudTerapeutas.add(lblListadoTerapeutas);

		JScrollPane scrollPaneTrp = new JScrollPane();
		scrollPaneTrp.setBounds(10, 60, 880, 310);
		crudTerapeutas.add(scrollPaneTrp);

		tableTerapeutas = new JTable();
		tableTerapeutas.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				int fila = tableTerapeutas.getSelectedRow();
				txtNombre.setText((String) tableTerapeutas.getValueAt(fila, 3));
				txtApellidos.setText((String) tableTerapeutas.getValueAt(fila, 0));
				txtCiudad.setText((String) tableTerapeutas.getValueAt(fila, 1));
				txtTelf.setText((String) tableTerapeutas.getValueAt(fila, 2));
				// Setteo el objeto actual para obtener su id para su posible actualizacion
				terapeutaActual.setNombre(txtNombre.getText());
				terapeutaActual.setApellidos(txtApellidos.getText());
				terapeutaActual.setCiudad(txtCiudad.getText());
				terapeutaActual.setTelefono(txtTelf.getText());
				try {
					controlador.obtenerIdTerapeuta(terapeutaActual);
				} catch (InterruptedException | ExecutionException e1) {
					e1.printStackTrace();
				}
			}
		});
		scrollPaneTrp.setViewportView(tableTerapeutas);

		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setForeground(Color.BLACK);
		lblNombre.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblNombre.setBounds(81, 64, 150, 40);
		panelTerapeutas.add(lblNombre);

		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setHorizontalAlignment(SwingConstants.CENTER);
		lblApellidos.setForeground(Color.BLACK);
		lblApellidos.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblApellidos.setBounds(312, 64, 150, 40);
		panelTerapeutas.add(lblApellidos);

		JLabel lblCiudad = new JLabel("Ciudad");
		lblCiudad.setHorizontalAlignment(SwingConstants.CENTER);
		lblCiudad.setForeground(Color.BLACK);
		lblCiudad.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblCiudad.setBounds(543, 64, 150, 40);
		panelTerapeutas.add(lblCiudad);

		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setHorizontalAlignment(SwingConstants.CENTER);
		lblTelefono.setForeground(Color.BLACK);
		lblTelefono.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblTelefono.setBounds(774, 64, 150, 40);
		panelTerapeutas.add(lblTelefono);

		txtNombre = new JTextField();
		txtNombre.setBounds(81, 114, 150, 40);
		panelTerapeutas.add(txtNombre);
		txtNombre.setColumns(10);

		txtApellidos = new JTextField();
		txtApellidos.setColumns(10);
		txtApellidos.setBounds(312, 114, 150, 40);
		panelTerapeutas.add(txtApellidos);

		txtCiudad = new JTextField();
		txtCiudad.setColumns(10);
		txtCiudad.setBounds(543, 114, 150, 40);
		panelTerapeutas.add(txtCiudad);

		txtTelf = new JTextField();
		txtTelf.setColumns(10);
		txtTelf.setBounds(774, 114, 150, 40);
		panelTerapeutas.add(txtTelf);

		btnAlta = new JButton("Alta");
		btnAlta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = txtNombre.getText();
				String apellidos = txtApellidos.getText();
				String ciudad = txtCiudad.getText();
				String telf = txtTelf.getText();
				Terapeuta terapeuta = new Terapeuta(nombre, apellidos, ciudad, telf);
				limpiarCamposTerapeuta();
				int alta = controlador.altaTerapeuta(terapeuta);
				try {
					tableTerapeutas.setModel(controlador.getTabla(nombreTabla));
				} catch (InterruptedException | ExecutionException e1) {
					e1.printStackTrace();
				}
				respuestaAltaTerapeuta(alta);
			}
		});
		btnAlta.setForeground(new Color(255, 250, 240));
		btnAlta.setFont(new Font("Roboto Condensed", Font.BOLD, 22));
		btnAlta.setBackground(new Color(0, 128, 0));
		btnAlta.setBounds(262, 176, 150, 50);
		panelTerapeutas.add(btnAlta);

		lblRespuestaTerapeuta = new JLabel("");
		lblRespuestaTerapeuta.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRespuestaTerapeuta.setForeground(Color.GREEN);
		lblRespuestaTerapeuta.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblRespuestaTerapeuta.setBounds(621, 230, 328, 27);
		panelTerapeutas.add(lblRespuestaTerapeuta);

		btnActualizarTerapeuta = new JButton("Actualizar");
		btnActualizarTerapeuta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nombre = txtNombre.getText();
				String apellidos = txtApellidos.getText();
				String ciudad = txtCiudad.getText();
				String telf = txtTelf.getText();
				// Actualizamos los datos del objeto
				terapeutaActual.setNombre(nombre);
				terapeutaActual.setApellidos(apellidos);
				terapeutaActual.setCiudad(ciudad);
				terapeutaActual.setTelefono(telf);
				limpiarCamposTerapeuta();
				try {
					boolean update = controlador.actualizarTerapeuta(terapeutaActual);// Actualizamos en bd
					tableTerapeutas.setModel(controlador.getTabla(nombreTabla));
				} catch (InterruptedException | ExecutionException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnActualizarTerapeuta.setForeground(new Color(255, 250, 240));
		btnActualizarTerapeuta.setFont(new Font("Roboto Condensed", Font.BOLD, 22));
		btnActualizarTerapeuta.setBackground(Color.GRAY);
		btnActualizarTerapeuta.setBounds(436, 176, 150, 50);
		panelTerapeutas.add(btnActualizarTerapeuta);

		btnBorrarTerapeuta = new JButton("Borrar");
		btnBorrarTerapeuta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCamposTerapeuta();
				try {
					controlador.eliminarTerapeuta(terapeutaActual);
					tableTerapeutas.setModel(controlador.getTabla(nombreTabla));
				} catch (InterruptedException | ExecutionException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnBorrarTerapeuta.setForeground(new Color(255, 250, 240));
		btnBorrarTerapeuta.setFont(new Font("Roboto Condensed", Font.BOLD, 22));
		btnBorrarTerapeuta.setBackground(Color.RED);
		btnBorrarTerapeuta.setBounds(607, 176, 150, 50);
		panelTerapeutas.add(btnBorrarTerapeuta);

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
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				try {
					switch (nombreTabla) {
					case "terapeutas":
						tableTerapeutas.setModel(controlador.getTabla(nombreTabla));
						break;
					case "programasAndroid":
						jListProgramas.setModel(controlador.getListModel(nombreTabla));
						break;
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});

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
	 * Metodo para informar el usuario sobre su petición de alta en Terapeutas
	 * 
	 * @param alta
	 */
	protected void respuestaAltaTerapeuta(int alta) {
		switch (alta) {
		case 0:
			lblRespuestaTerapeuta.setText("Error al dar de alta");
			break;
		case 1:
			lblRespuestaTerapeuta.setText("Alta correctamente");
			break;
		case -1:
			lblRespuestaTerapeuta.setText("Nombre o Apellidos no pueden estar vacíos");
			break;

		}
	}

	protected void limpiarCamposPrograma() {
		txtNombrePrograma.setText("");
	}

	protected void limpiarCamposTerapeuta() {
		txtNombre.setText("");
		txtApellidos.setText("");
		txtCiudad.setText("");
		txtTelf.setText("");
	}

	/**
	 * Metodo para hacer visible solo el panel de terapeutas
	 */
	protected void visibleTerapeutas() {
		nombreTabla = "terapeutas";
		try {
			tableTerapeutas.setModel(controlador.getTabla(nombreTabla));
		} catch (InterruptedException | ExecutionException e1) {
			e1.printStackTrace();
		}
		panelTerapeutas.setVisible(true);
		panelInformes.setVisible(false);
		panelCitas.setVisible(false);
		panelProgramas.setVisible(false);
	}

	/**
	 * Metodo para hacer visible solo el panel de informes
	 */
	protected void visibleInformes() {
		nombreTabla = "informes";
		panelTerapeutas.setVisible(false);
		panelInformes.setVisible(true);
		panelCitas.setVisible(false);
		panelProgramas.setVisible(false);
	}

	/**
	 * Metodo para hacer visible solo el panel de citas
	 */
	protected void visibleCitas() {
		nombreTabla = "citas";
		panelTerapeutas.setVisible(false);
		panelInformes.setVisible(false);
		panelCitas.setVisible(true);
		panelProgramas.setVisible(false);
	}

	/**
	 * Metodo para hacer visible solo el panel de programas
	 */
	protected void visibleProgramas() {
		nombreTabla = "programasAndroid";

		try {
			jListProgramas.setModel(controlador.getListModel(nombreTabla));
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

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
