package com.orion.collector.service;

import com.orion.adapter.avro.ACall;
import com.orion.adapter.avro.ACallId;
import lombok.RequiredArgsConstructor;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SpecificRecordConsumerHandler implements ConsumerHandler<SpecificRecord> {

    private final RCallService rCallService;

    @Override
    public void handle(SpecificRecord aCall) {
        if (aCall instanceof ACall call) {
            rCallService.saveRCall(call);
        }

        if (aCall instanceof ACallId callId) {
            rCallService.deleteCall(callId.getId().toString());
        }
    }
}
