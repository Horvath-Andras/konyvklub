package hu.unideb.inf.konyvklub.service;

import hu.unideb.inf.konyvklub.service.dto.KonyvDto;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface KonyvService {
    /** A saját és a barátok könyvei. */
    List<KonyvDto> findLathatoKonyvek(String uid) throws ExecutionException, InterruptedException;
    KonyvDto letrehoz(String uid, KonyvDto dto) throws ExecutionException, InterruptedException;
    /** Csak a saját könyv módosítható, különben 403. */
    KonyvDto modosit(String uid, String id, KonyvDto dto) throws ExecutionException, InterruptedException;
}
