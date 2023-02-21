# KinoGoClean
"A movie and TV show information app for teaching MVVM, Paging3, Flow-Coroutines with Clean
Architecture

With this app, you can easily find information about your favorite films and TV series.
You can view the cast, plot summary, release date, and more. "

<div style="text-align: center">

[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

</div>

## Screeshots

### Light Version

***
<div>
<img src="Screenshoots/home_light_turkish.png" width="180" height="360" float:left>
<img src="Screenshoots/home_see_all_light_turkish.png" width="180" height="360" float:left>
<img src="Screenshoots/home_detail_light_turkish.png" width="180" height="360" float:left>
<img src="Screenshoots/detail_light_turkish.png" width="180" height="360" float:left>
<img src="Screenshoots/detail_2_light_turkish.png" width="180" height="360" float:left>
<img src="Screenshoots/settings_light_turkish.png" width="180" height="360" float:left>
</div>

### Dark Version

***
<div>
<img src="Screenshoots/dark/home_dark_english.png" width="180" height="360" float:left>
<img src="Screenshoots/dark/home_see_all_dark_english.png" width="180" height="360" float:left>
<img src="Screenshoots/dark/home_see_all_2_dark_english.png" width="180" height="360" float:left>
<img src="Screenshoots/dark/home_detail_dark_english.png" width="180" height="360" float:left>
<img src="Screenshoots/dark/detail_dark_english.png" width="180" height="360" float:left>
<img src="Screenshoots/dark/detail_2_dark_english.png" width="180" height="360" float:left>
<img src="Screenshoots/dark/setting_dark_english.png" width="180" height="360" float:left>
</div>

## Tech stack & Open-source libraries

- Minimum SDK level 24+
- 100% [Kotlin](https://kotlinlang.org/)
  based [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) [Flow](https://developer.android.com/kotlin/flow)
  for asynchronous.

- [Android Architecture Components](https://developer.android.com/topic/architecture) - Collection
  of libraries that help you design robust, testable, and maintainable apps.
    - A single-activity architecture, using
      the [Navigation](https://developer.android.com/guide/navigation) to manage composable
      transactions.
    - [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform an
      action when lifecycle state change
    - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores
      UI-related data that isn't destroyed on UI changes.
    - [UseCases](https://developer.android.com/topic/architecture/domain-layer) - Located domain
      layer
      that sits between the UI layer and the data layer.
    - [Repository](https://developer.android.com/topic/architecture/data-layer) - Located in data
      layer that contains application data and business logic

- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) -Dependency
  Injection Library
- [Paging](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) - The
  Paging library makes it easier for you to load data incrementally and gracefully within your app's
  UI
- [Navigation](https://developer.android.com/guide/navigation) - Manage transaction among the
  fragments
- [Room](https://developer.android.com/training/data-storage/room) - -Create, store, and manage
  persistent data backed by a SQLite database.
- [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) -
  WorkManager is the recommended solution for persistent work. Work is persistent when it remains
  scheduled through app restarts and system reboots.
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging
  network data.
- [Moshi](https://github.com/square/moshi) - A modern JSON library for Android, Java and Kotlin
- [Coil](https://github.com/coil-kt/coil) - An image loading library for Android backed by Kotlin
