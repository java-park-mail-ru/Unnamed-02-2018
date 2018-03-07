package ru.shipcollision.api.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileIOService {

    boolean fileExists(String resoursePath);

    String saveFileAndGetResourcePath(MultipartFile file);

    void deleteFile(String resoursePath);
}