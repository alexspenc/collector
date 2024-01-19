package com.orion.collector.service;

import com.orion.adapter.avro.ACall;
import com.orion.collector.exceptions.NotFoundException;
import com.orion.collector.model.RCall;
import com.orion.collector.model.RParticipant;
import com.orion.collector.repository.RedisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RCallService {

    private final RedisRepository repository;

    public void saveRCall(ACall call) {
        log.info("Save call: {}", call.toString());
        RCall rCall = RCall.builder()
            .id(call.getId().toString())
            .calledNumber(call.getCalledNumber().toString())
            .callerNumber(call.getCallerNumber().toString())
            .engagementDialogId(call.getEngagementDialogId().toString())
            .participants(new RParticipant(call.getParticipant().getId().toString(), call.getParticipant().getName().toString()))
            .timestamp(call.getTimestamp().toString())
            .build();
        repository.saveCall(rCall);
    }

    public void deleteCall(String id) {
        repository.deleteCall(id);
        log.info("Deleted call with ID: {}", id);
    }

    public RCall getCallById(String id) {
        RCall callById = repository.getCallById(id);
        if (callById == null) {
            throw new NotFoundException("Not found RCall with id: " + id);
        }
        return callById;
    }
}
