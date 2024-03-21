package ru.teamscore.java23.springdata.users.exception;

public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException(String username) {
        super(String.format("Пользователь с именем %s уже существует", username));
    }
}
