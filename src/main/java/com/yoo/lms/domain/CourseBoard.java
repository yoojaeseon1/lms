package com.yoo.lms.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@DiscriminatorValue("C")
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class CourseBoard extends Board{

    public CourseBoard(Course course, String title, String content, Member createdBy) {
        super(title,content, createdBy);
        this.course = course;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id")
    private Course course;

}
