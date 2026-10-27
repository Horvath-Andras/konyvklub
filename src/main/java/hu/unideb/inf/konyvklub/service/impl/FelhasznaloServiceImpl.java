package hu.unideb.inf.konyvklub.service.impl;

import hu.unideb.inf.konyvklub.repository.FelhasznaloRepository;
import hu.unideb.inf.konyvklub.service.FelhasznaloService;
import hu.unideb.inf.konyvklub.service.dto.FelhasznaloDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class FelhasznaloServiceImpl implements FelhasznaloService {

    private final FelhasznaloRepository repo;

    @Override
    public FelhasznaloDto regisztral(String uid, String email, String felhasznalonev)
            throws ExecutionException, InterruptedException {
        if (felhasznalonev == null || felhasznalonev.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A felhasználónév kötelező");
        }
        String nev = felhasznalonev.trim();
        if (repo.findByFelhasznalonev(nev).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ez a felhasználónév már foglalt");
        }
        FelhasznaloDto f = new FelhasznaloDto();
        f.setUid(uid);
        f.setEmail(email);
        f.setFelhasznalonev(nev);
        return repo.save(f);
    }

    @Override
    public FelhasznaloDto findByUid(String uid) throws ExecutionException, InterruptedException {
        return repo.findByUid(uid)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nincs ilyen felhasználó"));
    }

    @Override
    public FelhasznaloDto findByFelhasznalonev(String nev) throws ExecutionException, InterruptedException {
        return repo.findByFelhasznalonev(nev)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nincs ilyen felhasználó"));
    }
}
