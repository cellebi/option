import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    `maven-publish`
    kotlin("jvm") version "1.3.31"
}

group = "pub.cellebi"
version = "0.0.1-SNAPSHOT"

ext["developer"] = "Mak Ho Cheung"
ext["team"] = "Cellebi"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

tasks.named<Jar>("jar") {
    configManifest()
}

tasks.register<Jar>("sourcesJar") {
    configManifest()
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allJava)
}

tasks.register<Jar>("javadocJar") {
    configManifest()
    archiveClassifier.set("javadoc")
    from(tasks.javadoc.get().destinationDir)
    dependsOn("javadoc")
}

publishing {
    publications {
        create<MavenPublication>("option") {
            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
        }
    }
}

tasks.register("cellebiPublish") {
    logger.quiet("[gradle-cellebi] publish")
    group = "publishing"
    dependsOn("test")
    dependsOn("publishToMavenLocal")
}

fun Jar.configManifest() {
    manifest {
        attributes("Specification-Team" to ext["team"])
        attributes("Specification-Developer" to ext["developer"])
        attributes("Specification-Version" to project.version)
    }
}