package ru.tilipod.services.sources.exceptions;

/**
 * Исключение "пустой ресурс". Возникает, если ожидаемый ресурс оказался пустым
 * @author Tilipod
 */
public class EmptySourceException extends SourceException {
    public EmptySourceException(String message) {
        super(message);
    }
}
