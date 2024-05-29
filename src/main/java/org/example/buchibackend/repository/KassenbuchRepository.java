package org.example.buchibackend.repository;

import org.example.buchibackend.domain.Kassenbuch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KassenbuchRepository extends JpaRepository<Kassenbuch, Integer> {

}
