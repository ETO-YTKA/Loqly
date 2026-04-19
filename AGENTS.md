# Loqly Agent Notes

## Project Overview

Loqly is a native android application written in Kotlin. it will become a messaging app that allows users to send and receive messages in real-time.

## Architecture

This project is a modern Android application that follows the official architecture guidance from Google. It is a reactive, single-activity app that uses the following:

-   **UI:** Built entirely with Jetpack Compose, including Material 3 components and adaptive layouts for different screen sizes.
-   **State Management:** Unidirectional Data Flow (UDF) is implemented using Kotlin Coroutines and `Flow`s. `ViewModel`s act as state holders, exposing UI state as streams of data.
-   **Dependency Injection:** Hilt is used for dependency injection throughout the app, simplifying the management of dependencies and improving testability.
-   **Navigation:** Navigation is handled by Jetpack Navigation 2 for Compose, allowing for a declarative and type-safe way to navigate between screens.
-   **Data:** The data layer is implemented using the repository pattern.
    -   **Local Data:** Room and DataStore are used for local data persistence.
    -   **Remote Data:** Retrofit and OkHttp are used for fetching data from the network.

## Project Shape
- Build logic uses Gradle Kotlin DSL plus the version catalog in `gradle/libs.versions.toml`.

## Design Context
- Project-level UI and brand direction lives in `.impeccable.md` at the repo root.
- Read `.impeccable.md` before doing design or frontend-facing work so changes stay aligned with the product's intended audience, tone, and aesthetic direction.

## Stack And Constraints
- Kotlin, AGP, Material 3, Navigation Compose, Kotlin serialization, Hilt, KSP, Coil 3, Timber.
- Hilt uses KSP, not KAPT. If you add DI code, keep compiler wiring on `ksp(...)`.
- Release builds are minified and shrink resources; check `:app:assembleRelease` if your change could affect ProGuard/resource shrinking.

## Commands
- Windows shell here should use `./gradlew.bat` from repo root.
- Build a debug APK: `./gradlew.bat :app:assembleDebug`
- Run host/unit tests: `./gradlew.bat :app:testDebugUnitTest`
- Run one host test: `./gradlew.bat :app:testDebugUnitTest --tests "com.example.loqly.ExampleUnitTest.addition_isCorrect"`
