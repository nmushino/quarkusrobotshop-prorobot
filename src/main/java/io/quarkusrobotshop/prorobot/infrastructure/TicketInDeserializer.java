package io.quarkusrobotshop.prorobot.infrastructure;

import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import io.quarkusrobotshop.prorobot.domain.valueobjects.TicketIn;

public class TicketInDeserializer extends ObjectMapperDeserializer<TicketIn> {

    public TicketInDeserializer() {
        super(TicketIn.class);
    }
}
