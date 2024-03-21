package ru.teamscore.java23.springdata.storage.repos;

import org.springframework.data.repository.CrudRepository;
import ru.teamscore.java23.springdata.storage.entities.Operation;

import java.util.UUID;

public interface OperationRepository extends CrudRepository<Operation, UUID> {
}