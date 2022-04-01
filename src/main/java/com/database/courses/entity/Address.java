package com.database.courses.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {

    @Column(nullable = true)
    private String city;
    @Column(nullable = true)
    private String street;
    @Column(nullable = true)
    private String number;
    @Column(nullable = true)
    private int postCode;
}
