/*
 * Copyright (C) 2010-2019, Danilo Pianini and contributors listed in the main(project"s alchemist/build.gradle file.
 *
 * This file is part of Alchemist, and is distributed under the terms of the
 * GNU General Public License, with a linking exception,
 * as described in the file LICENSE in the Alchemist distribution"s top directory.
 */

dependencies {
    // API
    api(project(":alchemist-interfaces"))
    api(project(":alchemist-implementationbase"))
    api(protelis("interpreter"))
    api(protelis("lang"))
    // IMPLEMENTATION
    implementation(project(":alchemist-euclidean-geometry"))
    implementation(project(":alchemist-maps"))
    implementation(Libs.commons_lang3)
    // TESTING
    testImplementation(project(":alchemist-loading"))
    testImplementation(project(":alchemist-engine"))
    testImplementation(Libs.commons_io)
}

publishing.publications {
    withType<MavenPublication> {
        pom {
            developers {
                developer {
                    name.set("Danilo Pianini")
                    email.set("danilo.pianini@unibo.it")
                    url.set("http://www.danilopianini.org")
                }
            }
            contributors {
                contributor {
                    name.set("Jacob Beal")
                    email.set("jakebeal@bbn.com")
                    url.set("http://web.mit.edu/jakebeal/www/")
                }
            }
        }
    }
}

tasks.register<Exec>("shadowJar-testProtelisExecution") {
    dependsOn(tasks.shadowJar)
    val jar = tasks.shadowJar.get().archiveFile.get().asFile.absolutePath
    val javaExecutable = org.gradle.internal.jvm.Jvm.current().javaExecutable.absolutePath
    val command = arrayOf(
        javaExecutable,
        "-jar",
        jar,
        "-y",
        "$projectDir/src/test/resources/testbase.yml",
        "-t",
        "2",
        "--batch"
    )
    commandLine(*command)
}
