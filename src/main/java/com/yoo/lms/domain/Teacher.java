package com.yoo.lms.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("T")
@Getter
public class Teacher extends Member{

    @OneToMany(mappedBy = "teacher")
    private List<Course> courses = new ArrayList<>();

    @OneToMany(mappedBy = "counselee")
    private List<CounselBoard> counselBoards = new ArrayList<>();

}
