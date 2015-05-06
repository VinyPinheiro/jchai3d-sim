/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jchai3d.sim.builder;

import java.util.HashMap;

/**
 *
 * @author Marcos
 */
public class SimulationComponent {

    private String identifier;
    private HashMap<String, String> attributes;
    private HashMap<String, String[]> values;

    public SimulationComponent() {
        attributes = new HashMap<String, String>();
        values = new HashMap<String, String[]>();
    }

    /**
     * @return the attributes
     */
    public HashMap<String, String> getAttributes() {
        return attributes;
    }

    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    /**
     *  Return attribute names for this component
     */
    public String[] getAttributeNames() {
        int n = this.attributes.size();
        String[] attr = new String[n];
        this.attributes.keySet().toArray(attr);
        return attr;
    }

    public boolean hasAttribute(String attributeName) {
        return this.attributes.containsKey(attributeName);
    }

    public String getAttributeValue(String key) {
        return attributes.get(key);
    }


    public HashMap<String, String[]> getValues() {
        return values;
    }

    public void addValue(String key, String value) {
        values.put(key, new String[]{value});
    }

    public void addValues(String key, String[] v) {
        values.put(key, v);
    }

    /**
     *  Return attribute names for this component
     */
    public String[] getValueNames() {
        int n = this.values.size();
        String[] tmp = new String[n];
        this.values.keySet().toArray(tmp);
        return tmp;
    }

    public boolean hasValue(String val) {
        return this.values.containsKey(val);
    }

    public String getValue(String key) {
        String[] v = values.get(key);
        return v == null ? null : v[0];
    }

    public String[] getValues(String key) {
        return values.get(key);
    }

    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }


    @Override
    public String toString() {
        String s = "";
        s += "ID: " + identifier;


        s += "\nValues[";

        String[] val = getValueNames();
        if (val.length > 0) {
            s += "\n";
        }


        for (int i = 0; i < val.length; i++) {
            s += "\t" + val[i] + ": " + getValue(val[i]) + "\n";
        }

            s += "]\n";

        s += "\nAttributes[";

        String[] att = getAttributeNames();
        if (att.length > 0) {
            s += "\n";
        }


        for (int i = 0; i < att.length; i++) {
            s += "\t" + att[i] + ": " + getAttributeValue(att[i]) + "\n";
        }

            s += "]\n";
        


        return s;
    }
}
