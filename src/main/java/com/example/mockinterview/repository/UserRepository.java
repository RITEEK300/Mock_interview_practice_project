package com.example.mockinterview.repository;

import com.example.mockinterview.entity.User;
import com.example.mockinterview.entity.Preference;
import com.example.mockinterview.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByPreferenceAndStatusOrderByIdAsc(Preference preference, Status status);
}