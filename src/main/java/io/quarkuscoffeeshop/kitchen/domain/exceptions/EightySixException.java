package io.quarkusrobotshop.prorobot.domain.exceptions;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.quarkusrobotshop.prorobot.domain.EightySixEvent;
import io.quarkusrobotshop.prorobot.domain.Item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Thrown when an item is out of stock
 */
@RegisterForReflection
public class EightySixException extends RuntimeException {

        List<Item> items;

        public EightySixException(Item eightySixedItem) {

            this.items = new ArrayList(){{ add(eightySixedItem); }};
        }

        public EightySixException(List<Item> eightySixedItems) {
            this.items = eightySixedItems;
        }

        public List<Item> getItems() {
            return items;
        }
}
