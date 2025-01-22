package com.challengealura.literalura_challenge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertData implements IConvertData {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {

        if (json == null || json.isBlank()) {
            throw new IllegalArgumentException("El JSON de entrada está vacío o es nulo");
        }

        try {
            return mapper.readValue(json, clase);
        } catch (JsonProcessingException e) {

            throw new RuntimeException("Error procesando el JSON: " + e.getMessage(), e);
        }
    }
}