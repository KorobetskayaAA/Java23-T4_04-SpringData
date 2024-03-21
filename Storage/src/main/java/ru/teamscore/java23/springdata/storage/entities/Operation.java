package ru.teamscore.java23.springdata.storage.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.teamscore.java23.springdata.storage.entities.embeddable.AuditedEntity;

import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "operation")
public class Operation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "operation_document_id", nullable = false)
    private OperationDocument operationDocument;

    @Column(name = "quantity")
    @JdbcTypeCode(SqlTypes.DOUBLE)
    private double quantity;

    @Embedded
    private final AuditedEntity auditedEntity = new AuditedEntity();
}