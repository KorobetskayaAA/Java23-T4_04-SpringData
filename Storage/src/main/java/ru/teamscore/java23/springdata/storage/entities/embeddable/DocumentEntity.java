package ru.teamscore.java23.springdata.storage.entities.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class DocumentEntity {
    @Column(name = "code", nullable = false, unique = true, length = 12)
    @JdbcTypeCode(SqlTypes.NVARCHAR)
    private String number;

    @Column(name = "document_date", nullable = false)
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    private LocalDateTime documentDate;
}