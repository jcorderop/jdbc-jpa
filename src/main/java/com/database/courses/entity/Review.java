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
@Entity(name = "Review")
@Table(name = "review")
@NamedQueries(value = {
        @NamedQuery(name = "all_review", query = "select c from Review c"),
})
@Cacheable
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "rating", nullable = false, updatable = false)
    @NonNull
    private int rating;

    @Column(name = "description")
    @NonNull
    private String description;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;

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

