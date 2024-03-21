package ru.teamscore.java23.springdata.users.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import ru.teamscore.java23.springdata.users.entity.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String>,
        PagingAndSortingRepository<User, String> {
    @Transactional
    @Modifying
    @Query("update User u set u.banned = true where u.username = ?1")
    int updateBannedByUsername(@NonNull String username);

    boolean existsByUsernameIgnoreCase(@NonNull String username);

    Optional<User> findByUsernameIgnoreCase(@NonNull String username);
}