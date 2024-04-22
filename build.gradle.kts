plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
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

    compileOnly("com.github.retrooper.packetevents:spigot:2.0.2")
    compileOnly("dev.jorel:commandapi-bukkit-shade:9.3.0")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    //compileOnly("me.clip:placeholderapi:2.11.4")

    compileOnly("com.github.ben-manes.caffeine:caffeine:3.1.8")
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

tasks.shadowJar {
    dependencies {
        exclude { it.moduleGroup == "org.jetbrains.kotlin" || it.moduleGroup == "org.jetbrains.kotlinx" }
        exclude { it.moduleGroup == "org.jetbrains" }
    }

    exclude("**/*.kotlin_builtins")
    exclude("**/*.kotlin_module")

    //minimize()
}