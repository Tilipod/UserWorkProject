package ru.tilipod.services.users.exceptions;

/**
 * Исключение "пользователь не найден"
 * @author Tilipod
 */
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}