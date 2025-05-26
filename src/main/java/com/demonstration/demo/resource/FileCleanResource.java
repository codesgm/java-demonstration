package com.demonstration.demo.resource;

import com.demonstration.demo.services.interfaces.FileCleanService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/file")
@RequiredArgsConstructor
public class FileCleanResource {

    private final FileCleanService fileCleanService;

    @PostMapping("/clean-txt")
    public ResponseEntity<?> cleanTxtFile(@RequestParam("file") MultipartFile file) throws IOException {
        String cleanedContent = fileCleanService.cleanTxtFile(file);
        String cleanedFileName = fileCleanService.generateCleanedFileName(file.getOriginalFilename());

        byte[] contentBytes = cleanedContent.getBytes(StandardCharsets.UTF_8);
        ByteArrayResource resource = new ByteArrayResource(contentBytes);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + cleanedFileName + "\"")
                .contentType(MediaType.TEXT_PLAIN)
                .contentLength(contentBytes.length)
                .body(resource);
    }
}
