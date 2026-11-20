# Changelog

## Unreleased

### Added
- Material 3 theme with Material You dynamic color (Android 12+) and a static fallback palette; the phone app now follows the system light/dark setting
- Tag-triggered release workflow, Dependabot, blocking lint with per-module baselines, and unit tests for `MediaId`, JSON/equalizer serializers and the PLS parser
- R8 minification and resource shrinking for release builds (APKs roughly 40-60% smaller)
- Network security config: HTTPS-only for the station directories; explicit backup and data-extraction rules

### Changed
- Sources moved to the `app.coradio` package layout; the last Java file converted to Kotlin
- About screen links point to the CoRadio fork; the original author is credited
- Debug logging goes through `AppLogger` (logcat tag `CORADIO`)

### Fixed
- Crashes on malformed ASX playlists, missing media extras and a late `MediaPresenterImpl` callback after the view detached
- Other apps could delete cached station images, or read local image files, through the exported image provider
- On Android TV the settings list opened the wrong dialog on devices without Google services

### Removed
- Prebuilt APKs from the repository
- The non-functional cloud account / cloud storage feature
- Analytics wrapper and funding links
