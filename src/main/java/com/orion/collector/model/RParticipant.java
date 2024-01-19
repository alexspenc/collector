package com.orion.collector.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;

@REntity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RParticipant {
    @RId
    private String id;
    private String name;
}
