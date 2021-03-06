package com.database.courses.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode
@ToString
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity(name = "Passport")
@Table(name = "passport")
@NamedQueries(value = {
        @NamedQuery(name = "all_passport", query = "select c from Passport c"),
})
@Cacheable
public class Passport {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "number", nullable = false, unique = true)
    @NonNull
    private String number;

    @Column(name = "expiry_date", nullable = false)
    @NonNull
    private LocalDate expiryDate;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(fetch = FetchType.LAZY,
            mappedBy = "passport",
            cascade = {
                //CascadeType.PERSIST,
                //CascadeType.REMOVE,
                CascadeType.MERGE
            }
    )
    private Student student;

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

