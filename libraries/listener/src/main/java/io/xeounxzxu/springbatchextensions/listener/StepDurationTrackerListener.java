package io.xeounxzxu.springbatchextensions.listener;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/** Logs step execution metrics and returns a custom exit status when skips occur. */
public class StepDurationTrackerListener implements StepExecutionListener {

  private static final Logger log = LoggerFactory.getLogger(StepDurationTrackerListener.class);

  @Override
  public void beforeStep(StepExecution stepExecution) {
    log.info(
        "Step '{}' of job '{}' started at {}",
        stepExecution.getStepName(),
        stepExecution.getJobExecution().getJobInstance().getJobName(),
        stepExecution.getStartTime());
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    Optional<Duration> duration =
        resolveDuration(stepExecution.getStartTime(), stepExecution.getEndTime());
    String formattedDuration = duration.map(DurationFormatter::format).orElse("unknown");

    log.info(
        "Step '{}' ended with status {} in {}. read={}, write={}, readSkips={}, processSkips={}, writeSkips={}, commitCount={}",
        stepExecution.getStepName(),
        stepExecution.getStatus(),
        formattedDuration,
        stepExecution.getReadCount(),
        stepExecution.getWriteCount(),
        stepExecution.getReadSkipCount(),
        stepExecution.getProcessSkipCount(),
        stepExecution.getWriteSkipCount(),
        stepExecution.getCommitCount());

    if (duration.isEmpty()) {
      log.warn("Start or end time was not recorded for step '{}'.", stepExecution.getStepName());
    }

    if (stepExecution.getSkipCount() > 0) {
      log.warn(
          "Step '{}' completed with {} skipped items.",
          stepExecution.getStepName(),
          stepExecution.getSkipCount());
      return new ExitStatus("COMPLETED_WITH_SKIPS");
    }
    return stepExecution.getExitStatus();
  }

  private Optional<Duration> resolveDuration(LocalDateTime start, LocalDateTime end) {
    if (start == null || end == null) {
      return Optional.empty();
    }
    return Optional.of(Duration.between(start, end));
  }
}
