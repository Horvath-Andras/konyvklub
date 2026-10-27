package hu.unideb.inf.konyvklub.controller;

import hu.unideb.inf.konyvklub.service.KonyvService;
import hu.unideb.inf.konyvklub.service.dto.KonyvDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("api/konyvek")
@RequiredArgsConstructor
public class KonyvController {

    private final KonyvService service;

    @GetMapping
    public List<KonyvDto> findAll(@RequestAttribute("uid") String uid)
            throws ExecutionException, InterruptedException {
        return service.findLathatoKonyvek(uid);
    }

    @PostMapping
    public KonyvDto create(@RequestAttribute("uid") String uid, @RequestBody KonyvDto dto)
            throws ExecutionException, InterruptedException {
        return service.letrehoz(uid, dto);
    }

    @PutMapping("/{id}")
    public KonyvDto update(@RequestAttribute("uid") String uid, @PathVariable String id,
                           @RequestBody KonyvDto dto)
            throws ExecutionException, InterruptedException {
        return service.modosit(uid, id, dto);
    }
}
