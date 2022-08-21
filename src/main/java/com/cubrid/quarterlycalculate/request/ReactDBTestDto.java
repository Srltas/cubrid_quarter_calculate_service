package com.cubrid.quarterlycalculate.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReactDBTestDto {
    private String name;
    private String year;

    @Builder
    public ReactDBTestDto(String name, String year) {
        this.name = name;
        this.year = year;
    }
}
