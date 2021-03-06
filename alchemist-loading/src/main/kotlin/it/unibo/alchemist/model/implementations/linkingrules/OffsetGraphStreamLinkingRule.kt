/*
 * Copyright (C) 2010-2020, Danilo Pianini and contributors
 * listed in the main project's alchemist/build.gradle.kts file.
 *
 * This file is part of Alchemist, and is distributed under the terms of the
 * GNU General Public License, with a linking exception,
 * as described in the file LICENSE in the Alchemist distribution's top directory.
 */

package it.unibo.alchemist.model.implementations.linkingrules

import it.unibo.alchemist.model.implementations.neighborhoods.Neighborhoods
import it.unibo.alchemist.model.interfaces.Environment
import it.unibo.alchemist.model.interfaces.LinkingRule
import it.unibo.alchemist.model.interfaces.Neighborhood
import it.unibo.alchemist.model.interfaces.Node
import it.unibo.alchemist.model.interfaces.Position
import org.graphstream.graph.Graph
import kotlin.streams.toList

/**
 * A [LinkingRule] that statically connects nodes as they were configured by GraphStream.
 * An [offset] is used to determine the id of the environment's nodes when comparted to the one of the
 * provided [graph].
 */
class OffsetGraphStreamLinkingRule<T, P : Position<P>>(val offset: Int, val graph: Graph) : LinkingRule<T, P> {

    override fun computeNeighborhood(center: Node<T>, environment: Environment<T, P>): Neighborhood<T> {
        val graphNode = graph.getNode(center.id + offset)
        val neighborsIds = graphNode.neighborNodes().mapToInt { it.index + offset }.toList()
        val neighbors = environment.nodes.asSequence().filter { it.id in neighborsIds }
        return Neighborhoods.make(environment, center, neighbors.asIterable())
    }

    override fun isLocallyConsistent() = true
}
