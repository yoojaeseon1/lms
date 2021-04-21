
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

    public Student(String id, String password, String name, String email, Address address, LocalDate birthDate, MemberType memberType) {
        super(id,
                password,
                name,
                email,
                address,
                birthDate,
                memberType
        );
    }

    public Student(Member member) {
        super(member.getId(),
                member.getPassword(),
                member.getName(),
                member.getEmail(),
                member.getAddress(),
                member.getBirthDate(),
                member.getMemberType()
        );
    }


    @OneToMany(mappedBy = "student")
    private List<StudentCourse> studentCourses = new ArrayList<>();

    @OneToMany(mappedBy="student")
    private List<Attendance> attendances = new ArrayList<>();


}
