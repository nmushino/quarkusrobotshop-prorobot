package io.quarkuscoffeeshop.prorobot.infrastructure;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import io.quarkuscoffeeshop.prorobot.domain.valueobjects.TicketIn;

public class TicketInDeserializer extends ObjectMapperDeserializer<TicketIn> {

    public TicketInDeserializer() {
        super(TicketIn.class);
    }
}
