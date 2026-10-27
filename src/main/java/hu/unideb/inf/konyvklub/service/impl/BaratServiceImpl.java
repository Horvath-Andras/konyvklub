package hu.unideb.inf.konyvklub.service.impl;

import hu.unideb.inf.konyvklub.repository.BaratKeresRepository;
import hu.unideb.inf.konyvklub.service.BaratService;
import hu.unideb.inf.konyvklub.service.FelhasznaloService;
import hu.unideb.inf.konyvklub.service.dto.BaratKeresDto;
import hu.unideb.inf.konyvklub.service.dto.FelhasznaloDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class BaratServiceImpl implements BaratService {

    private static final String FUGGOBEN = "FUGGOBEN";
    private static final String ELFOGADVA = "ELFOGADVA";

    private final BaratKeresRepository repo;
    private final FelhasznaloService felhasznaloService;

    @Override
    public BaratKeresDto kerelmetKuld(String uid, String cimzettUid)
            throws ExecutionException, InterruptedException {
        if (uid.equals(cimzettUid)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Magadat nem jelölheted be");
        }
        felhasznaloService.findByUid(cimzettUid); // 404, ha nem létezik

        boolean marVan = repo.findByFeladoUid(uid).stream().anyMatch(k -> k.getCimzettUid().equals(cimzettUid))
                || repo.findByCimzettUid(uid).stream().anyMatch(k -> k.getFeladoUid().equals(cimzettUid));
        if (marVan) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Már van jelölés vagy barátság köztetek");
        }

        BaratKeresDto k = new BaratKeresDto();
        k.setFeladoUid(uid);
        k.setFeladoNev(felhasznaloService.findByUid(uid).getFelhasznalonev());
        k.setCimzettUid(cimzettUid);
        k.setStatusz(FUGGOBEN);
        return repo.save(k);
    }

    @Override
    public List<BaratKeresDto> beerkezoKerelmek(String uid) throws ExecutionException, InterruptedException {
        return repo.findByCimzettUid(uid).stream().filter(k -> FUGGOBEN.equals(k.getStatusz())).toList();
    }

    @Override
    public BaratKeresDto elfogad(String uid, String kerelemId) throws ExecutionException, InterruptedException {
        BaratKeresDto k = repo.findById(kerelemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nincs ilyen jelölés"));
        if (!k.getCimzettUid().equals(uid)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Ezt a jelölést nem te kaptad");
        }
        k.setStatusz(ELFOGADVA);
        return repo.save(k);
    }

    @Override
    public List<FelhasznaloDto> baratok(String uid) throws ExecutionException, InterruptedException {
        List<String> baratUidok = new ArrayList<>();
        repo.findByFeladoUid(uid).stream().filter(k -> ELFOGADVA.equals(k.getStatusz()))
                .forEach(k -> baratUidok.add(k.getCimzettUid()));
        repo.findByCimzettUid(uid).stream().filter(k -> ELFOGADVA.equals(k.getStatusz()))
                .forEach(k -> baratUidok.add(k.getFeladoUid()));

        List<FelhasznaloDto> baratok = new ArrayList<>();
        for (String id : baratUidok) {
            baratok.add(felhasznaloService.findByUid(id));
        }
        return baratok;
    }
}
