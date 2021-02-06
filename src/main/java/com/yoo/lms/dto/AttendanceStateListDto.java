package com.yoo.lms.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class AttendanceStateListDto {

    private List<AttendanceStateDto> states;

    public AttendanceStateListDto(List<AttendanceStateDto> states) {
        this.states = states;
    }
}
