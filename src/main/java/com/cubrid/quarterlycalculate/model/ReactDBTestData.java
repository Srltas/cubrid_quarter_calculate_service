package com.cubrid.quarterlycalculate.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@Getter
public class ReactDBTestData {
	
	private final String id;
	private final String passwd;
	private final String department;
	private final String name;
	private final String role;
	private final String employmentstatus;
	private final String year;
	private final String quarter;
}
