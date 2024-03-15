package io.quarkuscoffeeshop.prorobot.infrastructure;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.junit.mockito.InjectSpy;
import io.quarkuscoffeeshop.prorobot.domain.Item;
import io.quarkuscoffeeshop.prorobot.domain.Prorobot;
import io.quarkuscoffeeshop.prorobot.domain.valueobjects.TicketIn;
import io.quarkuscoffeeshop.prorobot.testing.KafkaTestProfile;
import io.quarkuscoffeeshop.prorobot.testing.KafkaTestResource;
import io.smallrye.reactive.messaging.connectors.InMemoryConnector;
import io.smallrye.reactive.messaging.connectors.InMemorySource;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Any;
import javax.inject.Inject;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@QuarkusTest
@QuarkusTestResource(KafkaTestResource.class)
public class KafkaResourceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaResourceTest.class);

    @ConfigProperty(name = "mp.messaging.incoming.prorobot-in.topic")
    protected String PROBOT_IN;

    @InjectSpy
    Prorobot prorobot;

    @Inject
    @Any
    InMemoryConnector connector;

    InMemorySource<TicketIn> ordersIn;

//    @BeforeEach
//    public void setUp() {
//        ordersIn = connector.source(ORDERS_IN);
//    }

    @Test
    public void testOrderIn() {

        LOGGER.debug("testOrderIn");

        TicketIn ticketIn = new TicketIn(
            UUID.randomUUID().toString(),
            UUID.randomUUID().toString(),
            Item.FAMKD8D8,
            "Uhura",
            Instant.now()
        );
        ordersIn = connector.source("prorobot-in");
        ordersIn.send(ticketIn);
        await().atLeast(6, TimeUnit.SECONDS);
        verify(prorobot, times(1)).make(any(TicketIn.class));
    }
}
