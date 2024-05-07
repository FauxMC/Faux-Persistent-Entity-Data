# Faux-Custom-Entity-Data

This API provides the framework and necessary patches for storing custom and persistent data onto entities.

## Modder Information

This API provides a helper class `CustomDataHelper` that can be used to easily access the data stored on an entity.

### Maven Information

Every push to this repository is built and published to the [BlameJared](https://maven.blamejared.com) maven, to use these builds in  your project, simply add the following code in your build.gradle

```gradle
repositories {
    maven { url 'https://maven.blamejared.com' }
}

dependencies {
    modImplementation("com.faux.faux-custom-entity-data:Faux-Custom-Entity-Data-fabric-1.20.6:[VERSION]")
}
```

Just replace `[VERSION]` with the latest released version, which is currently:

[![Maven](https://img.shields.io/maven-metadata/v?color=C71A36&label=&metadataUrl=https%3A%2F%2Fmaven.blamejared.com%2Fcom%2Ffaux%2Fingredientextension%2FIngredientExtensionAPI-fabric-1.20.6%2Fmaven-metadata.xml&style=flat-square)](https://maven.blamejared.com/com/faux/ingredientextension/)

Simply remove the `v` and use that version, so `v1.0.0` becomes `1.0.0`