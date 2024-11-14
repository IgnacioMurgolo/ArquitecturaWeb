package org.tudai.reportservice.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BenefitsBetweenMonthsDTO {
    private int month;
    private Double benefit;

    public BenefitsBetweenMonthsDTO(int month, Double benefit) {
        this.month = month;
        this.benefit = benefit;
    }

    public BenefitsBetweenMonthsDTO() {}

}