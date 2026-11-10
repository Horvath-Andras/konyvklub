package hu.unideb.inf.konyvklub.service;

import hu.unideb.inf.konyvklub.service.dto.KonyvDto;

import java.util.List;
import java.util.concurrent.ExecutionException;


public interface KonyvService {

    List<KonyvDto> findLathatoKonyvek(String uid) throws ExecutionException, InterruptedException;

    KonyvDto letrehoz(String uid, KonyvDto dto) throws ExecutionException, InterruptedException;

    KonyvDto modosit(String uid, String id, KonyvDto dto) throws ExecutionException, InterruptedException;

    void torol(String uid, String id) throws ExecutionException, InterruptedException;
}
