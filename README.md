# spring-batch-extensions

## English
A multi-module Gradle project showcasing reusable Spring Batch components.

### Code Formatting
This repository uses [Spotless](https://github.com/diffplug/spotless) with Google Java Format for every Java source.

#### Requirements
- Install JDK 17 or higher (available on PATH or via Gradle toolchains).

#### Commands
- Apply formatting to all modules:
  ```bash
  ./gradlew spotlessApply
  ```
- Verify formatting without modifying files:
  ```bash
  ./gradlew spotlessCheck
  ```
- Target a specific module:
  ```bash
  ./gradlew :libraries:listener:spotlessApply
  ```

## 한국어
재사용 가능한 Spring Batch 구성요소를 담은 멀티 모듈 Gradle 프로젝트입니다.

### 코드 포맷팅
이 저장소는 모든 Java 소스에 대해 [Spotless](https://github.com/diffplug/spotless)와 Google Java Format을 사용합니다.

#### 준비 사항
- JDK 17 이상을 설치하고 PATH 또는 Gradle toolchain으로 설정합니다.

#### 실행 명령
- 전체 모듈에 포맷 적용:
  ```bash
  ./gradlew spotlessApply
  ```
- 파일을 수정하지 않고 포맷 여부만 검증:
  ```bash
  ./gradlew spotlessCheck
  ```
- 특정 모듈만 실행:
  ```bash
  ./gradlew :libraries:listener:spotlessApply
  ```
