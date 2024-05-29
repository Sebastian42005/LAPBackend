package org.example.buchibackend.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.example.buchibackend.enums.Type;

import java.time.Instant;

@Entity
@Table(name = "transaction")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private Instant transaction_date;
    @Column(nullable = false)
    private double amount;
    @Column(nullable = false)
    private Type type;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Kassenbuch kassenbuch;
}
