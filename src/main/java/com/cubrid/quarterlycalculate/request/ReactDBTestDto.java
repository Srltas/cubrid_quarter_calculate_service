package com.cubrid.quarterlycalculate.request;

import org.springframework.web.bind.annotation.RestController;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReactDBTestDto {
    private String name;
    private String year;
    
    public ReactDBTestDto() {}

    @Builder
    public ReactDBTestDto(String name, String year) {
        this.name = name;
        this.year = year;
    }

}
