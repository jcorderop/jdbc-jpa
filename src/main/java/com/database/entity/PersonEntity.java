package com.database.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity(name = "Person")
@Table(name = "person")
@NamedQuery(name = "find_all_persons", query = "select p from Person p")
public class PersonEntity {

    @Id
    @GeneratedValue
    private int id;

    @NonNull
    @Column(name = "name")
    private String name;

    @NonNull
    @Column(name = "location")
    private String location;

    @NonNull
    @Column(name = "birth_date")
    private LocalDate birthDate;
}
