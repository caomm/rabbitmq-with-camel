package com.mircic.route;

import org.apache.camel.builder.NotifyBuilder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Ivan on 05-Feb-16.
 */
public class RabbitMQRouteTest extends CamelTestSupport {

    @Test
    public void testNormalClient() throws IOException{
        NotifyBuilder notify = new NotifyBuilder(context).whenDone(1).create();

        String message1Sent = IOUtils.toString(this.getClass().getResourceAsStream("/test-data/message1.xml"));
        template().sendBody("rabbitmq://localhost:5672/inputQueue?queue=inputQueue", message1Sent);

        notify.from("rabbitmq://localhost:5672/exitQueue?queue=exitQueue&routingKey=exit").equals(message1Sent);
    }
}
