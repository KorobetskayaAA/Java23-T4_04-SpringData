package ru.teamscore.java23.springdata.storage.entities.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class ReferenceEntity {
    @Column(name = "code", nullable = false, unique = true, length = 9)
    private String code;
}