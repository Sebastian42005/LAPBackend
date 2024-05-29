package org.example.buchibackend.service;

import lombok.RequiredArgsConstructor;
import org.example.buchibackend.domain.Kassenbuch;
import org.example.buchibackend.domain.Transaction;
import org.example.buchibackend.domain.User;
import org.example.buchibackend.repository.KassenbuchRepository;
import org.example.buchibackend.repository.TransactionRepository;
import org.example.buchibackend.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final KassenbuchRepository kassenbuchRepository;

    @Transactional(readOnly = true)
    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Transaction getTransaction(int id) {
        return transactionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public Transaction createTransaction(Transaction transaction, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));
        transaction.setTransaction_date(Instant.now());
        transaction.setKassenbuch(getUserKassenBuch(user));
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Kassenbuch getUserKassenBuch(User user) {
        Kassenbuch kassenbuch = user.getKassenbuch();
        if (kassenbuch != null) {
            return kassenbuch;
        }else {
            Kassenbuch dbKassenbuch = new Kassenbuch();
            dbKassenbuch.setUser(user);
            user.setKassenbuch(kassenbuchRepository.save(dbKassenbuch));
            return dbKassenbuch;
        }
    }

    @Transactional
    public Transaction updateTransaction(Transaction transaction) {
        Transaction dbTransaction = transactionRepository.findById(transaction.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        dbTransaction.setTransaction_date(transaction.getTransaction_date());
        dbTransaction.setAmount(transaction.getAmount());
        dbTransaction.setType(transaction.getType());
        dbTransaction.setCategory(transaction.getCategory());
        dbTransaction.setKassenbuch(transaction.getKassenbuch());
        return transactionRepository.save(dbTransaction);
    }

    @Transactional
    public void deleteTransaction(int id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        transactionRepository.delete(transaction);
    }
}
