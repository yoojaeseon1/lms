
package com.yoo.lms.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("S")
@Getter
public class Student extends Member{


    public static Student createStudent(String id, String password, String name, int age) {
        Student student = new Student();

        student.setId(id);
        student.setPassword(password);
        student.setName(name);
        student.setAge(age);

        return student;
    }

    @OneToMany(mappedBy = "student")
    private List<StudentCourse> studentCourse = new ArrayList<>();

    @OneToMany(mappedBy="student")
    private List<Attendance> attendances = new ArrayList<>();

    @OneToMany(mappedBy = "counselee")
    private List<CounselBoard> counselBoards = new ArrayList<>();
}
