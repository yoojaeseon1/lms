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
@DiscriminatorValue("T")
@NoArgsConstructor
@Getter
public class Teacher extends Member{

    public Teacher(String id, String password, String name, int age, Address address, LocalDate birthDate, MemberType memberType) {
        super(id, password, name, age, address, birthDate, memberType);
    }


    @OneToMany(mappedBy = "teacher")
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy")
    private List<CounselBoard> counselBoards = new ArrayList<>();


}
