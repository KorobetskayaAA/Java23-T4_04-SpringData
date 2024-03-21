package ru.teamscore.java23.springdata.storage.exceptions;

import ru.teamscore.java23.springdata.storage.entities.Item;
import ru.teamscore.java23.springdata.storage.entities.Storage;

public class NotEnoughStoredQuantityException extends RuntimeException {
    private final Item item;
    private final Storage source;
    private final Double quantity;

    public NotEnoughStoredQuantityException(Item item, Storage source, Double quantity) {
        super(String.format("Недостаточный запас %s в хранилище %s (требуется %.3f)",
                item.toString(), source.toString(), quantity));
        this.item = item;
        this.source = source;
        this.quantity = quantity;
    }
}
