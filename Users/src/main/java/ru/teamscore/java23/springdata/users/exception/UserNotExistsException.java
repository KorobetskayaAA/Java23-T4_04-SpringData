package ru.teamscore.java23.springdata.users.exception;

import lombok.NonNull;

public class UserNotExistsException extends RuntimeException {
    public UserNotExistsException(@NonNull String username) {
        super(String.format("Пользователь с именем %s не найден", username));
    }
}
