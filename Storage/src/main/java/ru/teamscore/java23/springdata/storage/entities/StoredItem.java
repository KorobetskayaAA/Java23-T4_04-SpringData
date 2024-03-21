package ru.teamscore.java23.springdata.storage.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.type.SqlTypes;
import ru.teamscore.java23.springdata.storage.entities.embeddable.AuditedEntity;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "storaged_item", indexes = {
        @Index(name = "idx_storageditem_item_id_unq", columnList = "item_id, storage_id", unique = true)
})
public class StoredItem {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(optional = false)
    @JoinColumn(name = "storage_id", nullable = false)
    private Storage storage;

    @Column(name = "quantity", nullable = false)
    @JdbcTypeCode(SqlTypes.DOUBLE)
    private double quantity;
    public void addQuantity(double quantity) {
        this.quantity += quantity;
    }

    @Embedded
    private final AuditedEntity auditedEntity = new AuditedEntity();

    public StoredItem(Item item, Storage storage, double quantity) {
        this.item = item;
        this.storage = storage;
        this.quantity = quantity;
    }
}