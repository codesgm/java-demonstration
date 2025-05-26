package com.demonstration.demo.services;

import com.demonstration.demo.services.interfaces.FileCleanService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
public class FileCleanServiceImpl implements FileCleanService {

    @Override
    public String cleanTxtFile(MultipartFile file) throws IOException {
        validateFile(file);

        return processFile(file);
    }

    @Override
    public String generateCleanedFileName(String originalFileName) {
        if (originalFileName == null) {
            return "cleaned_file.txt";
        }

        String nameWithoutExtension = originalFileName.replace(".txt", "");
        return nameWithoutExtension + "_cleaned.txt";
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo não pode estar vazio");
        }

        String fileName = file.getOriginalFilename();
        if (fileName == null || !fileName.toLowerCase().endsWith(".txt")) {
            throw new IllegalArgumentException("Apenas arquivos .txt são aceitos");
        }

        if (file.getSize() > 10 * 1024 * 1024) {
            throw new IllegalArgumentException("Arquivo muito grande. Máximo 10MB");
        }
    }

    private String processFile(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            return reader.lines()
                    .filter(line -> !line.trim().isEmpty())
                    .collect(Collectors.joining("\n"));
        }
    }
}