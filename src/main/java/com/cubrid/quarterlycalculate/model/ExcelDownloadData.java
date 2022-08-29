package com.cubrid.quarterlycalculate.model;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Builder
@RequiredArgsConstructor
@Getter
@Setter
public class ExcelDownloadData {

    private final String name;

    private final String year;

    private final String quarter;
    
    private final int compensationLeaveTime;

}
