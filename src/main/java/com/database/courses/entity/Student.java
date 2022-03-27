package com.database.courses.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity(name = "Student")
@Table(name = "student")
@NamedQueries(value = {
        @NamedQuery(name = "all_students", query = "select c from Student c"),
})
public class Student {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    @NonNull
    private String name;

    //https://www.baeldung.com/hibernate-initialize-proxy-exception
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToOne(orphanRemoval = true,
            optional = false,
            fetch = FetchType.LAZY)
    @NonNull
    private Passport passport;

    //optimistic updates
    @EqualsAndHashCode.Exclude
    @Version
    private int version;

    //update dates
    @EqualsAndHashCode.Exclude
    @Column(name = "create_date")
    @CreationTimestamp
    private LocalDateTime createDate;

    @EqualsAndHashCode.Exclude
    @Column(name = "update_date")
    @UpdateTimestamp
    private LocalDateTime updateDate;
}
