package com.database.employee.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.math.BigDecimal;

@Slf4j
@EqualsAndHashCode
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@RequiredArgsConstructor
@Entity
public class PartTimeEmployee extends Employee {

    @Column(name = "hourly_rate")
    private BigDecimal hourlyRate;

    public PartTimeEmployee(String name, BigDecimal hourlyRate) {
        super(name);
        this.hourlyRate = hourlyRate;
    }

}
