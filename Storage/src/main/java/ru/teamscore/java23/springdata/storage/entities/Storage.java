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
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "storage")
public class Storage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    @EqualsAndHashCode.Include
    private UUID id;

    @Embedded
    private ReferenceEntity referenceEntity;

    @Column(name = "title")
    private String title;

    @Column(name = "location")
    private String location;

    @JsonIgnore
    @OneToMany(mappedBy = "storage", orphanRemoval = true)
    private Set<StoredItem> storedItems = new LinkedHashSet<>();

    @Embedded
    private final AuditedEntity auditedEntity = new AuditedEntity();

    public Storage(String code, String title, String location) {
        this.referenceEntity = new ReferenceEntity(code);
        this.title = title;
        this.location = location;
    }

    @Override
    public String toString() {
        return title + " [" + referenceEntity.getCode() + "] " + location;
    }
}