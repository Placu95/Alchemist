/*
 * Copyright (C) 2010-2019, Danilo Pianini and contributors listed in the main project"s alchemist/build.gradle file.
 *
 * This file is part of Alchemist, and is distributed under the terms of the
 * GNU General Public License, with a linking exception,
 * as described in the file LICENSE in the Alchemist distribution"s top directory.
 */

dependencies {
    implementation(project(":alchemist-interfaces"))
    implementation(project(":alchemist-time"))
    implementation("net.sf.trove4j:trove4j:${extra["troveVersion"]}")
    implementation("org.danilopianini:boilerplate:${extra["boilerplateVersion"]}")
    implementation("org.jgrapht:jgrapht-core:${extra["jgraphtVersion"]}")
    testImplementation(project(":alchemist-implementationbase"))
}
