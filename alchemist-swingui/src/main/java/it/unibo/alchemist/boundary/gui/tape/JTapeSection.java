/*
 * Copyright (C) 2010-2019, Danilo Pianini and contributors listed in the main project's alchemist/build.gradle file.
 *
 * This file is part of Alchemist, and is distributed under the terms of the
 * GNU General Public License, with a linking exception,
 * as described in the file LICENSE in the Alchemist distribution's top directory.
 */
package it.unibo.alchemist.boundary.gui.tape;

import java.awt.Component;

import javax.swing.JPanel;

/**
 * A {@link JTapeSection} is a set of one or more feature that should appear
 * close to each other because of stylistic or semantic reasons.
 * 
 */
@Deprecated
public abstract class JTapeSection extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = 128847317931592742L;

    /**
     * Adds a feature to the current section.
     * 
     * @param c
     *            is the {@link Component} containing the feature
     * @return a <code>boolean</code> value
     */
    public abstract boolean registerFeature(Component c);

    /**
     * Removes a feature from the current section.
     * 
     * @param c
     *            is the {@link Component} containing the feature
     * @return a <code>boolean</code> value
     */
    public abstract boolean unregisterFeature(Component c);
}
