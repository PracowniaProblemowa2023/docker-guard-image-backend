package pl.dockerguardimage.core.config.quartz;

import lombok.AllArgsConstructor;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import pl.dockerguardimage.core.config.quartz.job.OsvJob;
import pl.dockerguardimage.core.functionality.common.properties.JobProperties;

@AllArgsConstructor
@Configuration
public class QuartzSubmitJobs {

    private final JobProperties jobProperties;

    @Bean
    public JobDetailFactoryBean osvExecution() {
        return SchedulerJobCreator.createJob(OsvJob.class, "osvJob");
    }

    @Bean
    public CronTriggerFactoryBean osvExecutionTrigger(@Qualifier("osvExecution") JobDetail jobDetail) {
        return SchedulerJobCreator.createCronTrigger(jobDetail, "osvExecutionTrigger", jobProperties.getOsv().getCron());
    }

}
