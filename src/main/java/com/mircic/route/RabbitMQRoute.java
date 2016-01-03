package com.mircic.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

/**
 * Created by Ivan on 03-Jan-16.
 */
@Component
public class RabbitMQRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("rabbitmq://localhost:5672/inputQueue?queue=inputQueue")
                .process(exchange -> {
                    exchange.getIn().setHeader("rabbitmq.ROUTING_KEY", "exit");
                    exchange.getIn().setHeader("rabbitmq.EXCHANGE_NAME", "exitQueue");
                }).to("log:com.mircic.route.RabbitMQRoute?level=INFO")
                .to("rabbitmq://localhost:5672/exitQueue?queue=exitQueue&routingKey=exit");
    }
}
