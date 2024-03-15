package io.quarkusrobotshop.prorobot.infrastructure;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.quarkusrobotshop.prorobot.domain.Prorobot;
import io.quarkusrobotshop.prorobot.domain.exceptions.EightySixException;
import io.quarkusrobotshop.prorobot.domain.valueobjects.TicketIn;
import io.quarkusrobotshop.prorobot.domain.valueobjects.TicketUp;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
@RegisterForReflection
public class KafkaResource {

    final Logger logger = LoggerFactory.getLogger(KafkaResource.class);

    @Inject
    Prorobot prorobot;

    @Inject
    @Channel("orders-in")
    Emitter<TicketUp> orderUpEmitter;

    @Inject
    @Channel("eighty-six-out")
    Emitter<String> eightySixEmitter;

    @Incoming("prorobot-in")
    public CompletableFuture handleOrderIn(final TicketIn ticketIn) {

        logger.debug("TicketIn received: {}", ticketIn);

        return CompletableFuture.supplyAsync(() -> {
            return prorobot.make(ticketIn);
        }).thenApply(orderUp -> {
            logger.debug("OrderUp: {}", orderUp);
            orderUpEmitter.send(orderUp);
            return null;
        }).exceptionally(exception -> {
            logger.debug("EightySixException: {}", exception.getMessage());
            ((EightySixException) exception).getItems().forEach(item -> {
                eightySixEmitter.send(item.toString());
            });
            return null;
        });
    }
}
