package com.orion.collector.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;

@REntity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RCall {
    @RId
    private String id;
    private String callerNumber;
    private String calledNumber;
    private String engagementDialogId;
    private String timestamp;
    private RParticipant participants;
}
