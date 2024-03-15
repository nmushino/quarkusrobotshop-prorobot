package io.quarkuscoffeeshop.prorobot.domain;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkuscoffeeshop.prorobot.domain.valueobjects.TicketIn;
import io.quarkuscoffeeshop.prorobot.domain.valueobjects.TicketUp;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class ProrobotTest {

    static final Logger logger = Logger.getLogger(ProrobotTest.class.getName());

    @Inject
    Prorobot prorobot;

    @Test
    public void testOrderCakepop() throws ExecutionException, InterruptedException {

        logger.info("Test that a Cakepop is ready instantly");

        TicketIn orderIn = new TicketIn(UUID.randomUUID().toString(), UUID.randomUUID().toString(), Item.FAMKD8D8, "Minnie", Instant.now());

        TicketUp ticketUp = prorobot.make(orderIn);
        assertEquals(orderIn.getItem(), ticketUp.getItem());
        assertEquals(orderIn.getOrderId(), ticketUp.getOrderId());
        assertEquals(orderIn.getName(), ticketUp.getName());
    }
}
