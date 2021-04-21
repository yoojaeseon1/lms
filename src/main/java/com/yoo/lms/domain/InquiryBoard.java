package com.yoo.lms.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@DiscriminatorValue("I")
@Getter @Setter
@EntityListeners(AuditingEntityListener.class)
public class InquiryBoard extends Board{


    @OneToOne(fetch = FetchType.LAZY, mappedBy = "inquiryBoard")
    private BoardReply reply;


    public InquiryBoard(String title, String content, Member createdBy) {
        super(title, content, createdBy);
    }

}
