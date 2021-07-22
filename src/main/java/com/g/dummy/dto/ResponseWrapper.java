package com.g.dummy.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

@Data
public class ResponseWrapper<T> {

    public ResponseWrapper(T data) {
        this.responseId = UUID.randomUUID();
        this.instantUtc = Instant.now(Clock.systemUTC());
        this.data = data;
    }

    private T data;

    private UUID responseId;

    private Instant instantUtc;
}
