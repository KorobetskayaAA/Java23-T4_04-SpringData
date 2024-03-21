package ru.teamscore.java23.springdata.storage.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import ru.teamscore.java23.springdata.storage.entities.Storage;

import java.util.Optional;
import java.util.UUID;

public interface StorageRepository extends CrudRepository<Storage, UUID> {
    Storage findByReferenceEntity_Code(@NonNull String code);
}