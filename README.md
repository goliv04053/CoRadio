# CoRadio

CoRadio is a free, ad-free, open-source internet radio player for Android. It runs on phones, Android TV, Android Auto and Android Automotive, and plays live streams from two community-maintained station directories:

- [Community Radio Browser](https://www.radio-browser.info)
- [Web Radio DB](https://jcorporation.github.io/webradiodb)

## About this project

CoRadio is a fork of [OpenRadio](https://github.com/ChernyshovYuriy/OpenRadio) by Yurii Chernyshov, originally released under the Apache License 2.0. The fork exists to keep an ad-free, fully open-source version of the app after the original project was acquired. It is not affiliated with or endorsed by the original project or its current maintainers.

An archive of the original repository is on [Software Heritage](https://archive.softwareheritage.org/browse/origin/directory/?origin_url=https://github.com/ChernyshovYuriy/OpenRadio).

Changes from OpenRadio: analytics removed, funding links removed, the defunct cloud-account feature removed, rebranded to CoRadio, and the phone app moved to Material 3 / Material You. See [CHANGELOG.md](CHANGELOG.md).

## Features

- Browse stations by country, category/genre, popularity, or search
- Favorites and your own stations, with reordering
- Import and export your stations to a file
- Equalizer, sleep timer, and configurable stream buffering
- Chromecast support, Bluetooth auto-play
- Phone UI with Material You dynamic color (Android 12+), plus Android TV, Android Auto and Android Automotive builds

## Download

Release APKs are published on the [Releases page](https://github.com/TheFirstMonster/CoRadio/releases). You can also follow releases with [Obtainium](https://github.com/ImranR98/Obtainium) by adding the repository URL.

Every push to `master` also builds debug APKs; see the [GitHub Actions](https://github.com/TheFirstMonster/CoRadio/actions/workflows/android.yml) page.

Requires Android 4.2 (API 17) or newer for the phone app; the TV app needs Android 5.0 and the Automotive app Android 9.

## Building

Requirements: JDK 17 and the Android SDK (platform 34).

```sh
./gradlew assembleDebug          # phone, TV and automotive debug APKs
./gradlew testDebugUnitTest      # unit tests
./gradlew lintDebug              # lint (existing findings are frozen in lint-baseline.xml)
```

APKs end up in `app/build/outputs/apk/`, `tv/build/outputs/apk/` and `automotive/build/outputs/apk/`.

### Modules

| Module | Purpose |
|---|---|
| `app` | Phone application |
| `tv` | Android TV application |
| `automotive` | Android Automotive OS application |
| `common` | Playback service, models, storage, networking (no UI) |
| `common-ui` | Dialogs, adapters and presenters shared by the apps |

## Releasing

Releases are built by [`.github/workflows/release.yml`](.github/workflows/release.yml) when a tag starting with `v` is pushed:

```sh
git tag v15.0.0 && git push origin v15.0.0
```

The workflow runs the unit tests, builds the release APKs for all three apps and attaches them to a GitHub Release. To produce *signed* APKs, add these repository secrets (Settings → Secrets and variables → Actions):

| Secret | Value |
|---|---|
| `KEYSTORE_BASE64` | `base64 -w0 your.keystore` |
| `KEYSTORE_ALIAS` | key alias |
| `KEYSTORE_PASSWORD` | password used for both the keystore and the key |

Without them the APKs are built unsigned. Locally, put the same values in a `sign.properties` file (`file=`, `alias=`, `pwd=`); it is git-ignored.

## Permissions

- `INTERNET`, `ACCESS_NETWORK_STATE`: stream audio and monitor connectivity
- `WAKE_LOCK`, `FOREGROUND_SERVICE`, `FOREGROUND_SERVICE_MEDIA_PLAYBACK`: keep playback running
- `ACCESS_COARSE_LOCATION`: only on request, to pick your country for local stations
- `READ_EXTERNAL_STORAGE` / `READ_MEDIA_IMAGES`: only on request, to use an image from your device for your own station
- `BLUETOOTH`, `BLUETOOTH_ADMIN`, `BLUETOOTH_CONNECT`: only on request, for Bluetooth auto-play
- `RECORD_AUDIO`: only on request, for voice search on Android TV

To try the Android Auto experience on a phone, install [Android Auto](https://play.google.com/store/apps/details?id=com.google.android.projection.gearhead) and enable developer mode.

## Credits

- [Community Radio Browser](https://www.radio-browser.info) and [Web Radio DB](https://jcorporation.github.io/webradiodb) for station data
- Playlist parsing based on [JavaPlaylistParser](https://github.com/wseemann/JavaPlaylistParser) by William Seemann
- Swipe actions based on [swipe-maker](https://github.com/xenione/swipe-maker)
- Playback by [AndroidX Media3 / ExoPlayer](https://github.com/androidx/media)

## License

Apache License 2.0. See [LICENSE](LICENSE) and [NOTICE](NOTICE). Original OpenRadio copyright notices are retained in the source files.
