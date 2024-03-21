package ru.teamscore.java23.springdata.users.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode(of = "username")
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "username", nullable = false, unique = true)
    @lombok.NonNull
    private String username;

    @Column(name = "personal_name")
    private String personalName;

    @Column(name = "family_name")
    private String familyName;

    @Column(name = "email")
    @lombok.NonNull
    private String email;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate = LocalDateTime.now();

    @Column(name = "banned", nullable = false)
    private boolean banned = false;
}