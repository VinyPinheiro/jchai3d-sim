/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jchai3d.sim.builder;

/**
 *
 * @author Marcos
 */
public class InconsistentSimulationException extends Exception{

    public InconsistentSimulationException() {
        super("Inconsistent Simulation");
    }

    public InconsistentSimulationException(String msg) {
        super("Inconsistent simulation: "+msg);
    }
}
