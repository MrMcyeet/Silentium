import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("kapt") version "1.9.0"
    kotlin("jvm") version "1.9.0"
    java
}

shadow {
    group = "me.mcyeet"
    version = "1.0"
}

repositories {
    mavenCentral()

    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")

    //maven { url = 'https://repo.extendedclip.com/content/repositories/placeholderapi/' }
    maven("https://repo.codemc.io/repository/maven-releases/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")

    compileOnly("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")
    kapt("com.velocitypowered:velocity-api:3.3.0-SNAPSHOT")

    compileOnly("com.github.retrooper.packetevents:spigot:2.0.2")
    //compileOnly("dev.jorel:commandapi-bukkit-shade:9.3.0")
    //compileOnly("org.incendo:cloud-core:2.0.0-beta.2")
    //compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    //compileOnly("me.clip:placeholderapi:2.11.4")

    //compileOnly("com.github.ben-manes.caffeine:caffeine:2.9.3")
    implementation("com.github.ben-manes.caffeine:caffeine:2.9.3")
    implementation("org.reflections:reflections:0.9.12")
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)

    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.register<ShadowJar>("legacy") {
    from(tasks.shadowJar.get().project.sourceSets.main.get().output)
    configurations = tasks.shadowJar.get().configurations
    archiveClassifier.set("legacy")

    exclude("**/*.kotlin_builtins")
    exclude("**/*.kotlin_module")
}

tasks.shadowJar {
    dependsOn("legacy")
    archiveClassifier.set("")

    dependencies {
        exclude(dependency("com.google.errorprone:error_prone_annotations"))
        exclude(dependency("com.github.ben-manes.caffeine:caffeine"))
        exclude(dependency("org.checkerframework:checker-qual"))

        exclude { it.moduleGroup == "org.jetbrains.kotlin" || it.moduleGroup == "org.jetbrains.kotlinx" }
        exclude { it.moduleGroup == "org.jetbrains" }
    }

    exclude("**/*.kotlin_builtins")
    exclude("**/*.kotlin_module")
    //minimize()
}