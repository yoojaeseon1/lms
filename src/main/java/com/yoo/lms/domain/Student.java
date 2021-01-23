
package com.yoo.lms.domain;

import com.yoo.lms.domain.valueType.Address;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("S")
@Getter
public class Student extends Member{

    public Student() {
    }

    public Student(String id, String password, String name, int age, Address address) {
        super(id, password, name, age, address);
    }

    @OneToMany(mappedBy = "student")
    private List<StudentCourse> studentCourses = new ArrayList<>();

    @OneToMany(mappedBy="student")
    private List<Attendance> attendances = new ArrayList<>();

    @OneToMany(mappedBy = "counselee")
    private List<CounselBoard> counselBoards = new ArrayList<>();

}
