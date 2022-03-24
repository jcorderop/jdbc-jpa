package com.database.courses.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;


}
