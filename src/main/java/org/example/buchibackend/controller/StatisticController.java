package org.example.buchibackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.buchibackend.domain.Kassenbuch;
import org.example.buchibackend.domain.Transaction;
import org.example.buchibackend.dto.StatisticDto;
import org.example.buchibackend.enums.Type;
import org.example.buchibackend.repository.KassenbuchRepository;
import org.example.buchibackend.repository.TransactionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/statistic")
@RequiredArgsConstructor
public class StatisticController {


    private final TransactionRepository transactionRepository;

    @GetMapping
    public List<StatisticDto> getStatistic() {
        List<StatisticDto> statisticDtos = new ArrayList<>();
        List<Transaction> transactionList = transactionRepository.findAll();
        transactionList.sort(Comparator.comparing(Transaction::getTransaction_date));
        double currentMoney = 0;
        for (Transaction transaction : transactionList) {
            if (transaction.getType() == Type.REVENUE) {
                currentMoney += transaction.getAmount();
            }else {
                currentMoney -= transaction.getAmount();
            }
            statisticDtos.add(new StatisticDto(transaction.getTransaction_date(), currentMoney));
        }
        return statisticDtos;
    }
}
