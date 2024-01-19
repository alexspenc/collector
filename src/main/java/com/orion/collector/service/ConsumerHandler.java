package com.orion.collector.service;

public interface ConsumerHandler<T> {
    void handle(T o);
}
