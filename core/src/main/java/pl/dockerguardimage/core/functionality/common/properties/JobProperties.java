package pl.dockerguardimage.core.functionality.common.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "jobs")
public class JobProperties {

    private boolean enabled;
    private JobParameters osv = new JobParameters();

    @Data
    @NoArgsConstructor
    public class JobParameters {
        private String cron;
        private boolean enabled;

        public boolean isTaskEnabled() {
            return JobProperties.this.isEnabled() && isEnabled();
        }

    }

}
