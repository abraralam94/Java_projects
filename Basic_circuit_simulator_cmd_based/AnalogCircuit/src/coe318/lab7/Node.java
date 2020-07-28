/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe318.lab7;

/**
 *
 * @author abrar
 */
public class Node {
    private static int nextNodeID = 0;
    // instancr variables
    private int nodeID;
    // constructor
    public Node (){
        nodeID = nextNodeID;
        nextNodeID ++ ;
    }
    public String toString(){
        return (""+nodeID); 
    }
    
   
    
    
}
