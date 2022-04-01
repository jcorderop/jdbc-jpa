package com.database.courses.entity;


import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
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
@Cacheable
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
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.REMOVE,
                    CascadeType.MERGE
            },
            fetch = FetchType.LAZY)
    @NonNull
    private Passport passport;

    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY,
            mappedBy = "students",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    //CascadeType.REMOVE
                }
            )
    List<Course> courses = new ArrayList<>();

    public void addCourse(Course course) {
        if (courses.contains(course)) {
            log.info("Already added...");
        } else {
            courses.add(course);
            course.addStudent(this);
        }
    }

    public void removeCourse(Course course) {
        if (courses.contains(course)) {
            courses.remove(course);
            course.removeStudent(this);
        } else {
            log.info("Already removed...");
        }
    }

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
