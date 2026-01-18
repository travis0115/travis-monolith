package com.travis.monolith.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@ToString
class Test {
    @Getter
    @Setter
    private LocalDateTime d2;

    @Getter
    @Setter
    private Date d1;

    public Test() {
        this.d1 = new Date();
        this.d2 = LocalDateTime.now();
    }
}
