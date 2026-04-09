
![Publishing](https://github.com/coderwise/core-mobile/actions/workflows/publish.yaml/badge.svg)

# Core Mobile Library

Core Mobile is a comprehensive library designed to streamline the development of mobile applications by providing a robust set of essential modules and utilities.

## Modules

The library is organized into several key modules, each focusing on a specific domain of mobile app development:

- **core-ui**: Standardized UI components, themes, and navigation patterns for consistent app appearance and behavior.
- **core-domain**: Base classes and interfaces for business logic, including use cases and domain models.
- **core-data**: Data access layer utilities, including repository patterns and local/remote data source abstractions.
- **core-auth**: A complete authentication suite including UI, domain logic, data handling, and server-side components.
- **core-permissions**: Simplified API for handling runtime permissions across platforms.
- **core-location**: Utilities for accessing and managing device location services.
- **core-time**: Common time and date manipulation utilities.
- **core-versioning**: Tools for managing application and API versioning.

## Getting Started

### Installation

Add the following to your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
```

Then, include the desired modules in your `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.coderwise.core:core-ui:x.y.z")
    implementation("com.coderwise.core:core-domain:x.y.z")
    // ... add other modules as needed
}
```

## Project Structure

The project is structured as a Kotlin Multiplatform (KMP) repository, supporting Android and iOS targets. It also includes a `sampleApp` to demonstrate the usage of the library components.

- `core-*`: Core library modules.
- `sampleApp`: Demonstration application showcasing library features.
    - `composeApp`: Shared UI and logic for the sample application.
    - `server`: Ktor-based server for testing backend interactions.
    - `server-api`: Common API definitions shared between the app and the server.

## License

This project is licensed under the Apache License, Version 2.0. See the [LICENSE](LICENSE) file for details.
