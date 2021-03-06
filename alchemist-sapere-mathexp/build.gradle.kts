import org.gradle.kotlin.dsl.spotbugs

/*
 * Copyright (C) 2010-2019, Danilo Pianini and contributors listed in the main project's alchemist/build.gradle file.
 *
 * This file is part of Alchemist, and is distributed under the terms of the
 * GNU General Public License, with a linking exception,
 * as described in the file LICENSE in the Alchemist distribution's top directory.
 */

dependencies {
    implementation(Libs.boilerplate)
}

spotbugs {
    ignoreFailures.set(true)
}

pmd {
    isIgnoreFailures = true
}

checkstyle {
    isIgnoreFailures = true
}

tasks.withType<de.aaschmid.gradle.plugins.cpd.Cpd> {
    ignoreFailures = true
}

tasks.javadoc {
    setFailOnError(false)
}

publishing.publications {
    withType<MavenPublication> {
        pom {
            developers {
                developer {
                    name.set("Giacomo Pronti")
                    email.set("giacomo.pronti@studio.unibo.it")
                    url.set("http://apice.unibo.it/xwiki/bin/view/XWiki/GiacomoPronti/")
                }
            }
        }
    }
}
