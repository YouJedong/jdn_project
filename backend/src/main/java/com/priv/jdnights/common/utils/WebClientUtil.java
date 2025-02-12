package com.priv.jdnights.common.utils;

import com.priv.jdnights.common.exception.LogicException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
public class WebClientUtil {

    private final WebClient webClient;

    public WebClientUtil(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    public <T> T get(String url, Class<T> responseType) {
        try {
            return webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(responseType)
                    .block();

        } catch (WebClientResponseException e) {
            throw new LogicException("400", "Web Client Get 요청 시 오류: " + e.toString());
        }
    }


}


