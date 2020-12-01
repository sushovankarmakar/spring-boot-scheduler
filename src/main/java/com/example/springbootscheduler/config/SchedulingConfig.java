package com.example.springbootscheduler.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/* https://www.youtube.com/watch?v=92-qLIxv0JA&ab_channel=SpringAcademy */
/* https://www.youtube.com/watch?v=ZXlxQ3z4zDE&t=90s&ab_channel=DailyCodeBuffer */
/* https://www.youtube.com/watch?v=uxV-3947fiM&ab_channel=JavaTechie */

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduling.enabled", matchIfMissing = true)  /* we can disable this scheduling on property */
public class SchedulingConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(SchedulingConfig.class);

    @Scheduled(fixedRate = 2000L)   // first invocation is done when the spring context is up
    void fixedRateExample() throws InterruptedException {
        LOGGER.info("This is a fixedRate example. Now is " + new Date());
        Thread.sleep(1000L);
        //  making this method to sleep 1 second but still this method will be called cause, fixedRate doesn't
        //  consider the execution time of the method
    }

    @Scheduled(initialDelay = 3000L, fixedDelay = 2000L)    // first invocation is done after 3 seconds of spring context is up
    void fixedDelayExample() throws InterruptedException {
        LOGGER.info("This is a fixedDelay example. Now is " + new Date());
        Thread.sleep(1000L);
        // making this method to sleep 1 second, now this method will be called per (2 + 1 =) 3 seconds
    }

    // PT3S = Period Time 3 seconds, PT3M (3 minutes), PT3H (3 hours)
    @Scheduled(initialDelay = 3000L, fixedDelayString = "${fixedDelayString.delay}")    // schedule job will run at 10 seconds
    void fixedDelayStringExample() throws InterruptedException {
        LOGGER.info("This is a fixedDelayString example. Now is " + new Date());
    }

    // https://crontab.guru/
    // cron.time = */5 * * * * * <- it means every 5 seconds
    @Scheduled(/*initialDelay = 1000L,*/ cron = "${cron.time}")     // 'initialDelay' not supported for cron triggers
    void cronExpressionExample() throws InterruptedException {
        LOGGER.info("This is a cron expression example. Now is " + new Date());
    }

    /* all the schedule jobs by default runs in a single thread
    * but from spring boot 2.1, we can schedule what will be the thread pool dedicated to scheduling
    * spring.task.scheduling.pool.size = 1 (default) */

    @Scheduled(fixedRate = 1000L)
    void fixedRateExampleWithMultithreading1() throws InterruptedException {
        LOGGER.info("Example 1 " + new Date());
        Thread.sleep(1000L);
    }

    @Scheduled(fixedRate = 1000L)
    void fixedRateExampleWithMultithreading2() throws InterruptedException {
        LOGGER.info("Example 2 " + new Date());
    }
}
