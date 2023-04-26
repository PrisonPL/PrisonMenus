# [PrisonMenus](https://github.com/PrisonPL)
This API allows you to create custom menus through configuration or another plugin!

To make your own submodules, you can add it as a dependency with Maven or Gradle.
**Maven**
```xml
<repositories>
  <repository>
    <id>CollidaCube-PrisonPL</id>
    <url>https://packagecloud.io/CollidaCube/PrisonPL/maven2</url>
  </repository>
</repositories>

<dependency>
  <groupId>com.collidacube.prison</groupId>
  <artifactId>PrisonMenus</artifactId>
  <version>1.0</version>
</dependency>
```

**Gradle**
```gradle
repositories {
    maven {
        url "https://packagecloud.io/CollidaCube/PrisonPL/maven2"
    }
}

compile 'com.collidacube.prison:PrisonMenus:1.0'
```
