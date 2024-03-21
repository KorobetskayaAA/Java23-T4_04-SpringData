package ru.teamscore.java23.springdata.storage.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.teamscore.java23.springdata.storage.entities.OperationDocument;
import ru.teamscore.java23.springdata.storage.exceptions.DocumentAlreadyExistsException;
import ru.teamscore.java23.springdata.storage.exceptions.NotEnoughStoredQuantityException;
import ru.teamscore.java23.springdata.storage.exceptions.NotFoundException;
import ru.teamscore.java23.springdata.storage.model.Income;
import ru.teamscore.java23.springdata.storage.model.Movement;
import ru.teamscore.java23.springdata.storage.model.Sale;
import ru.teamscore.java23.springdata.storage.model.Storage;
import ru.teamscore.java23.springdata.storage.service.StorageService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @GetMapping("storage/{code}")
    public Storage getStorage(@PathVariable("code") String code) {
        return storageService.getAllStoredItems(code);
    }

    @PostMapping("move")
    public OperationDocument postMove(@RequestBody Movement movement) {
        return storageService.processMovement(movement);
    }

    @PostMapping("sale")
    public OperationDocument postSale(@RequestBody Sale sale) {
        return storageService.processSale(sale);
    }

    @PostMapping("income")
    public OperationDocument postIncome(@RequestBody Income income) {
        return storageService.processIncome(income);
    }

    @ExceptionHandler
    public ResponseEntity<String> catchResourceNotFoundException(NotFoundException ex) {
        System.err.println(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> catchNotEnoughStoredQuantityException(NotEnoughStoredQuantityException ex) {
        System.err.println(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<String> catchDocumentAlreadyExistsException(DocumentAlreadyExistsException ex) {
        System.err.println(ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }
}
