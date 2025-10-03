# Listener Library

## English

This module provides reusable Spring Batch listeners for monitoring job metrics.

- `JobDurationTrackerListener` logs job lifecycle events and the total execution duration.
- `StepDurationTrackerListener` records step statistics and flags skipped items with a custom exit status.
- `ChunkDurationTrackerListener` measures processing time per chunk and reports failures.

Include the module in your Gradle project:

```groovy
dependencies {
    implementation "com.github.your-org:spring-batch-extensions-listener:<version>"
}
```

## 한국어

이 모듈은 Spring Batch 작업의 메트릭을 모니터링하는 재사용 가능한 리스너를 제공합니다.

- `JobDurationTrackerListener`는 잡의 시작/종료 로그와 전체 실행 시간을 기록합니다.
- `StepDurationTrackerListener`는 스텝 통계를 남기고 스킵이 발생하면 커스텀 ExitStatus를 반환합니다.
- `ChunkDurationTrackerListener`는 청크 단위 처리 시간을 측정하고 오류를 보고합니다.

Gradle 프로젝트에 모듈을 추가하려면 아래와 같이 의존성을 선언하세요.

```groovy
dependencies {
    implementation "com.github.your-org:spring-batch-extensions-listener:<버전>"
}
```
