package ru.teamscore.java23.springdata.storage.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import ru.teamscore.java23.springdata.storage.entities.Item;

import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
    Item findByReferenceEntity_Code(@NonNull String code);
}