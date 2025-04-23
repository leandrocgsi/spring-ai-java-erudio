package br.com.erudio.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatModel chatModel;

    public ChatService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String getResponse(String prompt) {
        return chatModel.call(prompt);
    }

    public String getResponseWithOptions(String prompt) {
        ChatResponse response = chatModel.call(
            new Prompt(
                prompt,
                ChatOptions.builder()
                    //.model("deepseek-r1:7b")
                    .temperature(0.4)
                    .build()
            ));
        return response.getResult().getOutput().getText();
    }
}
