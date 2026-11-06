package hu.unideb.inf.konyvklub.controller;

import hu.unideb.inf.konyvklub.service.FelhasznaloService;
import hu.unideb.inf.konyvklub.service.dto.FelhasznaloDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/felhasznalo")
@RequiredArgsConstructor
public class FelhasznaloController {

    private final FelhasznaloService service;

    /** Firebase-es regisztráció után hívja a frontend: létrehozza a profilt a választott névvel. */
    @PostMapping("/profil")
    public FelhasznaloDto profilLetrehoz(@RequestAttribute("uid") String uid,
                                         @RequestAttribute("email") String email,
                                         @RequestBody Map<String, String> body)
            throws ExecutionException, InterruptedException {
        return service.regisztral(uid, email, body.get("felhasznalonev"));
    }

    @GetMapping("/en")
    public FelhasznaloDto en(@RequestAttribute("uid") String uid)
            throws ExecutionException, InterruptedException {
        return service.findByUid(uid);
    }

    @GetMapping("/kereses")
    public FelhasznaloDto kereses(@RequestParam String nev)
            throws ExecutionException, InterruptedException {
        return service.findByFelhasznalonev(nev.trim());
    }
}
