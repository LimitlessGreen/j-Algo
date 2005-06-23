/*
 * Created on 21.05.2005
 * $Id: State.java,v 1.1 2005/06/23 10:08:27 jalgosequoia Exp $
 */
package org.jalgo.module.dijkstraModule.model;

import java.util.ArrayList;

import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.graphics.Color;
import org.jalgo.module.dijkstraModule.util.StatusbarText;


/**
 * @author Julian Stecklina
 *
 */
public class State {
    private boolean isMacro;
    private Graph graph;
    private String description;

    private ArrayList borderStates;

    /** Returns the list of {@link BorderState} objects.
     * 	@return border state objects
     */
    public ArrayList getBorderStates() {
        return borderStates;
    }
    
    /** Returns the path from the given node back to the start node.
    * @return The list of nodes in the right order. (Start node is the first)
    */
   public ArrayList getPath(Node node) {
       ArrayList nodes = new ArrayList();
       
       for(Node n = node; n != null; n = node.getPredecessor()) {
           // Wow, this is wasteful. :)
           nodes.add(0,n);
       }
       
       return nodes;
   }
    
    /**
     * @return Returns the description.
     * @deprecated use getDescriptionEx() instead
     */
    public String getDescription() {
        return description;
    }
    
    private ArrayList styleRanges;
    
    /** Appends descr to the end of the current description and set appropriate styles.
     * 
     * @param descr
     * @param foreground colour
     * @param background colour
     */
    public void addStyledDescription(String descr, Color foreground, Color background) {
        int start = description.length();
        int length = descr.length();
        
        styleRanges.add(new StyleRange(start, length, foreground, background));
        description = description + descr;
    }
    
    public void addDescription(String descr) {
        description = description + descr;
    }
    
    /**
     * @return a coloured version of the decription
     */
    public StatusbarText getDescriptionEx() {
    	StyleRange[] styles = new StyleRange[styleRanges.size()];
    	
    	for (int i = 0; i<styles.length; i++)
    	    styles[i] = (StyleRange)styleRanges.get(i);
    	
        return new StatusbarText(description,styles);
    }
    /** Creates a state object with the given parameters. This constructor does 
     *  copy the passed graph. Care must be taken, that only structures are passed that
     *  are never changed again.
     * 
     * @param graph The graph
     * @param macro
     */
    public State(Graph graph, String descr, boolean macro, ArrayList borderStates) {
        this.graph = graph;
        this.description = descr;
        this.isMacro = macro;
        this.borderStates = borderStates;
        styleRanges = new ArrayList();
    }
    
    /** Creates a new state object without discription. 
     * @see State(Graph,String,boolean,ArrayList)
     */
    public State(Graph graph, boolean macro, ArrayList borderStates) {
        this(graph,"",macro,borderStates);
    }
    /**
     * @return Returns the isMacro.
     */
    public boolean isMacro() {
        return isMacro;
    }
    /** Sets the macro property of this state.
     * @param isMacro The value to set.
     */
    public void setMacro(boolean isMacro) {
        this.isMacro = isMacro;
    }
    /**
     * @return Returns the graph.
     */
    public Graph getGraph() {
        return graph;
    }
}
