package com.yoo.lms.domain;

import com.yoo.lms.domain.enumType.AcceptType;
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

    public Teacher(String id, String password, String name, String email, Address address, LocalDate birthDate, MemberType memberType) {
        super(id, password, name, email, address, birthDate, memberType);
        this.acceptType = AcceptType.REJECTED;
    }

    public Teacher(Member member) {
        super(member.getId(),
                member.getPassword(),
                member.getName(),
                member.getEmail(),
                member.getAddress(),
                member.getBirthDate(),
                member.getMemberType()
        );
        this.acceptType = AcceptType.REJECTED;
    }

    @Enumerated(EnumType.STRING)
    private AcceptType acceptType;

    @OneToMany(mappedBy = "teacher")
    private List<Course> courses = new ArrayList<>();


    public void changeAcceptToAccepted() {
        this.acceptType = AcceptType.ACCEPTED;
    }

    public void changeAcceptToReject(){
        this.acceptType = AcceptType.REJECTED;
    }

    public void changeAcceptToWaiting(){
        this.acceptType = AcceptType.WAITING;
    }

}
