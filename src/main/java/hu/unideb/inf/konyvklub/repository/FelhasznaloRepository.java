package hu.unideb.inf.konyvklub.repository;

import com.google.cloud.firestore.FieldPath;
import com.google.cloud.firestore.Firestore;
import hu.unideb.inf.konyvklub.service.dto.FelhasznaloDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
@RequiredArgsConstructor
public class FelhasznaloRepository {

    private static final String KOLLEKCIO = "felhasznalok";
    private final Firestore db;

    public FelhasznaloDto save(FelhasznaloDto f) throws ExecutionException, InterruptedException {
        db.collection(KOLLEKCIO).document(f.getUid()).set(f).get();
        return f;
    }

    public Optional<FelhasznaloDto> findByUid(String uid) throws ExecutionException, InterruptedException {
        var doc = db.collection(KOLLEKCIO).document(uid).get().get();
        return doc.exists() ? Optional.ofNullable(doc.toObject(FelhasznaloDto.class)) : Optional.empty();
    }

    /** Több felhasználó egyszerre (10-es csomagokban), nem egyenként. */
    public List<FelhasznaloDto> findByUidIn(List<String> uidok) throws ExecutionException, InterruptedException {
        List<FelhasznaloDto> eredmeny = new ArrayList<>();
        for (int i = 0; i < uidok.size(); i += 10) {
            List<String> csomag = uidok.subList(i, Math.min(i + 10, uidok.size()));
            db.collection(KOLLEKCIO).whereIn(FieldPath.documentId(), csomag).get().get()
                    .getDocuments()
                    .forEach(d -> eredmeny.add(d.toObject(FelhasznaloDto.class)));
        }
        return eredmeny;
    }

    public Optional<FelhasznaloDto> findByFelhasznalonev(String nev) throws ExecutionException, InterruptedException {
        var docs = db.collection(KOLLEKCIO).whereEqualTo("felhasznalonev", nev).limit(1).get().get();
        return docs.isEmpty() ? Optional.empty()
                : Optional.ofNullable(docs.getDocuments().get(0).toObject(FelhasznaloDto.class));
    }
}
