package ru.tilipod.services.sourceservices.imageservices;

import org.springframework.web.multipart.MultipartFile;
import ru.tilipod.services.sourceservices.exceptions.EmptySourceException;
import ru.tilipod.services.sourceservices.exceptions.UnsupportedExtensionException;

import java.io.IOException;
import java.net.URI;

public interface ImageService {
    URI uploadJpg(MultipartFile image) throws UnsupportedExtensionException,
            EmptySourceException, IOException;
}
