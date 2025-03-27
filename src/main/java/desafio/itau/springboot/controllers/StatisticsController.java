package desafio.itau.springboot.controllers;

import desafio.itau.springboot.dto.StatisticsDTO;
import desafio.itau.springboot.services.StatisticService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/estatistica", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticsController {

    private final StatisticService service;

    public StatisticsController(StatisticService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<?> lastMinuteTransactions() {
       StatisticsDTO statistics = service.getLastMinuteMetrics();

        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/personalizado")
    public ResponseEntity<?> getLastTransactions(@RequestParam(name = "segundos", required = false, defaultValue = "60") int segundos) {
        if (segundos <= 0) return ResponseEntity.badRequest().body("O parâmetro 'seconds' deve ser maior que zero");

        StatisticsDTO customStatistics = service.getCustomMinuteMetrics(segundos);

        return ResponseEntity.ok(customStatistics);
    }
}
