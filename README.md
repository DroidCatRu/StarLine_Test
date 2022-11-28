# StarLine_Test

[![Tests on master branch](https://github.com/DroidCatRu/StarLine_Test/actions/workflows/test.yml/badge.svg?branch=master)](https://github.com/DroidCatRu/StarLine_Test/actions/workflows/test.yml)

This app was made as a test task for StarLine company.

# Technologies

Jetpack Compose, Kotlin Coroutines, Hilt, Gradle Version Catalogs, Convention Plugins, Yandex MapKit, KtLint, Detekt

# Usage

You can install compiled application from release or build it by yourself.

If you want to build application by yourself, you should get your Yandex MapKit API key and place it in local.properties file:
```
MAPKIT_KEY=YOUR_API_KEY
```

# Task

Implement reading of the user's devices and displaying their location on the map.

## Application consists of two screens:

### 1. Catalog.

The screen displays a hierarchical list. The user's devices are grouped.

The list data structure is described in a json file:

<details>
  <summary>devices.json</summary>
  
  ```json
  {
    "devices": {
      "list": [
        {
          "parent": "root",
          "group": "private",
          "title": "Личные авто"
        },
        {
          "parent": "private",
          "type": "S96",
          "title": "VW Teramont",
          "lat": 59.944265,
          "lon": 30.307159
        },
        {
          "parent": "private",
          "type": "A93",
          "title": "Kia Rio",
          "lat": 49.869913,
          "lon": 142.788620
        },
        {
          "parent": "root",
          "group": "shared",
          "title": "Общие авто"
        },
        {
          "parent": "shared",
          "type": "A96",
          "title": "VW Caddy",
          "lat": 59.941842,
          "lon": 30.608865
        },
        {
          "parent": "shared",
          "type": "M36",
          "title": "Tesla Model X",
          "lat": 37.418964,
          "lon": -122.089884
        },
        {
          "parent": "shared",
          "type": "M17",
          "title": "Мотоцикл",
          "lat": 60.025627,
          "lon": 30.332308
        }
      ]
    }
  }
  ```
  
</details>

According to the results of reading this json file, a list of the form should be displayed:

**Личные авто**
 - VW Teramont
 - Kia Rio

**Общие авто**
 - VW Caddy
 - Tesla Model X
 - Мотоцикл

Clicking on any of the elements opens the Map screen

### 2. The map.

Displays a map with the car's label in the corresponding coordinates
