package it.unibo.alchemist.model.implementations.actions.steeringstrategies

import it.unibo.alchemist.model.implementations.positions.Euclidean2DPosition
import it.unibo.alchemist.model.interfaces.GroupSteeringAction
import it.unibo.alchemist.model.interfaces.Pedestrian2D
import it.unibo.alchemist.model.interfaces.SteeringAction
import it.unibo.alchemist.model.interfaces.SteeringActionWithTarget
import it.unibo.alchemist.model.interfaces.SteeringStrategy
import it.unibo.alchemist.model.interfaces.environments.Euclidean2DEnvironment

/**
 * A [SteeringStrategy] performing a weighted sum of steering actions (see [computeNextPosition]).
 *
 * @param environment
 *          the environment in which the pedestrian moves.
 * @param pedestrian
 *          the owner of the steering actions combined by this strategy.
 * @param weight
 *          lambda used to assign a weight to each steering action: the higher the weight, the greater the
 *          importance of the action.
 */
open class Weighted<T>(
    private val environment: Euclidean2DEnvironment<T>,
    private val pedestrian: Pedestrian2D<T>,
    private val weight: SteeringAction<T, Euclidean2DPosition>.() -> Double
) : SteeringStrategy<T, Euclidean2DPosition> {

    /**
     * [actions] are partitioned in group steering actions and non-group steering actions. The overall next position
     * for each of these two sets of actions is computed via weighted sum. The resulting vectors are then summed
     * together (with unitary weight).
     */
    override fun computeNextPosition(actions: List<SteeringAction<T, Euclidean2DPosition>>): Euclidean2DPosition =
        actions.partition { it is GroupSteeringAction<T, Euclidean2DPosition> }.let { (groupActions, steerActions) ->
            groupActions.calculatePosition() + steerActions.calculatePosition()
        }

    /**
     * If there's no [SteeringActionWithTarget] among the provided [actions], a zero vector is returned. Otherwise,
     * the closest target is picked.
     */
    override fun computeTarget(actions: List<SteeringAction<T, Euclidean2DPosition>>): Euclidean2DPosition =
        (environment.getPosition(pedestrian) ?: environment.origin).let { currPos ->
            actions
                .filterIsInstance<SteeringActionWithTarget<T, out Euclidean2DPosition>>()
                .map { it.target() }
                .minByOrNull { it.distanceTo(currPos) }
                ?: currPos
        }

    private fun List<SteeringAction<T, Euclidean2DPosition>>.calculatePosition(): Euclidean2DPosition =
        if (size > 1) {
            map { it.nextPosition() to it.weight() }.run {
                val totalWeight = map { it.second }.sum()
                map { it.first * (it.second / totalWeight) }.reduce { acc, pos -> acc + pos }
            }
        } else firstOrNull()?.nextPosition() ?: environment.origin
}
