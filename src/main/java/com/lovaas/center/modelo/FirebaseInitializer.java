package com.lovaas.center.modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import java.util.*;

import javax.annotation.PostConstruct;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

@Service
public class FirebaseInitializer {

	private Firestore bd;

	public FirebaseInitializer() {
	}

	@PostConstruct
	public void iniciarDB() throws IOException {

		ClassLoader classLoader = FirebaseInitializer.class.getClassLoader();

		File file = new File(
				Objects.requireNonNull(classLoader.getResource("fundacionlovaas_firebase.json")).getFile());

		FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());

		FirebaseOptions options = new FirebaseOptions.Builder()
				.setCredentials(GoogleCredentials.fromStream(serviceAccount))
				.setDatabaseUrl("https//:fundacionlovaas.firebase.app/").build();

		if (FirebaseApp.getApps().isEmpty()) {
			FirebaseApp.initializeApp(options);
			System.out.println("Succesfull");
		}


	}

	public Firestore getFirebase() {
		return FirestoreClient.getFirestore();
	}

}
