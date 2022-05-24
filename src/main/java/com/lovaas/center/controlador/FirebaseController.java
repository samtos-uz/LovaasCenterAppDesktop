package com.lovaas.center.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.lovaas.center.modelo.FirebaseInitializer;
import com.lovaas.center.modelo.entidad.Programa;
import com.lovaas.center.modelo.entidad.Terapeuta;
import com.lovaas.center.modelo.entidad.User;

@RestController
public class FirebaseController {

	@Autowired
	private FirebaseInitializer db;

	@GetMapping("/getAllUsers")
	public List<User> getAllUsers() throws InterruptedException, ExecutionException {
		List<User> userList = new ArrayList<User>();
		CollectionReference users = db.getFirebase().collection("users");
		ApiFuture<QuerySnapshot> querySnapshot = users.get();
		for (DocumentSnapshot doc : querySnapshot.get().getDocuments()) {
			userList.add(doc.toObject(User.class));
		}
		return userList;
	}

	/**
	 * Metodo que me devuelve una lista de Objetos de una coleccion
	 * 
	 * @param nombreColeccion el nombre de la coleccion a buscar
	 * @return lista {@link List} de los objetos
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	@SuppressWarnings("unchecked")
	public CollectionReference getCollection(String nombreColeccion) throws InterruptedException, ExecutionException {

		CollectionReference collection = db.getFirebase().collection(nombreColeccion);

		/*
		 * Comprobamos si firebase devolvio una coleccion sino la devolvió devolvemos
		 * null
		 */
		if (collection == null) {
			return null;
		} else {
			return collection;
		}
	}

	public User getUser(@RequestParam("email") String email) {
		return new User();
	}

	/**
	 * Método que comprueba si existe el usuario en BD y comprueba su contraseña
	 * 
	 * @param usuario
	 * @param password
	 * @return <code>true</code> en caso de que las credenciales sean correctas,
	 *         <code>false</code> en caso contrario
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public boolean iniciarSesion(String usuario, String password) throws InterruptedException, ExecutionException {

		boolean response = false;

		DocumentReference doReference = db.getFirebase().collection("users").document(usuario);

		ApiFuture<DocumentSnapshot> apFuture = doReference.get();

		DocumentSnapshot document = apFuture.get();

		User user = null;

		if (document.exists()) {
			user = document.toObject(User.class);
			response = comprobarPassword(password, user.getPassword());
			System.out.println(user.toString());
			return response;
		}

		return response;
	}

	/**
	 * Método para comparar la contraseña introducida por el usuario, con la de bd.
	 * 
	 * @param passwordIntroducida
	 * @param passwordBD
	 * @return <code>true</code> si coinciden, <code>false</code> en caso contrario.
	 */
	private boolean comprobarPassword(String passwordIntroducida, String passwordBD) {
		if (passwordIntroducida.matches(passwordBD)) {
			return true;
		}
		return false;
	}

	/**
	 * Método que busca la colección y la devuelve en forma de tabla
	 * 
	 * @param nombreTabla
	 * @return un {@link TableModel} con la información de la colección
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public TableModel getTabla(String nombreTabla) throws InterruptedException, ExecutionException {
		int numColumnas = 0;
		int numFilas = 0;

		// traerme toda la coleccion
		CollectionReference collection = getCollection(nombreTabla);
		ApiFuture<QuerySnapshot> querySnapshot = collection.get();
		Map<String, Object> campos = null;

		// averiguar el numero de campos
		for (DocumentSnapshot doc : querySnapshot.get().getDocuments()) {
			campos = doc.getData();
			numFilas++;
		}

		numColumnas = campos.size();
		String[] cabecera = new String[numColumnas];
		Object[][] contenido = new Object[numFilas][numColumnas];

		// llenar el contenido de la tabla
		int fila = 0;
		while (fila < numFilas) {
			for (DocumentSnapshot doc : querySnapshot.get().getDocuments()) {
				int col = 0;
				campos = doc.getData();
				for (Object object : campos.values()) {
					contenido[fila][col] = object;
					col++;
				}
				fila++;
			}
		}
		// Sacar los campos de la cabecera
		int i = 0;
		for (String string : campos.keySet()) {
			cabecera[i] = string;
			i++;
		}

		// Definir el modelo de la tabla
		TableModel model = new DefaultTableModel(contenido, cabecera);
		return model;
	}

	/**
	 * Metodo para dar de alta a un terapeuta en la coleccion de terapeutas de
	 * Firebase
	 * 
	 * @param terapeuta
	 * @return <code>true</code> si se pudo dar de alta correctamente,
	 *         <code>false</code> en caso contrario
	 */
	public boolean altaTerapeuta(Terapeuta terapeuta) {
		boolean alta = false;
		String idDoc = terapeuta.getNombre() + terapeuta.getApellidos();
		//Peticion a bd (alta)
		ApiFuture<WriteResult> addedDocRef = db.getFirebase().collection("terapeutas").document(idDoc).set(terapeuta);
		
		try {
			System.out.println("update time: " + addedDocRef.get().getUpdateTime());
			alta = true;
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return alta;
	}

	/**
	 * Metodo para dar de alta a un programa en la coleccion de programas de
	 * Firebase
	 * 
	 * @param programa
	 * @return <code>true</code> si se pudo dar de alta correctamente,
	 *         <code>false</code> en caso contrario
	 */
	public boolean altaPrograma(Programa programa) {
		boolean alta = false;
		ApiFuture<DocumentReference> addedDocRef = db.getFirebase().collection("programas").add(programa);
		// future.get() blocks on response
		try {
			String id = addedDocRef.get().getId();
			System.out.println("Added document with ID: " + id);
			programa.setId(id);// asigno id para futura actualizacion
			System.out.println(programa.toString());
			if (!id.isEmpty())
				alta = true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return alta;
	}

	/**
	 * Método para actualizar un terapeuta, si tiene un id asignado significa que ya
	 * está dado de alta, por tanto se puede actualizar. Si no se dará de alta
	 * automáticamente
	 * 
	 * @param terapeuta
	 * @return <code>true</code> en caso de se haya actualizado o añadido
	 *         correctamente, <code>false</code> en caso contrario
	 * @throws InterruptedException
	 * @throws ExecutionException
	 */
	public boolean actualizarTerapeuta(Terapeuta terapeuta) throws InterruptedException, ExecutionException {
		boolean update = false;

		if (terapeuta.getId() != null) {
			// Update an existing document
			DocumentReference docRef = db.getFirebase().collection("terapeutas").document(terapeuta.getId());

			// (async) Update one field
			Map<String, Object> camposTerapeuta = new HashMap<String, Object>();
			camposTerapeuta.put("nombre", terapeuta.getNombre());
			camposTerapeuta.put("apellidos", terapeuta.getApellidos());
			camposTerapeuta.put("ciudad", terapeuta.getCiudad());
			camposTerapeuta.put("telefono", terapeuta.getTelefono());
			ApiFuture<WriteResult> future1 = docRef.update(camposTerapeuta);

			WriteResult result = future1.get();
			System.out.println("Write result: " + result);
			update = true;
		} else {
			update = altaTerapeuta(terapeuta);
		}

		return update;
	}

	public boolean actualizarPrograma(Programa programa) throws InterruptedException, ExecutionException {
		boolean update = false;

		if (programa.getId() != null) {
			// Update an existing document
			DocumentReference docRef = db.getFirebase().collection("programas").document(programa.getId());

			// (async) Update one field
			Map<String, Object> camposPrograma = new HashMap<String, Object>();
			camposPrograma.put("nombre", programa.getNombre());
			camposPrograma.put("fechaRealizacion", programa.getFechaRealizacion());
			camposPrograma.put("porcentajeRealizado", programa.getPorcentajeRealizado());
			ApiFuture<WriteResult> future1 = docRef.update(camposPrograma);

			WriteResult result = future1.get();
			System.out.println("Write result: " + result);
			update = true;
		} else {
			update = altaPrograma(programa);
		}

		return update;
	}

	/**
	 * Metodo que busca en BD si existe dicha entidad, si existe le asigna su ida
	 * para una futura actualización
	 * 
	 * @param terapeutaActual
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public void obtenerIdTerapeuta(Terapeuta terapeutaActual) throws InterruptedException, ExecutionException {
		// Traer documentos con coincidencia de nombre
		ApiFuture<QuerySnapshot> future = db.getFirebase().collection("terapeutas")
				.whereEqualTo("nombre", terapeutaActual.getNombre()).get();
		// future.get() blocks on response
		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		for (DocumentSnapshot document : documents) {
			System.out.println(document.getId() + " => " + document.toObject(Terapeuta.class));
			Terapeuta terapeutaBD = document.toObject(Terapeuta.class);
			// Comprobacion de apellidos para confirmar la igualdad de los objetos ( Nombre,
			// Apellidos = Nombre, Apellidos)
			if (terapeutaBD.getApellidos().matches(terapeutaActual.getApellidos())) {
				terapeutaActual.setId(document.getId());
			}
		}
	}

	public void obtenerIdPrograma(Programa programaActual) throws InterruptedException, ExecutionException {
		// Traer documentos con coincidencia de nombre
		ApiFuture<QuerySnapshot> future = db.getFirebase().collection("programas")
				.whereEqualTo("nombre", programaActual.getNombre()).get();
		// future.get() blocks on response
		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		for (DocumentSnapshot document : documents) {
			System.out.println(document.getId() + " => " + document.toObject(Terapeuta.class));
			Programa programaBD = document.toObject(Programa.class);
			// Comprobacion de apellidos para confirmar la igualdad de los objetos
			if (programaBD.getFechaRealizacion().matches(programaActual.getFechaRealizacion())) {
				programaActual.setId(document.getId());
			}
		}
	}

	/**
	 * Elimina un terapeuta de la coleccion en Firebase
	 * 
	 * @param terapeutaActual
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public void eliminarTerapeuta(Terapeuta terapeutaActual) throws InterruptedException, ExecutionException {
		// asynchronously delete a document
		ApiFuture<WriteResult> writeResult = db.getFirebase().collection("terapeutas").document(terapeutaActual.getId())
				.delete();
		// ...
		System.out.println("Update time : " + writeResult.get().getUpdateTime());

	}

	/**
	 * Elmina un programa de la coleccion en Firebase
	 * 
	 * @param programaActual
	 * @throws ExecutionException
	 * @throws InterruptedException
	 */
	public void eliminarPrograma(Programa programaActual) throws InterruptedException, ExecutionException {
		// asynchronously delete a document
		ApiFuture<WriteResult> writeResult = db.getFirebase().collection("programas").document(programaActual.getId())
				.delete();
		// ...
		System.out.println("Update time : " + writeResult.get().getUpdateTime());
	}

}
