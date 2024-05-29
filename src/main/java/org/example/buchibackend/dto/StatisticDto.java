package org.example.buchibackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class StatisticDto {
    Instant x;
    double y;
}
