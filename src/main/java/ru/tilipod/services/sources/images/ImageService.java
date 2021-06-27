package ru.tilipod.services.sources.images;

import org.springframework.web.multipart.MultipartFile;
import ru.tilipod.services.sources.exceptions.EmptySourceException;
import ru.tilipod.services.sources.exceptions.UnsupportedExtensionException;

import java.io.IOException;
import java.net.URI;

/**
 * Интерфейс сервиса для работы с изображениями. Поддерживает загрузку JPG-картинок
 * @author Tilipod
 */
public interface ImageService {
    /**
     * Сохраняет JPG-изображение на сервере
     * @param image Сохраняемое изображение
     * @return URI сохраненного изображения
     * @throws UnsupportedExtensionException Возникает, если файл не имеет расширения .jpg
     * @throws EmptySourceException Возникает, если пришел null или файл пустой
     * @throws IOException Возникает при ошибке сохранения файла на диске
     * @author Tilipod
     */
    URI uploadJpg(MultipartFile image) throws UnsupportedExtensionException,
            EmptySourceException, IOException;
}
