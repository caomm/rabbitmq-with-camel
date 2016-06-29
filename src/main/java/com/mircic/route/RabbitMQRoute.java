package com.mircic.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * This route represent example how RabbitMQ intyernal routing actually work.
 *
 * Here are two routes(separate ones)
 *
 * 1. Simple Rest server which allow us to post to input queue
 * 2. RabbitMQ one which actually read from exit queue
 *
 * Roting is done by using routing_key and routing_exchange_name values
 * (in that way rabbitmq recognize to qhihc queue message eventually go)
 */
@Component
public class RabbitMQRoute extends RouteBuilder {
    @Value("${rest.port}")
    private Integer restPort;

    @Override
    public void configure() throws Exception {
        restConfiguration().component("jetty")
                .port(restPort);

        from("rest:post:/rest/postMessageToInputQueue")
                .process(exchange -> {
                    exchange.getIn().setHeader("rabbitmq.ROUTING_KEY", "exit");
                    exchange.getIn().setHeader("rabbitmq.EXCHANGE_NAME", "exitQueue");
                })
                .inOnly("rabbitmq://{{rabbitmq.host}}:5672/inputQueue?queue=inputQueue");


        from("rabbitmq://{{rabbitmq.host}}:5672/exitQueue?queue=exitQueue&routingKey=exit")
                .to("log:com.mircic.route.RabbitMQRoute?level=INFO");
    }
}
