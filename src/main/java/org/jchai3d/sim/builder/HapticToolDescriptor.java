/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jchai3d.sim.builder;

/**
 *
 * @author Marcos
 */
public class HapticToolDescriptor {

    private String description;
    private String device;
    private String mesh;

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }


    /**
     * @return the mesh
     */
    public String getMesh() {
        return mesh;
    }

    /**
     * @param mesh the mesh to set
     */
    public void setMesh(String mesh) {
        this.mesh = mesh;
    }

    /**
     * @return the device
     */
    public String getDevice() {
        return device;
    }

    /**
     * @param device the device to set
     */
    public void setDevice(String device) {
        this.device = device;
    }
}
