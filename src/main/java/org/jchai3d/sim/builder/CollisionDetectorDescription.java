/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jchai3d.sim.builder;

/**
 *
 * @author Marcos
 */
public class CollisionDetectorDescription {

    public String type;

    public String radius;

    public String useNeighbors;

    public String affectChildren;

    public CollisionDetectorDescription(String type, String radius, String useNeighbors, String affectChildren) {
        this.type = type;
        this.radius = radius;
        this.useNeighbors = useNeighbors;
        this.affectChildren = affectChildren;
    }
}
