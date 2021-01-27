package com.yoo.lms.domain;

import com.yoo.lms.domain.enumType.MemberType;
import com.yoo.lms.domain.valueType.Address;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("C")
@NoArgsConstructor
public class Counseler extends Member{

    public Counseler(String id, String password, String name, int age, Address address, LocalDate birthDate, MemberType memberType) {
        super(id, password, name, age, address, birthDate, memberType);
    }

    @OneToMany(mappedBy="replyCreatedBy")
    private List<CounselBoard> counselBoards = new ArrayList<>();

}
