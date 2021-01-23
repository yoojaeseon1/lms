package com.yoo.lms.domain;

import com.yoo.lms.domain.valueType.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("T")
@NoArgsConstructor
@Getter
public class Teacher extends Member{

    public Teacher(String id, String password, String name, int age, Address address) {
        super(id, password, name, age, address);
    }

    @OneToMany(mappedBy = "teacher")
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "counselee")
    private List<CounselBoard> counselBoards = new ArrayList<>();


}
