package com.orion.collector.repository;

import com.orion.collector.model.RCall;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLiveObjectService;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisRepository {

    private final RedissonClient redissonClient;

    public void saveCall(RCall rCall) {
        getRLiveObjectService().persist(rCall);
    }

    public void deleteCall(String id) {
        getRLiveObjectService().delete(RCall.class, id);
    }

    public RCall getCallById(String id) {
        return getRLiveObjectService().get(RCall.class, id);
    }

    private RLiveObjectService getRLiveObjectService() {
        return redissonClient.getLiveObjectService();
    }
}
