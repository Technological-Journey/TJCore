import net.minecraftforge.gradle.user.UserBaseExtension

buildscript {
    repositories {
        mavenCentral()
        maven {
            name = "forge"
            setUrl("https://maven.minecraftforge.net/")
        }
    }
    dependencies {
        classpath("net.minecraftforge.gradle:ForgeGradle:2.3-SNAPSHOT")
        classpath("org.eclipse.jgit:org.eclipse.jgit:5.8.0.202006091008-r")
        classpath("org.apache.commons:commons-lang3:3.12.0")
    }
}

plugins {
    id("com.matthewprenger.cursegradle") version "1.1.0"
    id("maven-publish")
}

apply {
    plugin("net.minecraftforge.gradle.forge")
}

val mcVersion = "1.12.2"
val forgeVersion = "14.23.5.2847"
val mcFullVersion = "$mcVersion-$forgeVersion"
val modVersion = "0.0.1"
version = "$mcVersion-$modVersion"
group = "TJCore"

configure<BasePluginConvention> {
    archivesBaseName = "TJCore"
}

fun minecraft(configure: UserBaseExtension.() -> Unit) = project.configure(configure)

minecraft {
    version = mcFullVersion
    mappings = "stable_39"
    runDir = "run"
    replace("@VERSION@", modVersion)
    replaceIn("TJValues.java")
}
repositories {
    maven {
        setUrl("https://minecraft.curseforge.com/api/maven")
    }
    maven {
        setUrl("http://chickenbones.net/maven/")
    }
    maven {
        setUrl("http://dvs1.progwml6.com/files/maven/")
    }
    maven { // TOP
        name = "tterrag maven"
        setUrl("https://maven.tterrag.com/")
    }
    maven {
        name = "CraftTweaker Maven"
        setUrl("https://maven.blamejared.com/")
    }
}

dependencies {
    "deobfCompile"("mezz.jei:jei_1.12.2:+")
    "provided"(files("libs/gregtech-1.12.2-2.1.1-beta.jar"))
    //"provided"(files("libs/GregicalityMultiblocks-1.12.2-1.1.0.jar"))
    "deobfCompile"("codechicken-lib-1-8:CodeChickenLib-1.12.2:3.2.3.358:universal")
    "deobfCompile"("codechicken:ChickenASM:1.12-1.0.2.9")
    "deobfCompile"("mcjty.theoneprobe:TheOneProbe-1.12:1.12-1.4.23-16")
    "deobfCompile"("team.chisel.ctm:CTM:MC1.12.2-1.0.2.31")
    "deobfCompile"("CraftTweaker2:CraftTweaker2-MC1120-Main:1.12-4.1.20.655")
    "provided"(files("libs/BrandonsCore-1.12.2-2.4.20.162-universal.jar"))
    "provided"(files("libs/Draconic-Evolution-1.12.2-2.3.28.354-universal.jar"))
    "provided"(files("libs/RedstoneFlux-1.12-2.1.1.1-universal.jar"))
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val processResources: ProcessResources by tasks
val sourceSets: SourceSetContainer = the<JavaPluginConvention>().sourceSets

processResources.apply {
    inputs.property("version", modVersion)
    inputs.property("mcversion", mcFullVersion)

    from(sourceSets["main"].resources.srcDirs) {
        include("mcmod.info")
        expand(mapOf("version" to modVersion,
            "mcversion" to mcFullVersion))
    }

    // copy everything else, thats not the mcmod.info
    from(sourceSets["main"].resources.srcDirs) {
        exclude("mcmod.info")
    }
}
