package org.example.buchibackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.buchibackend.domain.Transaction;
import org.example.buchibackend.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<List<Transaction>> getTransactions() {
        return ResponseEntity.ok(transactionService.getTransactions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable int id) {
        return ResponseEntity.ok(transactionService.getTransaction(id));
    }

    @PostMapping
    public ResponseEntity<Transaction> saveTransaction(@RequestBody Transaction transaction, Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.createTransaction(transaction, principal.getName()));
    }

    @PutMapping
    public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction) {
        return ResponseEntity.ok(transactionService.updateTransaction(transaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTransaction(@PathVariable int id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.ok(Map.of("message", "Transaction deleted successfully"));
    }
}
