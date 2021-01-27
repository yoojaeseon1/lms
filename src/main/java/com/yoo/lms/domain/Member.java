package com.yoo.lms.domain;

import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.domain.valueType.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor
@Getter @Setter
public class Member {

    public Member(String id, String password, String name, int age, Address address, LocalDate birthDate, MemberType memberType) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.age = age;
        this.address = address;
        this.memberType = memberType;
    }

    @Id
    @Column(name="member_id")
    private String id;

    private String password;
    private String name;
    private int age;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    private LocalDate birthDate;


    @Embedded
    private Address address;

    public void updateInfo(Member member) {

        if(!name.equals(member.getName())) {
            System.out.println("1");
            name = member.getName();
        }

        if(!password.equals(member.getPassword())) {
            System.out.println("2");
            password = member.getPassword();
        }

        if(!address.equals(member.getAddress())) {
            System.out.println(3);
            address = member.getAddress();
        }

    }
}
