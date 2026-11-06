package hu.unideb.inf.konyvklub.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** Token nélkül hívható; a frontend ezzel "ébreszti fel" az elalvó szervert. */
@RestController
public class HealthController {

    @GetMapping("/health")
    public String health() {
        return "ok";
    }
}
