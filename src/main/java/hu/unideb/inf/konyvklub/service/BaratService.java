package hu.unideb.inf.konyvklub.service;

import hu.unideb.inf.konyvklub.service.dto.BaratKeresDto;
import hu.unideb.inf.konyvklub.service.dto.FelhasznaloDto;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface BaratService {
    BaratKeresDto kerelmetKuld(String uid, String cimzettUid) throws ExecutionException, InterruptedException;
    List<BaratKeresDto> beerkezoKerelmek(String uid) throws ExecutionException, InterruptedException;
    BaratKeresDto elfogad(String uid, String kerelemId) throws ExecutionException, InterruptedException;
    List<FelhasznaloDto> baratok(String uid) throws ExecutionException, InterruptedException;
}
