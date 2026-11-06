package hu.unideb.inf.konyvklub.config;

import com.google.cloud.firestore.Firestore;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Az első Firestore-hívás lassú (kapcsolatfelépítés), ezért indításkor egy
 * apró lekérdezéssel előre felépítjük, hogy az első bejelentkező ne ezt várja meg.
 */
@Component
@RequiredArgsConstructor
public class FirestoreWarmup {

    private final Firestore db;

    @EventListener(ApplicationReadyEvent.class)
    public void melegit() {
        try {
            db.collection("felhasznalok").limit(1).get(); // nem várunk rá
        } catch (Exception ignored) {
            // a bemelegítés nem kritikus
        }
    }
}
