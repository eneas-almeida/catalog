package com.correios.catalog.apis;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FakeStoreApi {

    private final WebClient.Builder webClientBuilder;

    public FakeStoreApi(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<String> getProducts() {
        String apiUrl = "https://fakestoreapi.com/products";

        return webClientBuilder.baseUrl(apiUrl)
                .build()
                .get()
                .retrieve()
                .bodyToMono(String.class);
    }
}
