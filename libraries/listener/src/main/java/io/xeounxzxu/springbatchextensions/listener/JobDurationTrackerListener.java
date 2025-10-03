package io.xeounxzxu.springbatchextensions.listener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobInstance;

/**
 * Logs job lifecycle events alongside execution durations.
 */
public class JobDurationTrackerListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(JobDurationTrackerListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        JobInstance jobInstance = jobExecution.getJobInstance();
        log.info("Job '{}' started at {}", jobInstance.getJobName(), jobExecution.getStartTime());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        LocalDateTime start = jobExecution.getStartTime();
        LocalDateTime end = jobExecution.getEndTime();
        Optional<Duration> duration = resolveDuration(start, end);

        if (duration.isPresent()) {
            log.info(
                    "Job '{}' finished with status {} after {} (ended at {})",
                    jobExecution.getJobInstance().getJobName(),
                    jobExecution.getStatus(),
                    DurationFormatter.format(duration.get()),
                    end
            );
        } else {
            log.warn(
                    "Job '{}' finished with status {} but start or end time was not available",
                    jobExecution.getJobInstance().getJobName(),
                    jobExecution.getStatus()
            );
        }

        if (jobExecution.getStatus().isUnsuccessful() && !jobExecution.getAllFailureExceptions().isEmpty()) {
            log.error("Job '{}' failed with {} exceptions", jobExecution.getJobInstance().getJobName(), jobExecution.getAllFailureExceptions().size());
            jobExecution.getAllFailureExceptions()
                    .forEach(throwable -> log.error("Job failure cause", throwable));
        }
    }

    private Optional<Duration> resolveDuration(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) {
            return Optional.empty();
        }
        return Optional.of(Duration.between(start, end));
    }
}
