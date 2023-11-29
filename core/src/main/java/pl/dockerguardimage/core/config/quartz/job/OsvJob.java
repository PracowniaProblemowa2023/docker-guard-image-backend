package pl.dockerguardimage.core.config.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.dockerguardimage.core.functionality.common.properties.JobProperties;
import pl.dockerguardimage.core.functionality.packagethreat.PackageThreatService;

@Slf4j
@Component
@DisallowConcurrentExecution
public class OsvJob implements Job {

    @Autowired
    private PackageThreatService packageThreatService;

    @Autowired
    private JobProperties jobProperties;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if (jobProperties.getOsv().isEnabled()) {
            log.debug("Job works");
        }
        packageThreatService.executeImageScanInProgressJob();
    }
}
