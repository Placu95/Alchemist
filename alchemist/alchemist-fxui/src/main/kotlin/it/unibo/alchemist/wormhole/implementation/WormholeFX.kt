/*
 * Copyright (C) 2010-2020, Danilo Pianini and contributors
 * listed in the main project's alchemist/build.gradle.kts file.
 *
 * This file is part of Alchemist, and is distributed under the terms of the
 * GNU General Public License, with a linking exception,
 * as described in the file LICENSE in the Alchemist distribution's top directory.
 */

package it.unibo.alchemist.wormhole.implementation

import it.unibo.alchemist.boundary.wormhole.implementation.AbstractWormhole2D
import it.unibo.alchemist.boundary.wormhole.implementation.PointAdapter
import it.unibo.alchemist.model.interfaces.Environment
import it.unibo.alchemist.model.interfaces.Position2D
import it.unibo.alchemist.wormhole.implementation.adapter.NodeViewPort
import javafx.scene.Node
import java.util.function.Function

/**
 * An implementation of [AbstractWormhole2D] for JavaFX.
 *
 * @param P the type of the position
 */
open class WormholeFX<P : Position2D<P>>(
    environment: Environment<*, P>,
    node: Node
) : AbstractWormhole2D<P>(
    environment,
    NodeViewPort(node),
    Function<NodeViewPort, PointAdapter<P>> {
        PointAdapter.from(it.node.boundsInLocal.width / 2, it.node.boundsInLocal.height / 2)
    }
)
