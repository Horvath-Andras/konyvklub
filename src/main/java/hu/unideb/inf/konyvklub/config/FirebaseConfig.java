package hu.unideb.inf.konyvklub.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Configuration
public class FirebaseConfig {
    /**
     * Létrehozza és elérhetővé teszi a Firestore adatbázis-kapcsolatot.
     * Ha a kulcsot tartalmazó változó ki van töltve (éles szerver a render által), abból olvassa a kulcsot,
     * különben a megadott fájlból (serviceAccountKey.json).
     */
    @Bean
    public Firestore firestore(@Value("${firebase.service-account-json:}") String json,
                               @Value("${firebase.service-account-path}") String path)
            throws IOException {
        if (FirebaseApp.getApps().isEmpty()) {
            try (InputStream in = !json.isBlank()
                    ? new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))
                    : new FileInputStream(path)) {
                FirebaseApp.initializeApp(FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(in))
                        .build());
            }
        }
        return FirestoreClient.getFirestore();
    }
}
