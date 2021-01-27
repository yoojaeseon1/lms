
package com.yoo.lms.domain;

import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.domain.valueType.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("S")
@Getter
@NoArgsConstructor
public class Student extends Member{

    public Student(String id, String password, String name, int age, Address address, LocalDate birthDate, MemberType memberType) {
        super(id, password, name, age, address, birthDate, memberType);
    }

    @OneToMany(mappedBy = "student")
    private List<StudentCourse> studentCourses = new ArrayList<>();

    @OneToMany(mappedBy="student")
    private List<Attendance> attendances = new ArrayList<>();

    @OneToMany(mappedBy = "contentCreatedBy")
    private List<CounselBoard> counselBoards = new ArrayList<>();

}
