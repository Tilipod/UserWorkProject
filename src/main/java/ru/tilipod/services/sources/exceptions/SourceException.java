package ru.tilipod.services.sources.exceptions;

/**
 * Класс исключений ресурсов сервера. Возникает при сбое в работе с ресурсами сервера
 * @author Tilipod
 */
public class SourceException extends Exception {
    public SourceException(String message) {
        super(message);
    }
}
