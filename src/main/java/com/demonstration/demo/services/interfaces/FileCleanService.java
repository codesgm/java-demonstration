package com.demonstration.demo.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileCleanService {

    String cleanTxtFile(MultipartFile file) throws IOException;

    String generateCleanedFileName(String originalFileName);
}
