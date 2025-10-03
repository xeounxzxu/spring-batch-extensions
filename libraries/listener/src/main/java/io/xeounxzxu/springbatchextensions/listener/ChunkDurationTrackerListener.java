package io.xeounxzxu.springbatchextensions.listener;

import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * Measures how long each chunk takes to process and logs the result.
 */
public class ChunkDurationTrackerListener implements ChunkListener {

    private static final Logger log = LoggerFactory.getLogger(ChunkDurationTrackerListener.class);
    private static final String START_TIME_ATTRIBUTE = "chunkDurationTrackerListener.startTime";

    @Override
    public void beforeChunk(ChunkContext context) {
        context.setAttribute(START_TIME_ATTRIBUTE, System.currentTimeMillis());
    }

    @Override
    public void afterChunk(ChunkContext context) {
        Long startTime = (Long) context.getAttribute(START_TIME_ATTRIBUTE);
        if (startTime == null) {
            log.warn("Chunk #{} finished but start time was missing.", currentChunkNumber(context));
            return;
        }

        long durationMillis = System.currentTimeMillis() - startTime;
        log.info(
                "Chunk #{} finished in {}",
                currentChunkNumber(context),
                DurationFormatter.format(Duration.ofMillis(durationMillis))
        );
    }

    @Override
    public void afterChunkError(ChunkContext context) {
        log.error("Chunk #{} failed.", currentChunkNumber(context));
    }

    private long currentChunkNumber(ChunkContext context) {
        return context.getStepContext().getStepExecution().getCommitCount();
    }
}
