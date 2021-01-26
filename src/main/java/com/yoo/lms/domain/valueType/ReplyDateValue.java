package com.yoo.lms.domain.valueType;

import com.yoo.lms.domain.Member;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Embeddable
@Getter
public class ReplyDateValue {

    @CreatedDate
    private LocalDateTime contentCreatedDate;
    private LocalDateTime contentLastModifiedDate;

    private LocalDateTime replyCreatedDate;
    private LocalDateTime replyLastModifiedDate;





}
