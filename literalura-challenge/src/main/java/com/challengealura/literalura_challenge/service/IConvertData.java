package com.challengealura.literalura_challenge.service;

public interface IConvertData {
    <T> T obtenerDatos(String json, Class<T> clase);
}
