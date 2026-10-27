package hu.unideb.inf.konyvklub.repository;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import hu.unideb.inf.konyvklub.service.dto.BaratKeresDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Repository
@RequiredArgsConstructor
public class BaratKeresRepository {

    private static final String KOLLEKCIO = "baratkeresek";
    private final Firestore db;

    public BaratKeresDto save(BaratKeresDto k) throws ExecutionException, InterruptedException {
        DocumentReference ref = k.getId() == null
                ? db.collection(KOLLEKCIO).document()
                : db.collection(KOLLEKCIO).document(k.getId());
        k.setId(ref.getId());
        ref.set(k).get();
        return k;
    }

    public Optional<BaratKeresDto> findById(String id) throws ExecutionException, InterruptedException {
        var doc = db.collection(KOLLEKCIO).document(id).get().get();
        return doc.exists() ? Optional.ofNullable(doc.toObject(BaratKeresDto.class)) : Optional.empty();
    }

    public List<BaratKeresDto> findByFeladoUid(String uid) throws ExecutionException, InterruptedException {
        return lista(db.collection(KOLLEKCIO).whereEqualTo("feladoUid", uid));
    }

    public List<BaratKeresDto> findByCimzettUid(String uid) throws ExecutionException, InterruptedException {
        return lista(db.collection(KOLLEKCIO).whereEqualTo("cimzettUid", uid));
    }

    private List<BaratKeresDto> lista(Query q) throws ExecutionException, InterruptedException {
        List<BaratKeresDto> l = new ArrayList<>();
        q.get().get().getDocuments().forEach(d -> l.add(d.toObject(BaratKeresDto.class)));
        return l;
    }
}
