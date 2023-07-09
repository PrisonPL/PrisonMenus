# [PrisonMenus](https://github.com/PrisonPL)
This API allows you to create custom menus through configuration or another plugin!

Since this module acts as an API, it does not require PrisonCore.
This plugin supports [PlaceholderAPI](https://github.com/PlaceholderAPI/PlaceholderAPI).

To make your own submodules, you can add it as a dependency with Maven or Gradle.
**Maven**
```xml
<repositories>
  <repository>
    <id>CollidaCube-PrisonPL</id>
    <url>https://packagecloud.io/CollidaCube/PrisonPL/maven2</url>
  </repository>
</repositories>

<dependencies>
    <dependency>
      <groupId>com.collidacube.prison</groupId>
      <artifactId>PrisonMenus</artifactId>
      <version>1.1</version>
    </dependency>
</dependencies>
```

**Gradle**
```gradle
repositories {
    maven {
        url "https://packagecloud.io/CollidaCube/PrisonPL/maven2"
    }
}

compile 'com.collidacube.prison:PrisonMenus:1.1'
```
