package ru.tilipod.services.sources.exceptions;

/**
 * Исключение "неподдерживаемый формат". Возникает, если ресурс имеет неправильный формат
 * @author Tilipod
 */
public class UnsupportedExtensionException extends SourceException {
    public UnsupportedExtensionException(String message) {
        super(message);
    }
}
