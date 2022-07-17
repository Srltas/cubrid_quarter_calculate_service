package com.cubrid.quarterlycalculate.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class QuarterWorkTime {

    private final String quarter;

    private final int totalWorkTime;

    private final int legalWorkTime;
}
