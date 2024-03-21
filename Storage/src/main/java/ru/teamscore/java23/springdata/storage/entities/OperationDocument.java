package ru.teamscore.java23.springdata.storage.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import ru.teamscore.java23.springdata.storage.entities.embeddable.AuditedEntity;
import ru.teamscore.java23.springdata.storage.entities.embeddable.DocumentEntity;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "operation_document")
public class OperationDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private UUID id;

    @Embedded
    private DocumentEntity documentEntity;

    @ManyToOne
    @JoinColumn(name = "source_id")
    private Storage source;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Storage destination;

    @Setter(AccessLevel.NONE)
    @OneToMany(mappedBy = "operationDocument", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Operation> operations = new LinkedHashSet<>();

    @Embedded
    private final AuditedEntity auditedEntity = new AuditedEntity();
}