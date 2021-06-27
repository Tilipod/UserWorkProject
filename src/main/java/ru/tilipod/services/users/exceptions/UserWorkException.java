package ru.tilipod.services.users.exceptions;

/**
 * Класс исключений при работе с пользователями
 * @author Tilipod
 */
public class UserWorkException extends Exception {
    public UserWorkException(String message) {
        super(message);
    }
}