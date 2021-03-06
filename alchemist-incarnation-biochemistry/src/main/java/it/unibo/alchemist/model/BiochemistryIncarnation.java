/*
 * Copyright (C) 2010-2019, Danilo Pianini and contributors listed in the main project's alchemist/build.gradle file.
 *
 * This file is part of Alchemist, and is distributed under the terms of the
 * GNU General Public License, with a linking exception,
 * as described in the file LICENSE in the Alchemist distribution's top directory.
 */

package it.unibo.alchemist.model;


import it.unibo.alchemist.model.implementations.molecules.Biomolecule;
import it.unibo.alchemist.model.implementations.nodes.CellNodeImpl;
import it.unibo.alchemist.model.implementations.reactions.BiochemicalReactionBuilder;
import it.unibo.alchemist.model.implementations.timedistributions.ExponentialTime;
import it.unibo.alchemist.model.interfaces.Action;
import it.unibo.alchemist.model.interfaces.CellNode;
import it.unibo.alchemist.model.interfaces.Condition;
import it.unibo.alchemist.model.interfaces.Environment;
import it.unibo.alchemist.model.interfaces.Incarnation;
import it.unibo.alchemist.model.interfaces.Molecule;
import it.unibo.alchemist.model.interfaces.Node;
import it.unibo.alchemist.model.interfaces.Position;
import it.unibo.alchemist.model.interfaces.Reaction;
import it.unibo.alchemist.model.interfaces.TimeDistribution;
import it.unibo.alchemist.model.interfaces.geometry.Vector;
import org.apache.commons.math3.random.RandomGenerator;

/**
 * @param <P> position type, must be a {@link Vector}
 */
public final class BiochemistryIncarnation<P extends Position<P> & Vector<P>> implements Incarnation<Double, P> {

    @Override
    public double getProperty(final Node<Double> node, final Molecule molecule, final String property) {
        return node.getConcentration(molecule);
    }

    @Override
    public Biomolecule createMolecule(final String s) {
        return new Biomolecule(s);
    }

    @Override
    public CellNode<P> createNode(
            final RandomGenerator randomGenerator,
            final Environment<Double, P> environment,
            final String parameter) {
        if (parameter == null || parameter.isEmpty()) {
            return new CellNodeImpl<>(environment);
        }
        return new CellNodeImpl<>(environment, Double.parseDouble(parameter));
    }

    @Override
    public TimeDistribution<Double> createTimeDistribution(
            final RandomGenerator randomGenerator,
            final Environment<Double, P> environment,
            final Node<Double> node,
            final String parameter
    ) {
        if (parameter == null || parameter.isEmpty()) {
            return new ExponentialTime<>(1.0, randomGenerator);
        }
        try {
            final double rate = Double.parseDouble(parameter);
            return new ExponentialTime<>(rate, randomGenerator);
        } catch (NumberFormatException e) {
            return new ExponentialTime<>(1.0, randomGenerator);
        }
    }

    @Override
    public Reaction<Double> createReaction(final RandomGenerator randomGenerator,
            final Environment<Double, P> environment,
            final Node<Double> node,
            final TimeDistribution<Double> timeDistribution,
            final String parameter) {
        return new BiochemicalReactionBuilder<>(this, node, environment)
                .randomGenerator(randomGenerator)
                .timeDistribution(timeDistribution)
                .program(parameter)
                .build();
    }

    @Override
    public Condition<Double> createCondition(
            final RandomGenerator randomGenerator,
            final Environment<Double, P> environment,
            final Node<Double> node,
            final TimeDistribution<Double> time,
            final Reaction<Double> reaction,
            final String additionalParameters
    ) {
        return null;
    }

    @Override
    public Action<Double> createAction(
            final RandomGenerator randomGenerator,
            final Environment<Double, P> environment,
            final Node<Double> node,
            final TimeDistribution<Double> time,
            final Reaction<Double> reaction,
            final String additionalParameters
    ) {
        return null;
    }

    @Override
    public Double createConcentration(final String s) {
        if (s == null) { // default value
            return 1d;
        }
        return Double.parseDouble(s);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }
}
