package ru.teamscore.java23.springdata.storage.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import ru.teamscore.java23.springdata.storage.entities.embeddable.AuditedEntity;
import ru.teamscore.java23.springdata.storage.entities.embeddable.ReferenceEntity;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@Entity
@Table(name = "item")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private UUID id;

    @Embedded
    private ReferenceEntity referenceEntity;

    @Column(name = "title", nullable = false)
    private String title;

    @JsonIgnore
    @OneToMany(mappedBy = "item", orphanRemoval = true)
    private Set<StoredItem> storedItems = new LinkedHashSet<>();

    @Embedded
    private final AuditedEntity auditedEntity = new AuditedEntity();

    @Override
    public String toString() {
        return title + " [" + referenceEntity.getCode() + ']';
    }

    public Item(String code, String title) {
        this.referenceEntity = new ReferenceEntity(code);
        this.title = title;
    }
}