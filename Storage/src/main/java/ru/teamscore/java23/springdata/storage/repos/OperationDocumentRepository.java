package ru.teamscore.java23.springdata.storage.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import ru.teamscore.java23.springdata.storage.entities.OperationDocument;

import java.util.UUID;

public interface OperationDocumentRepository extends JpaRepository<OperationDocument, UUID> {
    boolean existsByDocumentEntity_Number(@NonNull String number);
}