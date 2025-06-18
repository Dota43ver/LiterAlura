package com.alura.Literalura.service;

import com.alura.Literalura.LiteraluraApplication;
import org.springframework.boot.SpringApplication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;



public class ConsumoApi {
    public String obtenerDatos(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();
            if (json == null || json.trim().isEmpty()) {
                System.out.println("Error: Respuesta vacía del servidor");
                return null;
            }
            return json;
        } catch (IOException | InterruptedException e) {
            System.out.println("Error en la solicitud: " + e.getMessage());
            throw new RuntimeException("Error al obtener datos: " + e.getMessage(), e);
        }
    }
}

