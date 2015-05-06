/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jchai3d.sim.builder;

import java.util.ArrayList;

/**
 *
 * @author Marcos
 */
public class SimulationMetadata {

    private String title;
    private String description;
    private String version;
    private String category;
    private String[] authors;
    private boolean hardwareSpecificationRequired;
    private String memoryAmmount;
    private String graphicsMemoryAmmount;
    private String processorSpeed;

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

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
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return the authors
     */
    public String[] getAuthors() {
        return authors;
    }

    /**
     * @param authors the authors to set
     */
    public void setAuthors(String[] authors) {
        this.authors = authors;
    }

    /**
     * @return the hardwareSpecificationRequired
     */
    public boolean isHardwareSpecificationRequired() {
        return hardwareSpecificationRequired;
    }

    /**
     * @param hardwareSpecificationRequired the hardwareSpecificationRequired to set
     */
    public void setHardwareSpecificationRequired(boolean hardwareSpecificationRequired) {
        this.hardwareSpecificationRequired = hardwareSpecificationRequired;
    }

    /**
     * @return the memoryAmmount
     */
    public String getMemoryAmmount() {
        return memoryAmmount;
    }

    /**
     * @param memoryAmmount the memoryAmmount to set
     */
    public void setMemoryAmmount(String memoryAmmount) {
        this.memoryAmmount = memoryAmmount;
    }

    /**
     * @return the graphicsMemoryAmmount
     */
    public String getGraphicsMemoryAmmount() {
        return graphicsMemoryAmmount;
    }

    /**
     * @param graphicsMemoryAmmount the graphicsMemoryAmmount to set
     */
    public void setGraphicsMemoryAmmount(String graphicsMemoryAmmount) {
        this.graphicsMemoryAmmount = graphicsMemoryAmmount;
    }

    /**
     * @return the processorSpeed
     */
    public String getProcessorSpeed() {
        return processorSpeed;
    }

    /**
     * @param processorSpeed the processorSpeed to set
     */
    public void setProcessorSpeed(String processorSpeed) {
        this.processorSpeed = processorSpeed;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return title;
    }

}