package com.challengealura.literalura_challenge.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class GPTChatQuery {

    public static String obtenerTraduccion(String texto) {
        OpenAiService service = new OpenAiService("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");

        CompletionRequest requisicion = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("traduce a español el siguiente texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var respuesta = service.createCompletion(requisicion);
        return respuesta.getChoices().get(0).getText();
    }
}
