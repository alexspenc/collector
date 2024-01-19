package com.orion.collector.controller;

import com.orion.collector.model.RCall;
import com.orion.collector.service.RCallService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("calls")
@RestController
@RequiredArgsConstructor
@Slf4j
public class RCallController {

    private final RCallService service;

    @GetMapping("/{id}")
    public RCall getCallById(@PathVariable String id) {
        RCall rCall = service.getCallById(id);
        log.info("Rcall: {}", rCall);
        return rCall;
    }

}
