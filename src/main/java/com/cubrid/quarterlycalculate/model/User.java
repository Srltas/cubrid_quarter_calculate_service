package com.cubrid.quarterlycalculate.model;

import lombok.Builder;
import lombok.Getter;

@Getter
public class User {

    private final Long seq;
    private final String id;

    @Builder
    public User(Long seq, String id) {
        this.seq = seq;
        this.id = id;
    }
}
