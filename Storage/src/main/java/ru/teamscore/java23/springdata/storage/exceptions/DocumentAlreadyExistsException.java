package ru.teamscore.java23.springdata.storage.exceptions;

public class DocumentAlreadyExistsException extends RuntimeException {
    private String documentNumber;

    public DocumentAlreadyExistsException(String documentNumber) {
        super(String.format("Документ с номером %s уже существует", documentNumber));
        this.documentNumber = documentNumber;
    }
}
