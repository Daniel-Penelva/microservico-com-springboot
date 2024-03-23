package com.microservice.notificationservice.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {

    // Cria uma instância estática e final da classe ObjectMapper da biblioteca Jackson, que é usada para mapear objetos Java para JSON e vice-versa.
    private static final ObjectMapper objectMapper = new ObjectMapper();

    /* Este método estático recebe um objeto Java como parâmetro e o converte em uma representação JSON. Ele utiliza o método writeValueAsString do
    objectMapper para realizar a serialização. */
    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /* Este método estático recebe uma string JSON e a classe do tipo de objeto desejado como parâmetros. Ele desserializa o JSON de volta para um
    objeto Java usando o método readValue do objectMapper. */
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
