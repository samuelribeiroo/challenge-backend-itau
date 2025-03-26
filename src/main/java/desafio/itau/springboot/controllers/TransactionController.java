package desafio.itau.springboot.controllers;

import desafio.itau.springboot.dto.TransactionDTO;
import desafio.itau.springboot.interfaces.DeletionStatus;
import desafio.itau.springboot.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

@RestController
@RequestMapping(value =  "/transacao", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {
    private TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> addNewTransaction(@RequestBody @Valid TransactionDTO transaction, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();


        service.createNewTransaction(transaction);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllRecentTransactions() {
        return service.deleteRecentTransactions().map(status -> {
               return status == DeletionStatus.SUCCESS
                       ? ResponseEntity.status(HttpStatus.OK).build()
                       : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }).orElseGet(() -> new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR));
}

}
