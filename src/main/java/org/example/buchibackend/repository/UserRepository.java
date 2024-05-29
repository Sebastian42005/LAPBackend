package org.example.buchibackend.repository;

import org.example.buchibackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u from User u WHERE u.email = :email AND u.password = :password")
    Optional<User> login(String email, String password);
}
