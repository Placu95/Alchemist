/*
 * Copyright (C) 2010-2018, Danilo Pianini and contributors listed in the main
 * project's alchemist/build.gradle file.
 *
 * This file is part of Alchemist, and is distributed under the terms of the
 * GNU General Public License, with a linking exception, as described in the file
 * LICENSE in the Alchemist distribution's top directory.
 */

package it.unibo.alchemist.boundary.monitors

import de.saring.leafletmap.MapConfig
import de.saring.leafletmap.ZoomControlConfig
import it.unibo.alchemist.boundary.CustomLeafletMapView
import it.unibo.alchemist.boundary.wormhole.implementation.LinearZoomManager
import it.unibo.alchemist.boundary.wormhole.interfaces.Wormhole2D
import it.unibo.alchemist.model.interfaces.Concentration
import it.unibo.alchemist.model.interfaces.Environment
import it.unibo.alchemist.model.interfaces.GeoPosition
import it.unibo.alchemist.runOnFXThread
import it.unibo.alchemist.wormhole.implementation.LeafletMapWormhole
import javafx.concurrent.Worker
import java.util.concurrent.CompletableFuture

/**
 * Simple implementation of a monitor that graphically represents a simulation on a 2D map, specifically LeafletMap.
 *
 * @param <T> The type which describes the [Concentration] of a molecule
 */
class LeafletMapDisplay<T> : AbstractFXDisplay<T, GeoPosition>() {
    private val map = CustomLeafletMapView()
    private val mapLoading: CompletableFuture<Worker.State>

    init {
        map.prefWidthProperty().bind(widthProperty())
        map.prefHeightProperty().bind(heightProperty())
        background.children.add(map)
        mapLoading = map.displayMap(MapConfig(zoomControlConfig = ZoomControlConfig(false)))
    }

    override fun init(environment: Environment<T, GeoPosition>) {
        if (!mapLoading.isDone) {
            mapLoading.join()
        }
        runOnFXThread {
            map.preventWrapping()
        }
        super.init(environment)
    }

    override fun createWormhole(environment: Environment<T, GeoPosition>) =
        LeafletMapWormhole(environment, this, map)

    override fun createZoomManager(wormhole: Wormhole2D<GeoPosition>) =
        LinearZoomManager(
            CustomLeafletMapView.MAX_ZOOM_VALUE.toDouble(),
            CustomLeafletMapView.ZOOM_RATE.toDouble(),
            CustomLeafletMapView.MIN_ZOOM_VALUE.toDouble(),
            CustomLeafletMapView.MAX_ZOOM_VALUE.toDouble()
        )
}
