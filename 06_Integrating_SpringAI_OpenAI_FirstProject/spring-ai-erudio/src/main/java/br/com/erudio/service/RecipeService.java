package br.com.erudio.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecipeService {

    private final ChatModel chatModel;

    public RecipeService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String createRecipe(String ingredients,
                               String cuisine,
                               String dietaryRestrictions) {
        var template = """
                I want to create a recipe using the following ingredients: {ingredients}
                The cuisine type I prefer is {cuisine}.
                Please consider the following dietary restrictions: {dietaryRestrictions}.
                Please provide me with a detailed recipe including title, list of ingredients, and cooking instructions
                """;

        PromptTemplate promptTemplate = new PromptTemplate(template);
        Map<String, Object> params = Map.of(
            "ingredients", ingredients,
            "cuisine", cuisine,
            "dietaryRestrictions", dietaryRestrictions
        );

        Prompt prompt = promptTemplate.create(params);

        return chatModel.call(prompt).getResult().getOutput().getText();
    }
}
