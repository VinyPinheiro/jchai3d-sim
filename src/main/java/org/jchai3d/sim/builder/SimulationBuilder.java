/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package org.jchai3d.sim.builder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jchai3d.devices.JHapticDeviceHandler;
import org.jchai3d.display.JViewport;
import org.jchai3d.files.JMeshLoader;
import org.jchai3d.graphics.JColorf;
import org.jchai3d.graphics.JMaterial;
import org.jchai3d.math.JMaths;
import org.jchai3d.math.JVector3d;
import org.jchai3d.scenegraph.*;
import org.jchai3d.tools.JGeneric3dofPointer;
import org.jchai3d.widgets.JBitmap;
import org.jchai3d.widgets.JLabel;

/**
 *
 * @author Marcos
 */
public abstract class SimulationBuilder {

    private static HashMap<String, JGenericObject> sceneElements;
    private static HashMap<String, HapticToolDescriptor> sceneTools;
    private static HashMap<String, Object> sceneObjects;

    static {
        sceneElements = new HashMap<String, JGenericObject>();
        sceneTools = new HashMap<String, HapticToolDescriptor>();
        sceneObjects = new HashMap<String, Object>();
    }

    public static SimulationRuntime buildSimulation(SimulationDescription description) {

        SimulationComponent[] components = description.getSimulationComponents();
        SimulationComponent c;
        String id;
        String path = description.getFile().getPath().replace("\\", "/");
        path = path.substring(0, path.lastIndexOf("/") + 1);

        SimulationRuntime runtime = new SimulationRuntime();
        runtime.setMetaData(description.getMetaData());

        sceneElements.clear();
        sceneTools.clear();
        sceneObjects.clear();

        for (int i = 0; i < components.length; i++) {
            c = components[i];
            id = c.getIdentifier();
            if (id == null) {
                continue;
            }

            /*
             * Tool Description: used later to bind create tools
             */
            if (id.equals("tool")) {
                String device = c.getValue("device");
                String desc = c.getValue("description");
                String devMesh = c.getValue("device-mesh");
                String proxyMesh = c.getValue("proxy-mesh");

                HapticToolDescriptor tool = new HapticToolDescriptor();

                tool.setDescription(desc);
                tool.setDevice(device);
                tool.setMesh(devMesh);

                sceneTools.put(c.getAttributeValue("name"), tool);



            } else if (id.equals("childs-2d-front")) {

                JBitmap jBitmap = new JBitmap();
                String transparencyEnabled = c.getValue("transparencyEnabled");
                String position = c.getValue("position");
                String file = c.getValue("file");

                if (file != null) {
                    try {
                        jBitmap.load(new File(file));
                    } catch (IOException ex) {
                        continue;
                    }
                }
                else
                    continue;
                
                if (transparencyEnabled != null)
                    jBitmap.setTransparencyEnabled(Boolean.valueOf(transparencyEnabled), true);
                
                if (position != null)
                    jBitmap.setPosition(10, 10, 0);

                sceneElements.put(c.getAttributeValue("name"), jBitmap);

            } else if (id.equals("operation")) {

                String rotation = c.getValue("rotation");
                String angle = c.getValue("angle");

                OperationDescriptor operationDescriptor = new OperationDescriptor();

                operationDescriptor.setRotation(rotation);
                operationDescriptor.setAngle(angle);

                sceneObjects.put(c.getAttributeValue("name"), operationDescriptor);

            } else if (id.equals("viewport")) {

                String enabled = c.getValue("enabled");
                String fps = c.getValue("fps");
                String stereoOn = c.getValue("stereoOn");

                /*
                 * create S.O window output
                 */
                JViewport jViewport = new JViewport(runtime.getCamera(), false);

                if (enabled != null) {
                    jViewport.setEnabled(Boolean.valueOf(enabled));
                }

                if (fps != null) {
                    jViewport.setFPSLabelVisible(Boolean.valueOf(fps));
                }

                if (stereoOn != null) {
                    jViewport.setStereoOn(Boolean.valueOf(stereoOn));
                }

                // set world
                String[] childs = c.getValues("world");
                if (childs != null) {
                    for (int k = 0; k < childs.length; k++) {

                        Object ob = sceneObjects.get(childs[k]);

                        if (ob instanceof JViewport) {
                            jViewport.setCamera(runtime.getCamera());
                        }
                    }
                }

                runtime.setViewport(jViewport);

                sceneObjects.put(c.getAttributeValue("name"), jViewport);

            } else if (id.equals("mesh")) {
                String file = c.getValue("file");
                String pos = c.getValue("position");
                String wire = c.getValue("wire-mode");
                String normal = c.getValue("normals-visible");
                String tree = c.getValue("bbox-tree-visible");
                String box = c.getValue("bbox-visible");
                String scale = c.getValue("scale-factor");
                String transparency = c.getValue("transparency-enabled");
                String transparencyLevel = c.getValue("transparency-level");
                String enabled = c.getValue("enabled");
                String frame = c.getValue("frame-visible");
                String culling = c.getValue("culling-enabled");
                String displayList = c.getValue("display-list-enabled");
                String vertexColors = c.getValue("vertex-colors-enabled");
                String vertexArrays = c.getValue("vertex-arrays-enabled");
                String stiffness = c.getValue("stiffness");
                String rotation = c.getValue("rotation");
                String collision = c.getValue("collision-detector");


                JMesh mesh = new JMesh(runtime.getWorld());
                if (file != null) {
                    try {
                        JMeshLoader.loadMeshFromFile(mesh, new File(path + file));

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                if (pos != null) {
                    mesh.setPosition(new JVector3d(pos));
                }

                if (wire != null) {
                    mesh.setWireMode(Boolean.valueOf(wire), Boolean.valueOf(wire));
                }

                if (normal != null) {
                    mesh.setNormalsVisible(Boolean.valueOf(normal));
                }

                if (tree != null) {
                    mesh.setTreeVisible(Boolean.valueOf(tree), Boolean.valueOf(tree));
                }

                if (box != null) {
                    mesh.setBoundaryBoxVisible(Boolean.valueOf(box), Boolean.valueOf(box));
                }
                if (scale != null) {
                    mesh.scale(Double.parseDouble(scale), false);
                }
                if (transparency != null) {
                    mesh.setTransparencyEnabled(Boolean.valueOf(transparency), Boolean.valueOf(transparency));
                }

                if (transparencyLevel != null) {
                    mesh.setTransparencyLevel(Float.valueOf(transparencyLevel), true, false);
                }

                if (enabled != null) {
                    mesh.setVisible(Boolean.valueOf(enabled), Boolean.valueOf(enabled));
                }

                if (frame != null) {
                    mesh.setFrameVisible(Boolean.valueOf(frame), Boolean.valueOf(frame));
                }

                if (stiffness != null) {
                    mesh.setStiffness(Double.parseDouble(stiffness), false);
                }

                if (culling != null) {
                    mesh.setCullingEnabled(Boolean.valueOf(culling), false);
                }

                if (displayList != null) {
                    mesh.setDisplayListEnabled(Boolean.valueOf(displayList));
                }

                if (vertexColors != null) {
                    mesh.setVertexColorsEnabled(Boolean.valueOf(vertexColors), Boolean.valueOf(vertexColors));
                }

                if (vertexArrays != null) {
                    mesh.setVertexArraysEnabled(Boolean.valueOf(vertexArrays));
                }

                if (rotation != null) {
                    String axis = rotation.substring(0, rotation.lastIndexOf(" "));
                    String angle = rotation.substring(rotation.lastIndexOf(" "));
                    mesh.rotate(new JVector3d(axis), Double.valueOf(angle));
                }

                if (collision != null) {
                    CollisionDetectorDescription d = (CollisionDetectorDescription) sceneObjects.get(collision);
                    double radius = Double.valueOf(d.radius);
                    boolean affectChildren = Boolean.valueOf(d.affectChildren);
                    boolean useNeighbors = Boolean.valueOf(d.useNeighbors);
                    if (d.type.equals("aabb")) {
                        mesh.createAABBCollisionDetector(radius, affectChildren, useNeighbors);
                    } else if (d.type.equals("sphere-tree")) {
                        mesh.createSphereTreeCollisionDetector(radius, affectChildren, useNeighbors);
                    } else {
                        mesh.createBruteForceCollisionDetector(true, false);
                    }
                }

                // MESH MATERIAL
                String[] childs = c.getValues("material");
                if (childs != null) {
                    for (int k = 0; k < childs.length; k++) {

                        Object ob = sceneObjects.get(childs[k]);

                        if (ob instanceof JMaterial) {
                            mesh.setMaterial((JMaterial) ob);
                        }
                    }
                }

                // MESH OPERATION 	 
                String[] operation = c.getValues("operation");
                if (operation != null) {
                    for (int k = 0; k < operation.length; k++) {

                        Object ob = sceneObjects.get(operation[k]);

                        if (ob instanceof OperationDescriptor) {
                            OperationDescriptor operationDescriptor = (OperationDescriptor) ob;

                            if (operationDescriptor != null) {
                                String angle = operationDescriptor.getAngle();
                                String rot = operationDescriptor.getRotation();
                                mesh.rotate(new JVector3d(rotation == null ? "0.0 0.0 0.0" : rot),
                                        JMaths.jDegToRad(angle == null ? 0 : Double.valueOf(angle)));
                            }
                        }
                    }
                }

                sceneElements.put(c.getAttributeValue("name"), mesh);

                /*
                 * Light
                 */
            } else if (id.equals("light")) {
                String enabled = c.getValue("enabled");
                String pos = c.getValue("position");
                String dir = c.getValue("direction");
                String amb = c.getValue("ambient");
                String dif = c.getValue("diffuse");
                String spc = c.getValue("specular");
                JLight light = new JLight(runtime.getWorld());


                if (enabled != null) {
                    light.setEnabled(Boolean.valueOf(enabled));
                }

                if (pos != null) {
                    light.setPosition(new JVector3d(pos));
                }

                if (dir != null) {
                    light.setDirection(new JVector3d(dir));
                    light.setDirectionalLight(true);
                }

                if (amb != null) {
                    light.setAmbientColor(new JColorf(amb));
                }

                if (dif != null) {
                    light.setDiffuseColor(new JColorf(dif));
                }

                if (spc != null) {
                    light.setSpecularColor(new JColorf(spc));
                }

                sceneElements.put(c.getAttributeValue("name"), light);

                /*
                 * Label
                 */
            } else if (id.equals("label")) {
                JLabel label = new JLabel();

                String text = c.getValue("text");
                String color = c.getValue("color");

                if (text != null) {
                    label.setString(text);
                }

                if (color != null) {
                    label.setFontColor(new JColorf(color));
                }
                sceneElements.put(c.getAttributeValue("name"), label);


                // Material definition
            } else if (id.equals("material")) {
                String enabled = c.getValue("enabled");
                String amb = c.getValue("ambient");
                String dif = c.getValue("diffuse");
                String spc = c.getValue("specular");
                String emi = c.getValue("emission");
                String shi = c.getValue("shininess");
                String trl = c.getValue("transparencyLevel");
                String via = c.getValue("vibrationAmplitude");
                String vif = c.getValue("vibrationFrequency");
                String vic = c.getValue("viscosity");
                String dfc = c.getValue("dynamicFriction");
                String mmd = c.getValue("magnetMaxDistance");
                String mmf = c.getValue("magnetMaxForce");

                JMaterial material = new JMaterial();

                if (amb != null) {
                    material.setAmbient(new JColorf(amb));
                }

                if (dif != null) {
                    material.setDiffuse(new JColorf(dif));
                }

                if (spc != null) {
                    material.setSpecular(new JColorf(spc));
                }

                if (emi != null) {
                    material.setEmission(new JColorf(emi));
                }

                if (shi != null) {
                    material.setShininess(new Integer(shi));
                }

                if (trl != null) {
                    material.setTransparencyLevel(new Float(trl));
                }

                if (via != null) {
                    material.setVibrationAmplitude(new Double(via));
                }

                if (vif != null) {
                    material.setVibrationFrequency(new Double(vif));
                }

                if (vic != null) {
                    material.setViscosity(new Double(vic));
                }

                if (dfc != null) {
                    material.setDynamicFriction(new Double(dfc));
                }

                if (mmd != null) {
                    material.setMagnetMaxDistance(new Double(mmd));
                }

                if (mmf != null) {
                    material.setMagnetMaxForce(new Double(mmf));
                }

                sceneObjects.put(c.getAttributeValue("name"), material);


            } else if (id.equals("collision-detector")) {
                String type = c.getValue("type");
                String radius = c.getValue("radius");
                String neighbors = c.getValue("use-neighbors");
                String affect = c.getValue("affect-children");

                CollisionDetectorDescription desc = new CollisionDetectorDescription(type, radius, neighbors, affect);

                sceneObjects.put(c.getAttributeValue("name"), desc);

                /*
                 * World
                 */
            } else if (id.equals("world")) {

                // WORLD CHILDS
                String[] childs = c.getValues("childs");
                JWorld world = runtime.getWorld();
                if (childs != null) {
                    for (int k = 0; k < childs.length; k++) {
                        JGenericObject obj = sceneElements.get(childs[k]);
                        world.addChild(obj);
                    }
                }

                String[] lights = c.getValues("lights");

                // ILLUMINATION
                if (lights != null) {
                    for (int k = 0; k < lights.length; k++) {
                        world.addLightSource((JLight) sceneElements.get(lights[k]));
                    }
                }

                String pos = c.getValue("position");

                if (pos != null) {
                    world.setPosition(new JVector3d(pos));
                }

                String cor = c.getValue("color");

                if (cor != null) {
                    world.setBackgroundColor(new JColorf(cor));
                }

                runtime.setWorld(world);

                sceneElements.put(c.getAttributeValue("name"), world);
            } else if (id.equals("camera")) {

                JCamera camera = new JCamera(runtime.getWorld());

                // CAMERA CHILDS
                String[] childs = c.getValues("childs");
                if (childs != null) {
                    for (int k = 0; k < childs.length; k++) {
                        camera.addChild(sceneElements.get(childs[k]));
                    }
                }

                // CAMERA CHILDS 2D (FRONT)
                childs = c.getValues("childs-2d-front");
                if (childs != null) {
                    for (int k = 0; k < childs.length; k++) {
                        camera.front2Dscene.addChild(sceneElements.get(childs[k]));
                    }
                }

                // CAMERA CHILDS 2D (BACK)
                childs = c.getValues("childs-2d-back");
                if (childs != null) {
                    for (int k = 0; k < childs.length; k++) {
                        camera.back2Dscene.addChild(sceneElements.get(childs[k]));
                    }
                }
                
                String pos = c.getValue("position");
                String dir = c.getValue("direction");
                String rot = c.getValue("orientation");
                String world = c.getValue("world");
                String fov = c.getValue("fov");
                String near = c.getValue("near");
                String far = c.getValue("far");
                String multipassTransparency = c.getValue("multipass-transparency-enabled");

                JVector3d upv = camera.getUpVector();
                JVector3d posv = camera.getPosition();
                JVector3d lookv = camera.getLookVector();

                if (pos != null) {
                    posv = new JVector3d(pos);
                }

                if (dir != null) {
                    lookv = new JVector3d(dir);

                }

                if (rot != null) {
                    upv = new JVector3d(rot);
                }
                camera.set(posv, lookv, upv);

                if (world != null) {
                    camera.setParentWorld((JWorld) sceneElements.get(world));
                }

                if (fov != null) {
                    camera.setFieldViewAngle(Double.parseDouble(fov));
                }
                if (near != null) {
                    camera.setNearClippingPlaneDistance(Double.parseDouble(near));
                }
                if (far != null) {
                    camera.setFarClippingPlaneDistance(Double.parseDouble(far));
                }

                if (multipassTransparency != null) {
                    camera.enableMultipassTransparency(Boolean.valueOf(multipassTransparency));
                }


                runtime.setCamera(camera);
            }
        }

        return runtime;
    }

    public static void bindDevices(SimulationDescription description, SimulationRuntime runtime, JHapticDeviceHandler handler) {

        if (description.getDeviceCount() == 0) {
            return;
        }
        int[] indices = description.getDeviceIndices();

        JGeneric3dofPointer[] tools = new JGeneric3dofPointer[indices.length];
        HapticToolDescriptor[] toolDesc = new HapticToolDescriptor[sceneTools.size()];
        toolDesc = sceneTools.values().toArray(toolDesc);



        for (int i = 0; i < indices.length; i++) {
            tools[i] = new JGeneric3dofPointer(runtime.getWorld());

            String meshName = toolDesc[i].getMesh();

            if (meshName != null) {
                tools[i].getDeviceMesh().addChild((JMesh) sceneElements.get(meshName));
                tools[i].getProxySphere().setVisible(false, true);
                tools[i].getDeviceSphere().setVisible(false, false);
            }

            // tool haptic device
            tools[i].setHapticDevice(handler.getDevice(indices[i]));

            runtime.getWorld().addChild(tools[i]);
        }
        runtime.setTools(tools);
    }
}
