package com.database.courses.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Entity(name = "Review")
@Table(name = "review")
@NamedQueries(value = {
        @NamedQuery(name = "all_review", query = "select c from Review c"),
})
public class Review {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "rating", nullable = false, updatable = false)
    @NonNull
    private int rating;

    @Column(name = "description", nullable = false)
    @NonNull
    private String description;

    //optimistic updates
    @Version
    private int version;

    //update dates
    @Column(name = "create_date")
    @CreationTimestamp
    private LocalDateTime createDate;

    @Column(name = "update_date")
    @UpdateTimestamp
    private LocalDateTime updateDate;
}

