package ru.teamscore.java23.springdata.storage.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.teamscore.java23.springdata.storage.exceptions.DocumentAlreadyExistsException;
import ru.teamscore.java23.springdata.storage.exceptions.NotFoundException;
import ru.teamscore.java23.springdata.storage.model.Income;
import ru.teamscore.java23.springdata.storage.model.Movement;
import ru.teamscore.java23.springdata.storage.entities.*;
import ru.teamscore.java23.springdata.storage.entities.embeddable.DocumentEntity;
import ru.teamscore.java23.springdata.storage.exceptions.NotEnoughStoredQuantityException;
import ru.teamscore.java23.springdata.storage.model.Sale;
import ru.teamscore.java23.springdata.storage.repos.*;

import java.time.LocalDateTime;

@Service
public class StorageService {

    private final OperationDocumentRepository operationDocumentRepository;
    private final StoredItemRepository storedItemRepository;
    private final StorageRepository storageRepository;
    private final ItemRepository itemRepository;
    private final OperationRepository operationRepository;
    private final ModelMapper modelMapper;

    public StorageService(OperationDocumentRepository operationDocumentRepository,
                          StoredItemRepository storedItemRepository,
                          StorageRepository storageRepository,
                          ItemRepository itemRepository,
                          OperationRepository operationRepository,
                          ModelMapper modelMapper) {
        this.operationDocumentRepository = operationDocumentRepository;
        this.storedItemRepository = storedItemRepository;
        this.storageRepository = storageRepository;
        this.itemRepository = itemRepository;
        this.operationRepository = operationRepository;
        this.modelMapper = modelMapper;
    }

    public ru.teamscore.java23.springdata.storage.model.Storage getAllStoredItems(String storageCode) {
        var storageDb = getStorage(storageCode);
        return new ru.teamscore.java23.springdata.storage.model.Storage(storageDb.getReferenceEntity().getCode(),
                storageDb.getStoredItems().stream()
                        .map(si -> new ru.teamscore.java23.springdata.storage.model.StoredItem(
                                si.getItem().getReferenceEntity().getCode(),
                                si.getItem().getTitle(),
                                si.getQuantity()
                        ))
                        .toList()
                );
    }

    // TODO return model class
    @Transactional
    public OperationDocument processMovement(Movement movement) {
        Storage source = getStorage(movement.getSourceStorageCode());
        Storage destination = getStorage(movement.getDestinationStorageCode());
        OperationDocument document = createDocument(movement.getNumber(), movement.getDocumentDate(), source, destination);
        for (var opItem : movement.getItems()) {
            Item item = getItem(opItem.getItemCode());
            takeItem(item, source, opItem.getQuantity());
            putItem(item, destination, opItem.getQuantity());
            document.getOperations().add(saveOperation(document, item, opItem.getQuantity()));
        }
        return document;
    }

    // TODO return model class
    @Transactional
    public OperationDocument processIncome(Income income) {
        Storage destination = getStorage(income.getDestinationStorageCode());
        OperationDocument document = createDocument(income.getNumber(), income.getDocumentDate(), null, destination);
        for (var opItem : income.getItems()) {
            Item item = getItem(opItem.getItemCode());
            putItem(item, destination, opItem.getQuantity());
            document.getOperations().add(saveOperation(document, item, opItem.getQuantity()));
        }
        return document;
    }

    // TODO return model class
    @Transactional
    public OperationDocument processSale(Sale sale) {
        Storage source = getStorage(sale.getSourceStorageCode());
        OperationDocument document = createDocument(sale.getNumber(), sale.getDocumentDate(), source, null);
        for (var opItem : sale.getItems()) {
            Item item = getItem(opItem.getItemCode());
            takeItem(item, source, opItem.getQuantity());
            document.getOperations().add(saveOperation(document, item, opItem.getQuantity()));
        }
        return document;
    }

    protected Storage getStorage(String code) {
        Storage storage = storageRepository.findByReferenceEntity_Code(code);
        if (storage == null) {
            throw new NotFoundException(code, "source");
        }
        return storage;
    }

    protected Item getItem(String code) {
        Item item = itemRepository.findByReferenceEntity_Code(code);
        if (item == null) {
            throw new NotFoundException(code, "item");
        }
        return item;
    }

    protected OperationDocument createDocument(String number, LocalDateTime date, Storage source, Storage destination) {
        if (operationDocumentRepository.existsByDocumentEntity_Number(number)) {
            throw new DocumentAlreadyExistsException(number);
        }
        OperationDocument document = new OperationDocument();
        document.setDocumentEntity(new DocumentEntity(number, date));
        document.setSource(source);
        document.setDestination(destination);
        return operationDocumentRepository.save(document);
    }

    protected void takeItem(Item item, Storage source, double quantity) {
        if (source != null) {
            if (storedItemRepository.existsSufficientQuantityItemAtStorage(item, source, quantity)) {
                storedItemRepository.updateDecreaseQuantityByItemAndStorage(quantity, item, source);
            }
            else {
                throw new NotEnoughStoredQuantityException(item, source, quantity);
            }
        }
    }

    protected void putItem(Item item, Storage destination, double quantity) {
        if (destination != null) {
            var storedItem = storedItemRepository.findByItemAndStorage(item, destination)
                    .orElseGet(() -> new StoredItem(item, destination, 0));
            storedItem.addQuantity(quantity);
            storedItemRepository.save(storedItem);
        }
    }

    protected Operation saveOperation(OperationDocument document, Item item, double quantity) {
        Operation operation = new Operation();
        operation.setOperationDocument(document);
        operation.setItem(item);
        operation.setQuantity(quantity);
        return operationRepository.save(operation);
    }
}
