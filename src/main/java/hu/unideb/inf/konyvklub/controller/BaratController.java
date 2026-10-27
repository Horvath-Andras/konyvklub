package hu.unideb.inf.konyvklub.controller;

import hu.unideb.inf.konyvklub.service.BaratService;
import hu.unideb.inf.konyvklub.service.dto.BaratKeresDto;
import hu.unideb.inf.konyvklub.service.dto.FelhasznaloDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/baratok")
@RequiredArgsConstructor
public class BaratController {

    private final BaratService service;

    @GetMapping
    public List<FelhasznaloDto> baratok(@RequestAttribute("uid") String uid)
            throws ExecutionException, InterruptedException {
        return service.baratok(uid);
    }

    @PostMapping("/jeloles/{cimzettUid}")
    public BaratKeresDto jelol(@RequestAttribute("uid") String uid, @PathVariable String cimzettUid)
            throws ExecutionException, InterruptedException {
        return service.kerelmetKuld(uid, cimzettUid);
    }

    @GetMapping("/kerelmek")
    public List<BaratKeresDto> kerelmek(@RequestAttribute("uid") String uid)
            throws ExecutionException, InterruptedException {
        return service.beerkezoKerelmek(uid);
    }

    @PostMapping("/kerelmek/{id}/elfogad")
    public BaratKeresDto elfogad(@RequestAttribute("uid") String uid, @PathVariable String id)
            throws ExecutionException, InterruptedException {
        return service.elfogad(uid, id);
    }
}
