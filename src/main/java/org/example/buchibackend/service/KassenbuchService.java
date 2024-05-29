package org.example.buchibackend.service;

import lombok.RequiredArgsConstructor;
import org.example.buchibackend.domain.Kassenbuch;
import org.example.buchibackend.domain.Transaction;
import org.example.buchibackend.repository.KassenbuchRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KassenbuchService {

    private final KassenbuchRepository kassenbuchRepository;

    public List<Kassenbuch> getKassenbuchs() {
        return kassenbuchRepository.findAll();
    }

    public Kassenbuch getKassenbuch(int id) {
        return kassenbuchRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Kassenbuch createKassenbuch(Kassenbuch kassenbuch) {
        return kassenbuchRepository.save(kassenbuch);
    }

    public Kassenbuch updateKassenbuch(Kassenbuch kassenbuch) {
        Kassenbuch dbKassenbuch = kassenbuchRepository.findById(kassenbuch.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        dbKassenbuch.setUser(kassenbuch.getUser());
        return kassenbuchRepository.save(dbKassenbuch);
    }

    public void deleteKassenbuch(int id) {
        Kassenbuch kassenbuch = kassenbuchRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        kassenbuchRepository.delete(kassenbuch);
    }

    public List<Transaction> getTransactions(int id) {
        Kassenbuch kassenbuch =  kassenbuchRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return kassenbuch.getTransactionList();
    }
}
