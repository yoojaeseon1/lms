package com.yoo.lms.domain.valueType;

import lombok.Getter;

import javax.persistence.Embeddable;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Embeddable
@Getter
public class ReplyDateValue {

    private String contentCreatedBy;
    private LocalDateTime contentCreatedDate;
    private String contentLastModifiedBy;
    private LocalDateTime contentLastModifiedDate;

    private String replyCreatedBy;
    private LocalDateTime replyCreatedDate;
    private String replyLastModifiedBy;
    private LocalDateTime replyLastModifiedDate;


}
