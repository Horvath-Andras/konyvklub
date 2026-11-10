package hu.unideb.inf.konyvklub.repository;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import hu.unideb.inf.konyvklub.service.dto.KonyvDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
@RequiredArgsConstructor
public class KonyvRepository {

    private static final String kollekcio = "konyvek";
    private final Firestore db;

    public KonyvDto save(KonyvDto k) throws ExecutionException, InterruptedException {
        DocumentReference ref = k.getId() == null
                ? db.collection(kollekcio).document()
                : db.collection(kollekcio).document(k.getId());
        k.setId(ref.getId());
        ref.set(k).get();
        return k;
    }

    public void deleteById(String id) throws ExecutionException, InterruptedException {
        db.collection(kollekcio).document(id).delete().get();
    }

    public Optional<KonyvDto> findById(String id) throws ExecutionException, InterruptedException {
        var doc = db.collection(kollekcio).document(id).get().get();
        return doc.exists() ? Optional.ofNullable(doc.toObject(KonyvDto.class)) : Optional.empty();
    }

    public List<KonyvDto> findByTulajdonosUidIn(List<String> uidok)
            throws ExecutionException, InterruptedException {
        List<KonyvDto> eredmeny = new ArrayList<>();
        for (int i = 0; i < uidok.size(); i += 10) {
            List<String> csomag = uidok.subList(i, Math.min(i + 10, uidok.size()));
            db.collection(kollekcio).whereIn("tulajdonosUid", csomag).get().get()
                    .getDocuments()
                    .forEach(d -> eredmeny.add(d.toObject(KonyvDto.class)));
        }
        return eredmeny;
    }
}
