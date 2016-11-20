package com.be_hase.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.vertx.core.http.HttpClientOptions;
import io.vertx.rxjava.core.http.HttpClient;

@Configuration
public class BeanConfiguration {
    @Bean
    public HttpClient httpClient() {
        return VertxApplication.vertx.createHttpClient(new HttpClientOptions());
    }
}
