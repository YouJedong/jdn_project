package com.priv.jdnights.common.utils;

import com.priv.jdnights.api.batch.service.NextClassBatchService;
import com.priv.jdnights.common.exception.LogicException;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.netty.http.client.HttpClient;

@Component
public class WebClientUtil {

    private static final Logger log = LoggerFactory.getLogger(WebClientUtil.class);

    private final WebClient webClient;

    @Autowired
    public WebClientUtil(WebClient webClient) {
        this.webClient = webClient;
    }

    public <T> T get(String url, Class<T> responseType) {
        try {
            log.info("WebClient 통신 시작 : {}", url);
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


