package com.yoo.lms.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 요청으로 받은 AttendanceTypeDto를 리스트로 한 번에 바인딩하기 위한 용도
 */

@Getter @Setter
public class AttendanceTypeListDto {

    private List<AttendanceTypeDto> states;

    public AttendanceTypeListDto(List<AttendanceTypeDto> states) {
        this.states = states;
    }
}
