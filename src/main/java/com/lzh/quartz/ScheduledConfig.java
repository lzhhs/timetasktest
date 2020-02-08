package com.lzh.quartz;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Lazy(false)
@Component
@EnableScheduling
@Configuration
public class ScheduledConfig implements SchedulingConfigurer {


    private static Logger logger = Logger.getLogger(ScheduledConfig.class);
    /**
     * 动态定时器默认时间为5秒钟
     */
    private static String cron = "0/10 * * * * ?";

    public static void setCron(String cron) {
        ScheduledConfig.cron = cron;
    }

    /**
     * Spring的定时任务调度器会尝试获取一个注册过的task scheduler来做任务调度，
     * 它会尝试通过BeanFactory.getBean的方法来获取一个注册过的scheduler bean，
     * 获取的步骤如下：
     * 1.尝试从配置中找到一个TaskScheduler Bean
     * 2.寻找ScheduledExecutorService Bean
     * 3.使用默认的scheduler
     * 前两步，如果找不到的话，就会以debug的方式抛出异常
     *
     * @return TaskScheduler
     */
    @Bean
    public TaskScheduler scheduledExecutorService() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(8);
        scheduler.setThreadNamePrefix("scheduled-thread-");
        return scheduler;
    }

    /**
     * 启动时执行一次，之后每天执行一次
     * cron:表达式控制
     */
//    @Scheduled(cron = "0 0 0 * * ?")
//    public void print() {
//        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        logger.info(" == 任务每天执行一次 == " + sdf.format(new Date()));
//    }

    /**
     * 启动时执行一次，之后每隔1s执行一次
     */
//    @Scheduled(fixedRate = 1000)
//    public void heartbeat() {
//        System.out.println("执行... " + new Date());
//    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(new Runnable() {
            /**
             * Thread current = Thread.currentThread();
             * logger.info("ScheduledTest.executeFileDownLoadTask 定时任务:" + current.getId() + ",name:" + current.getName());
             */
            @Override
            public void run() {
                //获取缓冲中的时间

                System.out.println("cron : " + cron);
                logger.info("cron : " + cron);
            }
        }, new Trigger() {
            /**
             *
             * @param triggerContext
             * @return
             */
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                // 任务触发，可修改任务的执行周期
                CronTrigger trigger = new CronTrigger(cron);
                Date nextExecutor = trigger.nextExecutionTime(triggerContext);
                return nextExecutor;
            }
        });
    }
}


