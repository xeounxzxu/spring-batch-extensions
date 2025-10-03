package io.xeounxzxu.springbatchextensions.listener;

import java.time.Duration;
import java.util.Objects;

/** Utility helpers for rendering execution durations in a human friendly form. */
final class DurationFormatter {

  private static final long MILLIS_PER_SECOND = 1000L;
  private static final long MILLIS_PER_MINUTE = MILLIS_PER_SECOND * 60;
  private static final long MILLIS_PER_HOUR = MILLIS_PER_MINUTE * 60;

  private DurationFormatter() {}

  static String format(Duration duration) {
    Duration safe = Objects.requireNonNullElse(duration, Duration.ZERO);
    long millis = Math.abs(safe.toMillis());
    long hours = millis / MILLIS_PER_HOUR;
    long minutes = (millis % MILLIS_PER_HOUR) / MILLIS_PER_MINUTE;
    long seconds = (millis % MILLIS_PER_MINUTE) / MILLIS_PER_SECOND;
    long remainingMillis = millis % MILLIS_PER_SECOND;

    if (hours > 0) {
      return String.format("%dh %dm %ds", hours, minutes, seconds);
    }
    if (minutes > 0) {
      if (seconds > 0) {
        return String.format("%dm %ds", minutes, seconds);
      }
      return String.format("%dm", minutes);
    }
    if (seconds > 0) {
      if (remainingMillis > 0) {
        return String.format("%d.%03ds", seconds, remainingMillis);
      }
      return String.format("%ds", seconds);
    }
    return String.format("%dms", remainingMillis);
  }
}
