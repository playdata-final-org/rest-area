package com.example.BAS.config.quartz;

import lombok.RequiredArgsConstructor;
import org.quartz.JobDetail;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.*;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuartzConfig {
    @Bean
    public JobDetailFactoryBean dailyQuartzJobDetail() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(DailyQuartzJob.class);
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }

    @Bean
    public JobDetailFactoryBean monthlyQuartzJobDetail() {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(MonthlyQuartzJob.class);
        jobDetailFactoryBean.setDurability(true);
        return jobDetailFactoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean dailyTrigger(JobDetail dailyQuartzJobDetail) {
        SimpleTriggerFactoryBean triggerFactoryBean = new SimpleTriggerFactoryBean();
        triggerFactoryBean.setJobDetail(dailyQuartzJobDetail);
//        triggerFactoryBean.setRepeatInterval(24 * 60 * 60 * 1000);
        triggerFactoryBean.setRepeatInterval(10000);
        triggerFactoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        triggerFactoryBean.setStartDelay(0);
        return triggerFactoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean monthlyTrigger(JobDetail monthlyQuartzJobDetail) {
        SimpleTriggerFactoryBean triggerFactoryBean = new SimpleTriggerFactoryBean();
        triggerFactoryBean.setJobDetail(monthlyQuartzJobDetail);
//        triggerFactoryBean.setRepeatInterval(30L * 24 * 60 * 60 * 1000);
        triggerFactoryBean.setRepeatInterval(30000);
        triggerFactoryBean.setRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY);
        triggerFactoryBean.setStartDelay(0);
        return triggerFactoryBean;
    }
    @Bean
    public SchedulerFactoryBean scheduler(Trigger dailyTrigger, Trigger monthlyTrigger) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setTriggers(dailyTrigger, monthlyTrigger);
        schedulerFactoryBean.setSchedulerName("PaymentScheduler");

        AdaptableJobFactory jobFactory = new SpringBeanJobFactory();
        schedulerFactoryBean.setJobFactory(jobFactory);

        return schedulerFactoryBean;
    }
}
