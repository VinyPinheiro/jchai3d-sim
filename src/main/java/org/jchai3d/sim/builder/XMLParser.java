/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jchai3d.sim.builder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Marcos
 */
public abstract class XMLParser {

    private static File file;

    public static SimulationDescription parseFile(String path) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
        return parseFile(new File(path));
    }

    public static SimulationDescription parseFile(File file) throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {
        XMLParser.file = file;
        SimulationDescription descr = parseFile(new FileInputStream(file));
        descr.setFile(file);
        return descr;
    }

    public static SimulationDescription parseFile(InputStream stream) throws ParserConfigurationException, SAXException, IOException {

        // abrir documento
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(stream);

        // a classe que cont�m a descri��o da simulação
        SimulationDescription desc = new SimulationDescription();

        if(file != null) {
            desc.setFile(file);
        }

        // criar a raíz, que é o próprio documento
        Element rootNode = document.getDocumentElement();

        /* Passo 1 : Ler meta dados*/
        desc.setMetaData(parseMeta(rootNode));

        /* Passo 2 : Ler dispositivos*/
        parseDevices(rootNode, desc);

        /* Passo 3 : Ler cena*/
        parseScene(rootNode, desc);

        return desc;
    }

    private static SimulationMetadata parseMeta(Element rootNode) {
        SimulationMetadata metadata = new SimulationMetadata();
        Node tmp = rootNode.getElementsByTagName("scene-meta").item(0);

        if (tmp == null) {
            return metadata;
        }

        Element meta = (Element) tmp;

        NodeList nodes = meta.getElementsByTagName("*");

        int len = nodes.getLength();

        for (int i = 0; i < len; i++) {
            Element e = (Element) nodes.item(i);

            if (e.getTagName().equals("hardware")) {
                String s = e.getAttribute("required");
                if (s != null) {
                    metadata.setHardwareSpecificationRequired(s.equals("true") ? true : false);
                }
                NodeList hn = e.getElementsByTagName("*");
                int n = hn.getLength();

                for (int j = 0; j < n; j++) {
                    e = (Element) hn.item(j);
                    if (e.getTagName().equals("memory")) {
                        metadata.setMemoryAmmount(e.getFirstChild().getNodeValue());
                    } else if (e.getTagName().equals("graphics-mem")) {
                        metadata.setGraphicsMemoryAmmount(e.getFirstChild().getNodeValue());
                    } else if (e.getTagName().equals("processor")) {
                        metadata.setProcessorSpeed(e.getFirstChild().getNodeValue());

                    }
                }

            } else if (e.getTagName().equals("authors")) {
                NodeList authors = e.getElementsByTagName("author");
                int n = authors.getLength();
                String[] aut = new String[n];
                for (int j = 0; j < n; j++) {
                    e = (Element) authors.item(j);
                    aut[j] = e.getFirstChild().getNodeValue();
                }
                metadata.setAuthors(aut);
            } else if (e.getTagName().equals("title")) {
                metadata.setTitle(e.getFirstChild().getNodeValue());
            } else if (e.getTagName().equals("version")) {
                metadata.setVersion(e.getFirstChild().getNodeValue());
            } else if (e.getTagName().equals("description")) {
                metadata.setDescription(e.getFirstChild().getNodeValue());
            } else if (e.getTagName().equals("category")) {
                metadata.setCategory(e.getFirstChild().getNodeValue());
            }
        }

        return metadata;
    }

    private static void parseDevices(Element rootNode, SimulationDescription desc) {

        Element deviceTag = (Element) rootNode.getElementsByTagName("devices").item(0);

        if(deviceTag == null)
            return;
        
        NodeList devices = deviceTag.getElementsByTagName("device");
        int n = devices.getLength();

        if (n == 0) {
            return;
        }

        SimulationComponent device;

        for (int i = 0; i < n; i++) {
            device = new SimulationComponent();
            Element e = (Element) devices.item(i);

            device.setIdentifier(e.getTagName());

            NamedNodeMap attr = e.getAttributes();

            if (attr == null) {
                continue;
            }

            int na = attr.getLength();

            for (int j = 0; j < na; j++) {
                Node attribute = attr.item(j);
                String key = attribute.getNodeName();
                String value = attribute.getNodeValue();
                device.addAttribute(key, value);
            }
            desc.addSimulationDevice(device);
        }

    }

    private static void parseScene(Element rootNode, SimulationDescription desc) {
        Element sceneContent = (Element) rootNode.getElementsByTagName("scene-content").item(0);
        NodeList elements = sceneContent.getChildNodes();
        int n = elements.getLength();

        if (n == 0) {
            return;
        }

        SimulationComponent component = null;

        for (int i = 0; i < n; i++) {

            component = new SimulationComponent();

            Node node = elements.item(i);

            if ((node instanceof Element) == false) {
                continue;
            }

            Element e = (Element) node;

            // nodes
            NodeList nodes = e.getElementsByTagName("*");

            // Look up for lights
            NodeList lights = e.getElementsByTagName("light");
            int len = lights.getLength();

            if(len > 0) {
                String[] aux = new String[len];
                for(int j=0; j<len; j++) {
                    aux[j] = lights.item(j).getFirstChild().getNodeValue();
                }
                component.addValues("lights", aux);
            }

            // Look for childs
            NodeList childs = e.getElementsByTagName("child");
            len = childs.getLength();

            if(len > 0) {
                String[] aux = new String[len];
                for(int j=0; j<len; j++) {
                    aux[j] = childs.item(j).getFirstChild().getNodeValue();
                }
                component.addValues("childs", aux);
            }


            //Look for front 2d childs
            NodeList childs2d = e.getElementsByTagName("child-2d-front");
            len = childs2d.getLength();

            if(len > 0) {
                String[] aux = new String[len];
                for(int j=0; j<len; j++) {
                    aux[j] = childs2d.item(j).getFirstChild().getNodeValue();
                }
                component.addValues("childs-2d-front", aux);
            }


            //Look for back 2d childs
            childs2d = e.getElementsByTagName("child-2d-back");
            len = childs2d.getLength();

            if(len > 0) {
                String[] aux = new String[len];
                for(int j=0; j<len; j++) {
                    aux[j] = childs2d.item(j).getFirstChild().getNodeValue();
                }
                component.addValues("childs-2d-back", aux);
            }

            if (nodes != null) {

                len = nodes.getLength();

                if (len != 0) {

                    for (int j = 0; j < len; j++) {
                        Node tmp = nodes.item(j);

                        if ((tmp != null)) {
                            Element etmp = (Element) tmp;

                            String key = etmp.getTagName();

                            if (!key.equals("child") && !key.equals("child-2d")) {
                                String value = tmp.getFirstChild().getNodeValue();

                                component.addValue(key, value);
                            }
                        }// end-if
                    } // end-for
                }// end-if
            } // end-if

            /* Attributes*/

            component.setIdentifier(e.getTagName());

            NamedNodeMap attr = e.getAttributes();

            if (attr != null) {

                int na = attr.getLength();

                for (int j = 0; j < na; j++) {
                    Node attribute = attr.item(j);
                    String key = attribute.getNodeName();
                    String value = attribute.getNodeValue();
                    component.addAttribute(key, value);
                }
            }
            desc.addSimulationComponent(component);
        }

        desc.addSimulationComponent(component);

    }

    private static String getChildTagValue(Element elem, String tagName) throws Exception {
        NodeList children = elem.getElementsByTagName(tagName);
        if (children == null) {
            return null;
        }
        Element child = (Element) children.item(0);
        if (child == null) {
            return null;
        }
        return child.getFirstChild().getNodeValue();
    }
}
