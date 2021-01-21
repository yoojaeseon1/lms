package com.yoo.lms.domain;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("C")
public class Counseler extends Member{

    @OneToMany(mappedBy="counseler")
    private List<CounselBoard> counselBoards = new ArrayList<>();

}
