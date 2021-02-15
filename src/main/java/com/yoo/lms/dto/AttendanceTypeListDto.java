package com.yoo.lms.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AttendanceTypeListDto {

    private List<AttendanceTypeDto> states;

    public AttendanceTypeListDto(List<AttendanceTypeDto> states) {
        this.states = states;
    }
}
