package com.lovaas.center.vista;

import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.google.protobuf.DescriptorProtos.OneofOptions;
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
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingConstants;
import java.awt.Cursor;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListDataListener;
import javax.swing.JTable;
import javax.swing.JScrollPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
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
	private TreeMap<String, String> unidadActual;
	// Posicion mouse
	private int xMouse, yMouse;
	private JPanel panelTerapeutas;
	private JPanel panelProgramas;
	private JLabel iconTerapeutas;
	private JLabel iconProgramas;
	private JLabel lblProgramas;
	private JLabel lblTerapeutas;
	private JLabel lblLogout;
	private JLabel iconLogout;
	private JLabel lblExit;
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
	private JButton btnAltaTerapeuta;
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
	private JButton btnEliminarUnidad;
	private JLabel lblRespuestaPrograma;
	private JPanel panelCamposTp;
	private JPanel panelCamposPrg;

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

		// Sacar ruta absoluta
		currentRelativePath = Paths.get("");
		rutaImg = currentRelativePath.toAbsolutePath().toString();

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
		panelProgramas.setVisible(false);// Para que no se vea cuando inicia la aplicacion

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
				lblRespuestaTerapeuta.setText("");

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
		lblNombre.setHorizontalTextPosition(SwingConstants.CENTER);
		lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombre.setForeground(Color.BLACK);
		lblNombre.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblNombre.setBounds(81, 64, 150, 40);
		panelTerapeutas.add(lblNombre);

		JLabel lblApellidos = new JLabel("Apellidos");
		lblApellidos.setHorizontalTextPosition(SwingConstants.CENTER);
		lblApellidos.setHorizontalAlignment(SwingConstants.CENTER);
		lblApellidos.setForeground(Color.BLACK);
		lblApellidos.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblApellidos.setBounds(312, 64, 150, 40);
		panelTerapeutas.add(lblApellidos);

		JLabel lblCiudad = new JLabel("Ciudad");
		lblCiudad.setHorizontalTextPosition(SwingConstants.CENTER);
		lblCiudad.setHorizontalAlignment(SwingConstants.CENTER);
		lblCiudad.setForeground(Color.BLACK);
		lblCiudad.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblCiudad.setBounds(543, 64, 150, 40);
		panelTerapeutas.add(lblCiudad);

		JLabel lblTelefono = new JLabel("Telefono");
		lblTelefono.setHorizontalTextPosition(SwingConstants.CENTER);
		lblTelefono.setHorizontalAlignment(SwingConstants.CENTER);
		lblTelefono.setForeground(Color.BLACK);
		lblTelefono.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblTelefono.setBounds(774, 64, 150, 40);
		panelTerapeutas.add(lblTelefono);

		txtNombre = new JTextField();
		txtNombre.setFont(new Font("Roboto", Font.PLAIN, 14));
		txtNombre.setHorizontalAlignment(SwingConstants.CENTER);
		txtNombre.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				lblRespuestaTerapeuta.setText("");
			}
		});
		txtNombre.setBounds(81, 114, 150, 40);
		panelTerapeutas.add(txtNombre);
		txtNombre.setColumns(10);

		txtApellidos = new JTextField();
		txtApellidos.setFont(new Font("Roboto", Font.PLAIN, 14));
		txtApellidos.setHorizontalAlignment(SwingConstants.CENTER);
		txtApellidos.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				lblRespuestaTerapeuta.setText("");
			}
		});
		txtApellidos.setColumns(10);
		txtApellidos.setBounds(312, 114, 150, 40);
		panelTerapeutas.add(txtApellidos);

		txtCiudad = new JTextField();
		txtCiudad.setFont(new Font("Roboto", Font.PLAIN, 14));
		txtCiudad.setHorizontalAlignment(SwingConstants.CENTER);
		txtCiudad.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				lblRespuestaTerapeuta.setText("");
			}
		});
		txtCiudad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblRespuestaPrograma.setText("");
			}
		});
		txtCiudad.setColumns(10);
		txtCiudad.setBounds(543, 114, 150, 40);
		panelTerapeutas.add(txtCiudad);

		txtTelf = new JTextField();
		txtTelf.setFont(new Font("Roboto", Font.PLAIN, 14));
		txtTelf.setHorizontalAlignment(SwingConstants.CENTER);
		txtTelf.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				lblRespuestaTerapeuta.setText("");
			}
		});
		txtTelf.setColumns(10);
		txtTelf.setBounds(774, 114, 150, 40);
		panelTerapeutas.add(txtTelf);

		Image imgAlta = new ImageIcon(rutaImg + "/src/main/resources/img/agregar_verde_50.png").getImage();
		ImageIcon iconAlta = new ImageIcon(imgAlta.getScaledInstance(45, 45, Image.SCALE_SMOOTH));

		btnAltaTerapeuta = new JButton(iconAlta);
		btnAltaTerapeuta.setForeground(Color.WHITE);
		btnAltaTerapeuta.setFont(new Font("Roboto Condensed", Font.BOLD, 22));
		btnAltaTerapeuta.setText("Alta");
		btnAltaTerapeuta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int alta = -1;
				String nombre = txtNombre.getText();
				String apellidos = txtApellidos.getText();
				String ciudad = txtCiudad.getText();
				String telf = txtTelf.getText();
				Terapeuta terapeuta = new Terapeuta(nombre, apellidos, ciudad, telf);
				try {
					alta = controlador.altaTerapeuta(terapeuta);
					respuestaTerapeuta(alta);
					tableTerapeutas.setModel(controlador.getTabla(nombreTabla));
				} catch (InterruptedException | ExecutionException e1) {
					e1.printStackTrace();
				}
				if (alta == 1) {
					limpiarCamposTerapeuta();
				}
			}
		});
		btnAltaTerapeuta.setBackground(new Color(0, 128, 0));
		btnAltaTerapeuta.setBounds(262, 180, 150, 50);
		panelTerapeutas.add(btnAltaTerapeuta);

		lblRespuestaTerapeuta = new JLabel("");
		lblRespuestaTerapeuta.setHorizontalTextPosition(SwingConstants.LEFT);
		lblRespuestaTerapeuta.setForeground(Color.GRAY);
		lblRespuestaTerapeuta.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblRespuestaTerapeuta.setBounds(49, 230, 328, 27);
		panelTerapeutas.add(lblRespuestaTerapeuta);

		btnActualizarTerapeuta = new JButton("Actualizar");
		btnActualizarTerapeuta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int update = -1;
				String nombre = txtNombre.getText();
				String apellidos = txtApellidos.getText();
				String ciudad = txtCiudad.getText();
				String telf = txtTelf.getText();
				// Actualizamos los datos del objeto
				terapeutaActual.setNombre(nombre);
				terapeutaActual.setApellidos(apellidos);
				terapeutaActual.setCiudad(ciudad);
				terapeutaActual.setTelefono(telf);
				try {
					update = controlador.actualizarTerapeuta(terapeutaActual);// Actualizamos en bd
					respuestaTerapeuta(update);
					tableTerapeutas.setModel(controlador.getTabla(nombreTabla));
				} catch (InterruptedException | ExecutionException e1) {
					e1.printStackTrace();
				}
				if (update == 0) {
					limpiarCamposTerapeuta();
				}
			}
		});
		btnActualizarTerapeuta.setForeground(new Color(255, 250, 240));
		btnActualizarTerapeuta.setFont(new Font("Roboto Condensed", Font.BOLD, 22));
		btnActualizarTerapeuta.setBackground(Color.GRAY);
		btnActualizarTerapeuta.setBounds(422, 180, 150, 50);
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
		btnBorrarTerapeuta.setBounds(583, 180, 150, 50);
		panelTerapeutas.add(btnBorrarTerapeuta);

		panelCamposTp = new JPanel();
		panelCamposTp.setBounds(49, 50, 914, 120);
		panelTerapeutas.add(panelCamposTp);
		panelCamposTp.setLayout(null);
		panelProgramas.setBackground(new Color(255, 250, 240));
		panelProgramas.setBounds(275, 50, 1005, 670);
		bg.add(panelProgramas);
		panelProgramas.setLayout(null);

		crudProgramas = new JPanel();
		crudProgramas.setBounds(49, 260, 900, 380);
		crudProgramas.setLayout(null);
		panelProgramas.add(crudProgramas);

		lblListadoProgramas = new JLabel("Listado Programas");
		lblListadoProgramas.setHorizontalAlignment(SwingConstants.CENTER);
		lblListadoProgramas.setForeground(Color.DARK_GRAY);
		lblListadoProgramas.setFont(new Font("Roboto", Font.BOLD, 18));
		lblListadoProgramas.setBounds(22, 10, 300, 40);
		crudProgramas.add(lblListadoProgramas);

		scrllProgramas = new JScrollPane();
		scrllProgramas.setBounds(22, 60, 300, 310);
		crudProgramas.add(scrllProgramas);

		jListProgramas = new JList<String>();
		jListProgramas.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void mousePressed(MouseEvent e) {
				lblRespuestaPrograma.setText("");

				if (!jListProgramas.isSelectionEmpty()) {

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
					jLIstUnidades.setModel(controlador.obtenerModeloUnidades(programaActual.getUnidades()));
				}

			}
		});
		jListProgramas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrllProgramas.setViewportView(jListProgramas);

		scrllUnidades = new JScrollPane();
		scrllUnidades.setBounds(395, 60, 300, 310);
		crudProgramas.add(scrllUnidades);

		jLIstUnidades = new JList<String>();
		jLIstUnidades.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				lblRespuestaPrograma.setText("");

				if (!jLIstUnidades.isSelectionEmpty()) {
					String parUnidad = jLIstUnidades.getSelectedValue();
					System.out.println(parUnidad);
					// Pedimos al controlador que nos convierta el String a clave - valor
					unidadActual = controlador.getClaveValor(parUnidad);
				}

			}
		});
		jLIstUnidades.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrllUnidades.setViewportView(jLIstUnidades);

		lblListadoUnidades = new JLabel("Unidades de programa");
		lblListadoUnidades.setHorizontalAlignment(SwingConstants.CENTER);
		lblListadoUnidades.setForeground(Color.DARK_GRAY);
		lblListadoUnidades.setFont(new Font("Roboto", Font.BOLD, 18));
		lblListadoUnidades.setBounds(395, 10, 300, 40);
		crudProgramas.add(lblListadoUnidades);

		JButton btnAnadirUnidad = new JButton("Añadir unidad");
		btnAnadirUnidad.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				if (!jListProgramas.isSelectionEmpty()) {
					String nombreUnidad = "";
					nombreUnidad = JOptionPane.showInputDialog(crudProgramas, "Nombre de la unidad: ", "Asignar Unidad",
							JOptionPane.DEFAULT_OPTION);
					System.out.println(nombreUnidad);
					int response = controlador.asinarUnidadPrograma(programaActual, nombreUnidad);
					if (response == 1) {
						try {
							controlador.actualizarUnidadesPrograma(programaActual);
							jLIstUnidades.setModel(controlador.obtenerModeloUnidades(programaActual.getUnidades()));
						} catch (InterruptedException | ExecutionException e1) {
							e1.printStackTrace();
						}
					} else {
						respuestaPrograma("unidad", response);
					}

				}

			}
		});
		btnAnadirUnidad.setForeground(new Color(255, 250, 240));
		btnAnadirUnidad.setFont(new Font("Roboto Condensed", Font.BOLD, 16));
		btnAnadirUnidad.setBackground(new Color(0, 128, 0));
		btnAnadirUnidad.setBounds(721, 147, 150, 50);
		crudProgramas.add(btnAnadirUnidad);

		btnEliminarUnidad = new JButton("Eliminar unidad");
		btnEliminarUnidad.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			public void actionPerformed(ActionEvent e) {
				try {
					controlador.eliminarUnidadPrograma(programaActual, unidadActual);
//					jListProgramas.setModel(controlador.getListModel(nombreTabla));
					jLIstUnidades.setModel(controlador.obtenerModeloUnidades(programaActual.getUnidades()));
				} catch (InterruptedException | ExecutionException e2) {
					e2.printStackTrace();
				}

			}
		});
		btnEliminarUnidad.setForeground(new Color(255, 250, 240));
		btnEliminarUnidad.setFont(new Font("Roboto Condensed", Font.BOLD, 16));
		btnEliminarUnidad.setBackground(Color.RED);
		btnEliminarUnidad.setBounds(721, 207, 150, 50);
		crudProgramas.add(btnEliminarUnidad);

		lblNombrePrograma = new JLabel("Nombre del programa:");
		lblNombrePrograma.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombrePrograma.setForeground(Color.BLACK);
		lblNombrePrograma.setFont(new Font("Roboto", Font.PLAIN, 18));
		lblNombrePrograma.setBounds(59, 89, 195, 40);
		panelProgramas.add(lblNombrePrograma);

		txtNombrePrograma = new JTextField();
		txtNombrePrograma.setHorizontalAlignment(SwingConstants.CENTER);
		txtNombrePrograma.setFont(new Font("Roboto", Font.PLAIN, 14));
		txtNombrePrograma.setColumns(10);
		txtNombrePrograma.setBounds(256, 89, 195, 40);
		panelProgramas.add(txtNombrePrograma);

		btnAltaPrograma = new JButton("Alta");
		btnAltaPrograma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int alta = -1;
				String nombrePrograma = txtNombrePrograma.getText();
				programaActual.setNombre(nombrePrograma);

				try {
					alta = controlador.altaPrograma(programaActual);
					respuestaPrograma("programa", alta);
					jListProgramas.setModel(controlador.getListModel(nombreTabla));
				} catch (InterruptedException | ExecutionException e1) {
					e1.printStackTrace();
				}

				limpiarCamposPrograma();
			}
		});
		btnAltaPrograma.setForeground(new Color(255, 250, 240));
		btnAltaPrograma.setFont(new Font("Roboto Condensed", Font.BOLD, 22));
		btnAltaPrograma.setBackground(new Color(0, 128, 0));
		btnAltaPrograma.setBounds(461, 82, 150, 50);
		panelProgramas.add(btnAltaPrograma);

		btnActualizarPrograma = new JButton("Actualizar");
		btnActualizarPrograma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nuevoNombrePrg = txtNombrePrograma.getText();
			}
		});

		btnBorrarPrograma = new JButton("Borrar");
		btnBorrarPrograma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCamposPrograma();
				try {
					boolean eliminado = controlador.eliminarPrograma(programaActual);
					respuestaEliminadoPrograma(eliminado);
					jListProgramas.setModel(controlador.getListModel(nombreTabla));
//					jLIstUnidades.setModel(controlador.obtenerModeloUnidades(programaActual.getUnidades()));
					jLIstUnidades.setModel(new DefaultListModel<String>());
				} catch (InterruptedException | ExecutionException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnBorrarPrograma.setForeground(new Color(255, 250, 240));
		btnBorrarPrograma.setFont(new Font("Roboto Condensed", Font.BOLD, 22));
		btnBorrarPrograma.setBackground(Color.RED);
		btnBorrarPrograma.setBounds(804, 82, 150, 50);
		panelProgramas.add(btnBorrarPrograma);
		btnActualizarPrograma.setForeground(new Color(255, 250, 240));
		btnActualizarPrograma.setFont(new Font("Roboto Condensed", Font.BOLD, 22));
		btnActualizarPrograma.setBackground(Color.GRAY);
		btnActualizarPrograma.setBounds(631, 82, 150, 50);
		panelProgramas.add(btnActualizarPrograma);

		lblRespuestaPrograma = new JLabel("");
		lblRespuestaPrograma.setHorizontalTextPosition(SwingConstants.LEFT);
		lblRespuestaPrograma.setForeground(Color.GRAY);
		lblRespuestaPrograma.setFont(new Font("Roboto", Font.PLAIN, 14));
		lblRespuestaPrograma.setBounds(49, 230, 328, 27);
		panelProgramas.add(lblRespuestaPrograma);

		panelCamposPrg = new JPanel();
		panelCamposPrg.setLayout(null);
		panelCamposPrg.setBounds(49, 50, 914, 120);
		panelProgramas.add(panelCamposPrg);
		;

		menuLateral = new MenuPanel();
		// menuLateral.setOpaque(false);
		menuLateral.setBounds(0, 50, 277, 670);
		bg.add(menuLateral);
		menuLateral.setLayout(null);

		JLabel lblTitleApp = new JLabel("LÖVAAS\r\n CENTER");
//		// Sacar ruta absoluta
//		currentRelativePath = Paths.get("");
//		rutaImg = currentRelativePath.toAbsolutePath().toString();
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
		// Sacar ruta absoluta
		ImageIcon informesIcon = new ImageIcon(rutaImg + "/src/main/resources/img/informes_icon.png");
		// Redimensionar el icono
		Image imgInf = informesIcon.getImage();
		Image newImgInf = imgInf.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		informesIcon = new ImageIcon(newImgInf);
		// Sacar ruta absoluta
		ImageIcon citasIcon = new ImageIcon(rutaImg + "/src/main/resources/img/citas_icon.png");
		// Redimensionar el icono
		Image imgCts = citasIcon.getImage();
		Image newImgCts = imgCts.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
		citasIcon = new ImageIcon(newImgCts);

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
	}

	protected void respuestaEliminadoPrograma(boolean response) {
		if (response) {
			lblRespuestaPrograma.setText("Programa eliminado correctamente");
			try {
				TimeUnit.SECONDS.sleep(4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lblRespuestaPrograma.setText("");
		} else {
			lblRespuestaPrograma.setText("No se pudo eliminar Programa");
			try {
				TimeUnit.SECONDS.sleep(4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lblRespuestaPrograma.setText("");
		}
	}

	/**
	 * Metodo para informar el usuario sobre su petición de alta en Terapeutas
	 * 
	 * @param alta
	 */
	protected void respuestaTerapeuta(int alta) {
		switch (alta) {
		case -1:
			lblRespuestaTerapeuta.setText("Ocurrió un error al registrar Terapeuta");
			break;
		case 0:
			lblRespuestaTerapeuta.setText("Terapeuta registrado correctamente");
			break;
		case 2:
			lblRespuestaTerapeuta.setText("Nombre está vacío");
			break;
		case 3:
			lblRespuestaTerapeuta.setText("Los apellidos están vacíos");
			break;
		case 4:
			lblRespuestaTerapeuta.setText("Ciudad está vacío");
			break;
		case 5:
			lblRespuestaTerapeuta.setText("Teléfono está vacío");
			break;
		case 6:
			lblRespuestaTerapeuta.setText("Los campos están vacíos");
			break;

		}
	}

	/**
	 * Informa al usuario el resultado de cualquier peticion realizada con Programa
	 * 
	 * @param categoriaPrograma
	 * 
	 * @param codigo
	 */
	protected void respuestaPrograma(String categoriaPrograma, int codigo) {

		if (categoriaPrograma.matches("programa")) {
			switch (codigo) {
			case 0:
				lblRespuestaPrograma.setText("Programa registrado correctamente.");
				break;
			case -1:
				lblRespuestaPrograma.setText("Ocurrió un error al registrar programa");
				break;
			case 3:
				lblRespuestaPrograma.setText("Nombre de programa no puede estar vacío");
				break;
			case 2:
				lblRespuestaPrograma.setText("Programa está vacío");
				break;
			}
		}

		if (categoriaPrograma.matches("unidad")) {
			switch (codigo) {
			case 0:
				lblRespuestaPrograma.setText("Nombre de la unidad no puede estar vacío");
				break;
			case 1:
				lblRespuestaTerapeuta.setText("Unidad asignada correctamente");
				break;
			}
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
