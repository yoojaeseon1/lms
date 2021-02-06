package com.yoo.lms.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class AttendanceStateDto {

    private String studentId;
    private String attendState;

    public AttendanceStateDto(String studentId, String attendState) {
        this.studentId = studentId;
        this.attendState = attendState;
    }
}
