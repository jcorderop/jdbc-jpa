package com.database.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private int id;
    private String name;
    private String location;
    private LocalDate birthDate;

}
