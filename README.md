# AnimatedFloatBottomNav

[![](https://jitpack.io/v/sahar-android/AnimatedFloatBottomNav.svg)](https://jitpack.io/#sahar-android/AnimatedFloatBottomNav)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

An animated, curved floating bottom navigation bar for **Jetpack Compose**.
Pass a list of titles and icons, get an animated bar back.

## Demo

![AnimatedFloatBottomNav demo](https://user-images.githubusercontent.com/46859947/219660373-1c137d9f-7d78-4ed1-9690-c32b06d6ce58.gif)

## Features

- Animated, curved floating bottom bar built with Jetpack Compose
- Any number of tabs: one title and one icon per tab
- One callback that gives you the index of the selected tab
- Available through JitPack, no extra setup

## Installation

### 1. Add JitPack to your repositories

In `settings.gradle`:

```groovy
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

Kotlin DSL (`settings.gradle.kts`):

```kotlin
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
```

If your project still declares repositories in the root `build.gradle`, add the
same `maven { url 'https://jitpack.io' }` line to `allprojects { repositories { ... } }`.

### 2. Add the dependency

```groovy
dependencies {
    implementation 'com.github.sahar-android:AnimatedFloatBottomNav:1.0.0'
}
```

Kotlin DSL:

```kotlin
dependencies {
    implementation("com.github.sahar-android:AnimatedFloatBottomNav:1.0.0")
}
```

## Usage

```kotlin
val titles = listOf("Guests", "Traffic", "Settings")
val icons = listOf(R.drawable.guests, R.drawable.traffic, R.drawable.settings)

var selected by remember { mutableStateOf(titles.first()) }

Scaffold(
    bottomBar = {
        SimyappBottomNav.MyNav(titles, icons, Color.Red) { index ->
            selected = titles[index]
        }
    }
) { padding ->
    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        Text(
            text = selected,
            fontSize = 22.sp,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
```

### Parameters

| Parameter | Type | Description |
|-----------|------|-------------|
| titles | `List<String>` | Title of each tab |
| icons | `List<Int>` | Drawable resource id of each tab icon, in the same order as `titles` |
| color | `Color` | Accent color of the bar |
| onItemSelected | `(Int) -> Unit` | Called with the index of the tab the user selected |

Both lists must have the same size.

## Roadmap

This is an early version and I plan to keep improving it. Ideas:

- Support `ImageVector` icons
- Let the caller control the selected index (state hoisting)
- More style options (colors, sizes, animation)

## Contributing

Issues and pull requests are welcome. If you find a bug or have an idea, please
[open an issue](../../issues).

## Author

Sahar Simyari — Android developer
[LinkedIn](https://www.linkedin.com/in/sahar-simyari/)

## License

Released under the MIT License. See [LICENSE](LICENSE).
