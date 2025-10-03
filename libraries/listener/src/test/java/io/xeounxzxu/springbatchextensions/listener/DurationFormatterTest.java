package io.xeounxzxu.springbatchextensions.listener;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import org.junit.jupiter.api.Test;

class DurationFormatterTest {

    @Test
    void formatsHoursMinutesAndSeconds() {
        String result = DurationFormatter.format(Duration.ofHours(2).plusMinutes(3).plusSeconds(4));
        assertThat(result).isEqualTo("2h 3m 4s");
    }

    @Test
    void usesMinutesAndSecondsWhenNoHours() {
        String result = DurationFormatter.format(Duration.ofMinutes(5).plusSeconds(12));
        assertThat(result).isEqualTo("5m 12s");
    }

    @Test
    void includesMillisWhenUnderASecond() {
        String result = DurationFormatter.format(Duration.ofMillis(345));
        assertThat(result).isEqualTo("345ms");
    }

    @Test
    void handlesNullDuration() {
        String result = DurationFormatter.format(null);
        assertThat(result).isEqualTo("0ms");
    }
}
