/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jchai3d.sim.builder;

/**
 *
 * @author Marcos
 */
public class HapticDeviceDescriptor {

    private String name;
    private String dof;
    private String flag;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the dof
     */
    public String getDoF() {
        return dof;
    }

    /**
     * @param dof the dof to set
     */
    public void setDof(String dof) {
        this.dof = dof;
    }

    /**
     * @return the flag
     */
    public String getFlag() {
        return flag;
    }

    /**
     * @param flag the flag to set
     */
    public void setFlag(String flag) {
        this.flag = flag;
    }
}
