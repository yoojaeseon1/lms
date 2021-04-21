package com.yoo.lms.domain;

import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.domain.valueType.Address;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@NoArgsConstructor
@Getter
public class Member {


    @Id
    @Column(name="member_id")
    private String id;

    private String password;
    private String name;
    private int age;

    private String email;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Embedded
    private Address address;


    public Member(String id, String password, String name, String email, Address address, LocalDate birthDate, MemberType memberType) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
        this.birthDate = birthDate;
        this.memberType = memberType;
        this.age = LocalDate.now().getYear() - birthDate.getYear() + 1;
    }

    public void updateInfo(Member member) {

        if(!password.equals(member.getPassword())) {
            password = member.getPassword();
        }

        if(!email.equals(member.getEmail()))
            email = member.getEmail();

        if(!address.equals(member.getAddress())) {
            address = member.getAddress();
        }

    }

    public void initMemberType(MemberType memberType) {
        this.memberType = memberType;
    }

    public void initPassword(String password) {
        this.password = password;
    }

}
