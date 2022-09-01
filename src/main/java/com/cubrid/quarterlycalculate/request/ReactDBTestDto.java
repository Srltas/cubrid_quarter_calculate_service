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
    private String id;
	private String passwd;
    
    public ReactDBTestDto() {}

    @Builder
    public ReactDBTestDto(String name, String year, String id, String passwd) {
        this.name = name;
        this.year = year;
        this.id = id;
        this.passwd = passwd;
    }

}
