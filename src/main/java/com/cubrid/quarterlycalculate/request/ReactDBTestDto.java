package com.cubrid.quarterlycalculate.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReactDBTestDto {
    private String name;

    @Builder
    public ReactDBTestDto(String name) {
        this.name = name;
    }
}
