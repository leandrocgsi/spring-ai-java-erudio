package br.com.erudio.service;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class TranscriptionService {

    private final OpenAiAudioTranscriptionModel transcriptionModel;

    public TranscriptionService(@Value("${spring.ai.openai.api-key}") String apiKey) {
        OpenAiAudioApi openAiAudioApi = OpenAiAudioApi.builder()
                .apiKey(apiKey)
                .build();
        this.transcriptionModel = new OpenAiAudioTranscriptionModel(openAiAudioApi);
    }

    public String transcribeAudio(MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("audio", ".mp3");
        file.transferTo(tempFile);

        OpenAiAudioTranscriptionOptions options =
                OpenAiAudioTranscriptionOptions.builder()
                    .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.TEXT)
                    .language("pt")
                    .temperature(0f)
                    .build();

        FileSystemResource audioFile = new FileSystemResource(tempFile);
        AudioTranscriptionPrompt transcriptionRequest = new AudioTranscriptionPrompt(audioFile, options);
        AudioTranscriptionResponse response = transcriptionModel.call(transcriptionRequest);

        tempFile.delete();

        return response.getResult().getOutput();
    }
}
