package com.cubrid.quarterlycalculate.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@RequiredArgsConstructor
@Getter
@Setter
public class QuarterWorkTime {

    private final Long seq;

    private final String name;

    private final String year;

    private final String quarter;

    private final int quarterTotalTime;

    private final int quarterLegalTime;
    
    private final int quarterWorkTime;
    
    private final int regulationWorkOverTime;

    private final int legalWorkOverTime;

    private final int nightWorkTime;

    private final int holidayWorkTime;
    
    private final int holiday8HOver;

    private final int leaveTime;
    
    private final int compensationLeaveTime;

    private final int calculateMoney;

    private final int calculateTotal;

}
