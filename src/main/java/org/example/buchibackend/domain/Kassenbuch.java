package org.example.buchibackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.example.buchibackend.enums.Type;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "kassenbuch")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Kassenbuch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne(mappedBy = "kassenbuch")
    private User user;
    @OneToMany(mappedBy = "kassenbuch")
    @JsonIgnore
    private List<Transaction> transactionList;
}
