package br.com.erudio.service;

import org.springframework.ai.image.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    // private final ImageModel imageModel;

    public ImageService() {}

    /*public ImageService(ImageModel imageModel) {
        this.imageModel = imageModel;
    }*/

    public ImageResponse generateImage(
            String prompt,
            String quality,
            Integer n,
            Integer height,
            Integer width) {

//
//        ImageResponse imageResponse = imageModel.call(
//            new ImagePrompt(prompt,
//                OpenAiImageOptions.builder()
//                    .model("janus-pro")
//                    .quality(quality)
//                    .N(n)
//                    .height(height)
//                    .width(width).build())
//
//        );

        List<ImageGeneration> imageGenerations = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String url = "https://placehold.co/" + width + "x" + height + "?text=Mocked+Image";
            String b64Json = null;

            Image image = new Image(url, b64Json);
            ImageGeneration imageGeneration = new ImageGeneration(image);
            imageGenerations.add(imageGeneration);
        }

        ImageResponse imageResponse = new ImageResponse(imageGenerations);

        return imageResponse;
    }
}
