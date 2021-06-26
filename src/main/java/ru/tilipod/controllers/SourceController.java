package ru.tilipod.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.tilipod.services.sourceservices.exceptions.EmptySourceException;
import ru.tilipod.services.sourceservices.exceptions.UnsupportedExtensionException;
import ru.tilipod.services.sourceservices.imageservices.ImageService;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/source")
public class SourceController {

    private final Logger logger = LoggerFactory.getLogger(SourceController.class);
    private final ImageService imageService;

    @Autowired
    public SourceController(ImageService imageService) {
        this.imageService = imageService;
    }

    /*@GetMapping("/new/image")
    public String jpgImage() {
        return "newImage";
    }*/

    @PostMapping(value = "/new/image",
                 consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    public Callable<ResponseEntity> newJpgImage (@RequestPart("file") MultipartFile image) {
        return () -> {
            try {
                URI uriImage = imageService.uploadJpg(image);
                return new ResponseEntity<>(uriImage.getPath(), HttpStatus.CREATED);
            } catch (UnsupportedExtensionException err) {
                logger.error(err.getMessage());
                return new ResponseEntity<>(err.getMessage(), HttpStatus.RESET_CONTENT);
            } catch (EmptySourceException err) {
                logger.error(err.getMessage());
                return new ResponseEntity<>(err.getMessage(), HttpStatus.NO_CONTENT);
            } catch (IOException err) {
                logger.error(err.getMessage());
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };
    }

}
