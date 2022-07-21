package com.cubrid.quarterlycalculate.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class User {

    private final Long seq;

    private final String name;

    private final LocalDate firstDayOfWork;

    @Builder
    public User(Long seq, String name, LocalDate firstDayOfWork) {
        this.seq = seq;
        this.name = name;
        this.firstDayOfWork = firstDayOfWork;
    }
}
