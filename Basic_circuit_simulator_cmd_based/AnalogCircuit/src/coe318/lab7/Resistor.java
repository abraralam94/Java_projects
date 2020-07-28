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
public class Resistor {
    private static int nextID = 1;
    //instance vars
    private double resistance;
    private int ID;
    private Node p1;
    private Node p2;
    //Constructor
    public Resistor(double resistance, Node Node1, Node Node2){
        if (resistance <= 0){
            throw new IllegalArgumentException("Resistance must be greater than 0!!");
        }
        if ((Node1 == null) || (Node2 == null)){
            throw new IllegalArgumentException("One of the node is invalid!!");
        }
        this.resistance = resistance;
        p1 = Node1;
        p2 = Node2;
        ID = nextID;
        nextID ++ ;
        Circuit circuit = Circuit.getInstance();
        circuit.add(this);
       
    }
    public Node[] getNodes(){
        Node[] k = new Node[2];
        k[0] = p1;
        k[1] = p2;
        return k;
    }
    public String toString(){
        return ("R" + this.ID + " " + this.p1.toString() + " " + this.p2.toString() + " " + this.resistance);
    }
    
    
    
}
