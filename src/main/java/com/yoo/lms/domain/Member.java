package com.yoo.lms.domain;

import com.yoo.lms.domain.valueType.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@RequiredArgsConstructor
@Getter @Setter
public class Member {

    @Id
    @Column(name="member_id")
    private String id;

    private String password;
    private String name;
    private int age;

    @Embedded
    private Address address;

}
