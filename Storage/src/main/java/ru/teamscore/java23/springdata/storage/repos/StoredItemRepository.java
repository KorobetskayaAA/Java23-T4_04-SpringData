package ru.teamscore.java23.springdata.storage.repos;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import ru.teamscore.java23.springdata.storage.entities.Item;
import ru.teamscore.java23.springdata.storage.entities.Storage;
import ru.teamscore.java23.springdata.storage.entities.StoredItem;

import java.util.Optional;
import java.util.UUID;

public interface StoredItemRepository extends JpaRepository<StoredItem, UUID> {

    @Query("""
            select (count(s) > 0) from StoredItem s
            where s.item = ?1 and s.storage = ?2 and s.quantity >= ?3""")
    boolean existsSufficientQuantityItemAtStorage(@NonNull Item item, @NonNull Storage storage, @NonNull double quantity);

    Optional<StoredItem> findByItemAndStorage(@NonNull Item item, @NonNull Storage storage);

    @Transactional
    @Modifying
    @Query("update StoredItem s set s.quantity = s.quantity - cast(?1 as DOUBLE) where s.item = ?2 and s.storage = ?3")
    int updateDecreaseQuantityByItemAndStorage(@NonNull double quantity, @NonNull Item item, @NonNull Storage storage);
}