package com.mircic.route;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Ivan on 03-Jan-16.
 */
@Component
public class RestRoute extends RouteBuilder {
    @Value("${rest.port}")
    private Integer restPort;

    @Override
    public void configure() throws Exception {
        restConfiguration().component("jetty")
                .port(restPort);

        from("rest:post:/rest//postMessageToInputQueue")
                .inOnly("rabbitmq://{{rabbitmq.host}}:5672/inputQueue?queue=inputQueue");
    }
}
