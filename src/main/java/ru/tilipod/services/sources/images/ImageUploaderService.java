package ru.tilipod.services.sources.images;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.tilipod.services.sources.exceptions.EmptySourceException;
import ru.tilipod.services.sources.exceptions.UnsupportedExtensionException;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@Service
public class ImageUploaderService implements ImageService {

    /** Путь к каталогу, где сохраняются изображения */
    @Value("${images.uploadPath}")
    private String uploadPath;

    /**
     * @author Tilipod
     * @see ImageService#uploadJpg
     */
    @Override
    public URI uploadJpg(MultipartFile image) throws UnsupportedExtensionException,
            EmptySourceException, IOException {
        String originalFilename = image.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        // Если расширение не .jpg, ошибка
        if (!extension.equals(".jpg"))
            throw new UnsupportedExtensionException("Need to JPG-extension");

        // Если файл пуст, сохранять нечего
        if (image.isEmpty())
            throw new EmptySourceException("File can't empty");

        // Название файла генерируем случайно через UUID
        File file = new File(uploadPath + UUID.randomUUID().toString() + ".jpg");
        image.transferTo(file);
        return file.toURI();
    }
}
