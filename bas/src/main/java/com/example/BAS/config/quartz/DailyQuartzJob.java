package com.example.BAS.config.quartz;

import com.example.BAS.service.subscribe.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DailyQuartzJob implements Job {

    private final SubscribeService subscribeService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        subscribeService.dailySchedulerService();
    }
}
