package ru.teamscore.java23.springdata.storage.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String objectId, String objectType) {
        super(String.format("%s [%s] не найден", objectType, objectId));
    }
}
