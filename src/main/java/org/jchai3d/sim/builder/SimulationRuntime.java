/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jchai3d.sim.builder;

import org.jchai3d.display.JViewport;
import org.jchai3d.scenegraph.JCamera;
import org.jchai3d.scenegraph.JWorld;
import org.jchai3d.tools.JGeneric3dofPointer;

/**
 *
 * @author Marcos da Silva Ramos <marcos.9306@gmail.com>
 */
public class SimulationRuntime {

    private JViewport viewport;
    private JWorld world;
    private JCamera camera;
    private SimulationMetadata metaData;
    private JGeneric3dofPointer[] tools;
    private boolean deviceBinded;
    private boolean cameraMotionEnabled;
    private boolean cameraZoomEnabled;

    public SimulationRuntime() {
        world = new JWorld();
    }


    /**
     * @return the world
     */
    public JWorld getWorld() {
        return world;
    }

    /**
     * @param world the world to set
     */
    public void setWorld(JWorld world) {
        this.world = world;
    }

    /**
     * @return the camera
     */
    public JCamera getCamera() {
        return camera;
    }

    /**
     * @param camera the camera to set
     */
    public void setCamera(JCamera camera) {
        this.camera = camera;
        this.camera.setParentWorld(world);
    }

    /**
     * @return the metaData
     */
    public SimulationMetadata getMetaData() {
        return metaData;
    }

    /**
     * @param metaData the metaData to set
     */
    public void setMetaData(SimulationMetadata metaData) {
        this.metaData = metaData;
    }

    /**
     * @return the deviceBinded
     */
    public boolean isDeviceBinded() {
        return deviceBinded;
    }

    /**
     * @param deviceBinded the deviceBinded to set
     */
    public void setDeviceBinded(boolean deviceBinded) {
        this.deviceBinded = deviceBinded;
    }

    /**
     * @return the tools
     */
    public JGeneric3dofPointer[] getTools() {
        return tools;
    }

    /**
     * @param tools the tools to set
     */
    public void setTools(JGeneric3dofPointer[] tools) {
        this.tools = tools;
    }

    public void finish() {
        for(int i=0; i<tools.length; i++) {
            this.world.addChild(tools[i]);
        }
    }

    /**
     * @return the cameraMotionEnabled
     */
    public boolean isCameraMotionEnabled() {
        return cameraMotionEnabled;
    }

    /**
     * @param cameraMotionEnabled the cameraMotionEnabled to set
     */
    public void setCameraMotionEnabled(boolean cameraMotionEnabled) {
        this.cameraMotionEnabled = cameraMotionEnabled;
    }

    /**
     * @return the cameraZoomEnabled
     */
    public boolean isCameraZoomEnabled() {
        return cameraZoomEnabled;
    }

    /**
     * @param cameraZoomEnabled the cameraZoomEnabled to set
     */
    public void setCameraZoomEnabled(boolean cameraZoomEnabled) {
        this.cameraZoomEnabled = cameraZoomEnabled;
    }

    /**
     * @return the viewport
     */
    public JViewport getViewport() {
        return viewport;
    }

    /**
     * @param viewport the viewport to set
     */
    public void setViewport(JViewport viewport) {
        this.viewport = viewport;
    }
}
