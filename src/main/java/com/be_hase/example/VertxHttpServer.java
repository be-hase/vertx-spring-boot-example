package com.be_hase.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.netty.handler.codec.http.HttpResponseStatus;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.handler.LoggerFormat;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.core.http.HttpClient;
import io.vertx.rxjava.core.http.HttpServer;
import io.vertx.rxjava.ext.web.Router;
import io.vertx.rxjava.ext.web.handler.BodyHandler;
import io.vertx.rxjava.ext.web.handler.LoggerHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class VertxHttpServer extends AbstractVerticle {
    @Autowired
    private HttpClient httpClient;

    @Override
    public void start() throws Exception {
        // Setting Router
        final Router router = Router.router(vertx);
        router.route().handler(LoggerHandler.create(LoggerFormat.DEFAULT));
        router.route().handler(BodyHandler.create().setBodyLimit(10 * 1024 * 1024));
        router.route(HttpMethod.GET, "/test").handler(context -> {
            log.info("test:{}", this);
            httpClient.getAbs("http://example.com/", event -> {
                context.response()
                       .putHeader(HttpHeaders.CONTENT_TYPE.toString(), "text/plain")
                       .end(HttpResponseStatus.OK.reasonPhrase());
            }).end();
        });

        // Setting VertxHttpServer
        final HttpServer httpServer = vertx.createHttpServer();
        httpServer.requestHandler(router::accept);
        httpServer.listen(8080);
    }
}
