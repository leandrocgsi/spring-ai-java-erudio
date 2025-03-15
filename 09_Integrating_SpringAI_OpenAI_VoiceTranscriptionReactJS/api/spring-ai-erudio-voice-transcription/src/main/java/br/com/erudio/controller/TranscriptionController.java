package br.com.erudio.controller;

import br.com.erudio.service.TranscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class TranscriptionController {

    private final TranscriptionService service;

    public TranscriptionController(TranscriptionService service) {
        this.service = service;
    }

    @PostMapping("/ai/transcribe")
    public ResponseEntity<String> transcribeAudio(@RequestParam("file") MultipartFile file) {
        try {
            String transcription = service.transcribeAudio(file);
            return ResponseEntity.ok(transcription);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error processing the audio fil: " + e.getMessage());
        }
    }
}
