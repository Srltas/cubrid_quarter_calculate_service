package com.cubrid.quarterlycalculate.model;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@RequiredArgsConstructor
@Getter
@Setter
public class TeamManagementData {

    private final String name;

    private final Date first_day_of_work;

    private final Date last_day_of_work;

}
