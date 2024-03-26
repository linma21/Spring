package kr.co.ch07.dto;

import lombok.*;
import org.springframework.context.annotation.Bean;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAggDTO {
    private int priceSum;
    private double priceAvg;
    private int priceMax;
    private int priceMin;
}
