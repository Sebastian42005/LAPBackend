package org.example.buchibackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.buchibackend.domain.Kassenbuch;
import org.example.buchibackend.domain.Transaction;
import org.example.buchibackend.service.KassenbuchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kassenbuch")
@RequiredArgsConstructor
public class KassenbuchController {

    private final KassenbuchService kassenbuchService;

    @GetMapping
    public ResponseEntity<List<Kassenbuch>> getKassenbuchs() {
        return ResponseEntity.ok(kassenbuchService.getKassenbuchs());
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable int id) {
        return ResponseEntity.ok(kassenbuchService.getTransactions(id));
    }

        @GetMapping("/{id}")
    public ResponseEntity<Kassenbuch> getKassenbuch(@PathVariable int id) {
        return ResponseEntity.ok(kassenbuchService.getKassenbuch(id));
    }

    @PostMapping
    public ResponseEntity<Kassenbuch> saveKassenbuch(@RequestBody Kassenbuch kassenbuch) {
        return ResponseEntity.status(HttpStatus.CREATED).body(kassenbuchService.createKassenbuch(kassenbuch));
    }

    @PutMapping
    public ResponseEntity<Kassenbuch> updateKassenbuch(@RequestBody Kassenbuch kassenbuch) {
        return ResponseEntity.ok(kassenbuchService.updateKassenbuch(kassenbuch));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteKassenbuch(@PathVariable int id) {
        kassenbuchService.deleteKassenbuch(id);
        return ResponseEntity.ok(Map.of("message", "Kassenbuch deleted successfully"));
    }
}
