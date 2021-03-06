package com.database.courses.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

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
@Entity(name = "Course")
@Table(name = "course")
@NamedQueries(value = {
        @NamedQuery(name = "all_courses", query = "select c from Course c"),
        @NamedQuery(name = "all_courses_join_fetch", query = "select c from Course c join fetch c.students"),
})
@Cacheable
@SQLDelete(sql = "UPDATE Course SET deleted = true WHERE id = ? and -1 != ?")
@Where(clause = "deleted = false")
public class Course {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    @NonNull
    private String name;

    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "course",
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    List<Review> reviews = new ArrayList<>();

    public void addReview(Review review) {
        reviews.add(review);
    }

    public void removeReview(Review review) {
        reviews.remove(review);
    }

    @Setter(AccessLevel.NONE)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    //CascadeType.REMOVE,
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
        )
    @JoinTable(name = "student_course",
            joinColumns = @JoinColumn(name = "course_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    List<Student> students = new ArrayList<>();

    public void addStudent(Student student) {
        if (students.contains(student)) {
            log.info("Already added...");
        } else {
            students.add(student);
            student.addCourse(this);
        }
    }

    public void removeStudent(Student student) {
        if (students.contains(student)) {
            students.remove(student);
            student.removeCourse(this);
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

    private boolean deleted = false;

    @PreRemove
    private void preRemoved() {
        log.info("row deleted {}", this);
        this.deleted = true;
    }

}
