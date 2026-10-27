package hu.unideb.inf.konyvklub.service;

import hu.unideb.inf.konyvklub.service.dto.FelhasznaloDto;

import java.util.concurrent.ExecutionException;

public interface FelhasznaloService {
    FelhasznaloDto regisztral(String uid, String email, String felhasznalonev) throws ExecutionException, InterruptedException;
    FelhasznaloDto findByUid(String uid) throws ExecutionException, InterruptedException;
    FelhasznaloDto findByFelhasznalonev(String nev) throws ExecutionException, InterruptedException;
}
