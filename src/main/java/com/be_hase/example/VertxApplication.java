package com.be_hase.example;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.vertx.core.VertxOptions;
import io.vertx.ext.dropwizard.DropwizardMetricsOptions;
import io.vertx.ext.dropwizard.Match;
import io.vertx.ext.dropwizard.MatchType;
import io.vertx.rxjava.core.Vertx;

@SpringBootApplication
public class VertxApplication {
    public static Vertx vertx;

    private final VertxHttpServer vertxHttpServer;

    public VertxApplication(VertxHttpServer vertxHttpServer) {
        this.vertxHttpServer = vertxHttpServer;
    }

    public static void main(String[] args) {
        VertxOptions vertxOptions = new VertxOptions()
                .setMetricsOptions(new DropwizardMetricsOptions()
                                           .setJmxEnabled(true)
                                           .addMonitoredHttpClientEndpoint(
                                                   new Match()
                                                           .setValue(".*:.*")
                                                           .setType(MatchType.REGEX)));

        vertx = Vertx.vertx(vertxOptions);

        SpringApplication.run(VertxApplication.class, args);

//        When you want to use CPU core effectively.
//
//        ※ Attention HttpClient usage.
//        http://vertx.io/docs/vertx-core/java/#_httpclient_usage
//
//        IntStream.range(0, vertxOptions.getEventLoopPoolSize()).forEach(i -> {
//            SpringApplication.run(VertxApplication.class, args);
//        });
    }

    @PostConstruct
    public void deployVerticle() {
        io.vertx.core.Vertx delegate = (io.vertx.core.Vertx) vertx.getDelegate();
        delegate.deployVerticle(vertxHttpServer);
    }
}
