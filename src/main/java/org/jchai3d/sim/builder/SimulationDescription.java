package org.jchai3d.sim.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Marcos
 */
public class SimulationDescription {

    ArrayList<SimulationComponent> simulationComponents;

    ArrayList<SimulationComponent> simulationDevices;

    private SimulationMetadata metaData;

    private int[] deviceIndices;

    private File file;

    public SimulationDescription() {
        simulationComponents = new ArrayList<SimulationComponent>();
        simulationDevices = new ArrayList<SimulationComponent>();
        deviceIndices = new int[0];
    }
    
    public SimulationComponent[] getComponentsByID(String id) {
        ArrayList<SimulationComponent> tmp = new ArrayList<SimulationComponent>();
        int n = simulationComponents.size();
        for(int i=0; i<n; i++) {
            if(tmp.get(i).getIdentifier().equals(id)) {
                tmp.add(simulationComponents.get(i));
            }
        }
        SimulationComponent[] c = new SimulationComponent[tmp.size()];
        return tmp.toArray(c);
    }

    public void addSimulationComponent(SimulationComponent c) {
        simulationComponents.add(c);
    }

    public SimulationComponent[] getSimulationComponents() {
        SimulationComponent[] c = new SimulationComponent[simulationComponents.size()];
        return simulationComponents.toArray(c);
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

    @Override
    public String toString() {
        String s = "";
        int n = simulationComponents.size();
        for(int i=0; i<n; i++) {
            s+= "# Component "+i+"\n";
            s+= simulationComponents.get(i);
        }

        return s;
    }

    /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }


    public void addSimulationDevice(SimulationComponent d) {
        simulationDevices.add(d);
    }

    public int getDeviceCount() {
        return simulationDevices.size();
    }

    public String[] getDeviceNames() {
        String[] s = new String[simulationDevices.size()];
        for(int i=0; i<s.length; i++) {
            s[i] = simulationDevices.get(i).getAttributeValue("name");
        }
        return s;
    }

    public SimulationComponent[] getSimulationDevices() {
        SimulationComponent[] d = new SimulationComponent[simulationDevices.size()];
        simulationDevices.toArray(d);
        return d;
    }

    /**
     * @return the deviceIndices
     */
    public int[] getDeviceIndices() {
        return deviceIndices;
    }

    /**
     * @param deviceIndices the deviceIndices to set
     */
    public void setDeviceIndices(int[] deviceIndices) {
        this.deviceIndices = deviceIndices;
    }

    /**
     * 
     * @throws InconsistentSimulationException 
     */
    public void checkSimulation() throws InconsistentSimulationException{
        SimulationComponent tmp[] = getSimulationComponents();
        String s, id;
        String path = file.getPath();
        int index = path.lastIndexOf(File.separator);
        path = path.substring(0, index + 1);
        
        for(int i=0; i<tmp.length; i++) {
            id = tmp[i].getIdentifier();
            
            if(id == null)
                continue;
            // check if all meshes are consistent
            if(id.equals("mesh")) {
                // check if file exists
                s = tmp[i].getValue("file");
                if(s!= null) {
                    File f = new File(path + s);
                    if(!f.exists()) {
                        throw new InconsistentSimulationException("Missing mesh file.");
                    }
                }
            }// end-mesh-checking
        }//end-for
    }
}
