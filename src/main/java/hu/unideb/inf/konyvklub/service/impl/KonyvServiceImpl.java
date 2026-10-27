package hu.unideb.inf.konyvklub.service.impl;

import hu.unideb.inf.konyvklub.repository.KonyvRepository;
import hu.unideb.inf.konyvklub.service.BaratService;
import hu.unideb.inf.konyvklub.service.FelhasznaloService;
import hu.unideb.inf.konyvklub.service.KonyvService;
import hu.unideb.inf.konyvklub.service.dto.FelhasznaloDto;
import hu.unideb.inf.konyvklub.service.dto.KonyvDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class KonyvServiceImpl implements KonyvService {

    private final KonyvRepository repo;
    private final BaratService baratService;
    private final FelhasznaloService felhasznaloService;

    @Override
    public List<KonyvDto> findLathatoKonyvek(String uid) throws ExecutionException, InterruptedException {
        Map<String, String> nevek = new HashMap<>();
        nevek.put(uid, felhasznaloService.findByUid(uid).getFelhasznalonev());
        for (FelhasznaloDto barat : baratService.baratok(uid)) {
            nevek.put(barat.getUid(), barat.getFelhasznalonev());
        }

        List<KonyvDto> konyvek = repo.findByTulajdonosUidIn(new ArrayList<>(nevek.keySet()));
        konyvek.forEach(k -> k.setTulajdonosNev(nevek.get(k.getTulajdonosUid())));
        return konyvek;
    }

    @Override
    public KonyvDto letrehoz(String uid, KonyvDto dto) throws ExecutionException, InterruptedException {
        ellenoriz(dto);
        dto.setId(null);
        dto.setTulajdonosUid(uid); // a tulajdonos mindig a bejelentkezett felhasználó
        dto.setTulajdonosNev(felhasznaloService.findByUid(uid).getFelhasznalonev());
        return repo.save(dto);
    }

    @Override
    public KonyvDto modosit(String uid, String id, KonyvDto dto) throws ExecutionException, InterruptedException {
        KonyvDto meglevo = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Nincs ilyen könyv"));
        if (!meglevo.getTulajdonosUid().equals(uid)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Csak a saját könyvedet módosíthatod");
        }
        ellenoriz(dto);
        meglevo.setCim(dto.getCim());
        meglevo.setIro(dto.getIro());
        meglevo.setKonyvInfo(dto.getKonyvInfo());
        meglevo.setTartalomInfo(dto.getTartalomInfo());
        return repo.save(meglevo);
    }

    private void ellenoriz(KonyvDto dto) {
        if (dto.getCim() == null || dto.getCim().isBlank() || dto.getIro() == null || dto.getIro().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A cím és az író kötelező");
        }
    }
}
