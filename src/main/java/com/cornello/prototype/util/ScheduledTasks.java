package com.cornello.prototype.util;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;

@Component
@Slf4j
public class ScheduledTasks {
    
    private ScheduledTasks() {
        log.info("Starting ScheduledTasks()...");
    }

    @Scheduled(fixedRate = 1000*60*15)
    private static void scheduleTaskWithFixedRate() {
        Utility.memoizeTable = new HashMap<>();
        log.info("Fixed Rate Task :: Execution Time - {}", LocalDateTime.now() );
        log.info(" this.memoizeTable:{}", Utility.memoizeTable);
    }
}
