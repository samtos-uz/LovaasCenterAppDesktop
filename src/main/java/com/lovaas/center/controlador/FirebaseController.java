package com.lovaas.center.controlador;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.lovaas.center.modelo.FirebaseInitializer;
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
}
