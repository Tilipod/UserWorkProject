package ru.tilipod.services.sourceservices.imageservices;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.tilipod.services.sourceservices.exceptions.EmptySourceException;
import ru.tilipod.services.sourceservices.exceptions.UnsupportedExtensionException;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@Service
public class ImageUploaderService implements ImageService {

    @Value("${images.uploadPath}")
    private String uploadPath;

    @Override
    public URI uploadJpg(MultipartFile image) throws UnsupportedExtensionException,
            EmptySourceException, IOException {
        String originalFilename = image.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        if (!extension.equals(".jpg"))
            throw new UnsupportedExtensionException("Need to JPG-extension");

        if (image.isEmpty())
            throw new EmptySourceException("File can't empty");

        File file = new File(uploadPath + UUID.randomUUID().toString() + ".jpg");
        image.transferTo(file);
        return file.toURI();
    }
}
