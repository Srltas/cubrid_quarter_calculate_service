package com.cubrid.quarterlycalculate.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalDataDto {

    private String name;

    private String year;

    private String quarter;

    @Builder
    public TotalDataDto(String name, String year, String quarter) {
        this.name = name;
        this.year = year;
        this.quarter = quarter;
    }
}
