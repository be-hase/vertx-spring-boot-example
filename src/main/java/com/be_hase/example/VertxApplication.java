package com.be_hase.example;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.vertx.rxjava.core.Vertx;
import io.vertx.rxjava.core.http.HttpClient;

@SpringBootApplication
public class VertxApplication {
    private static Vertx vertx = Vertx.vertx();

    @Autowired
    private VertxHttpServer vertxHttpServer;

    public static void main(String[] args) {
        SpringApplication.run(VertxApplication.class, args);
        SpringApplication.run(VertxApplication.class, args);
    }

    @PostConstruct
    public void deployVerticle() {
        ((io.vertx.core.Vertx) vertx.getDelegate()).deployVerticle(vertxHttpServer);
    }

    @Bean
    public HttpClient httpClient() {
        return vertx.createHttpClient();
    }
}
