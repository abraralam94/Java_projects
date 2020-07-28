/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe318.lab7;

/**
 *
 * @author Abrar 
 */
public class VoltageSource {
    private static int nextID = 1;
    private int ID;
    private double voltage;
    private Node p1;
    private Node p2;
    
    
    
    public VoltageSource(double v, Node w, Node x){
     // will add exceptions later on
        if (v < 0){
            this.voltage = Math.abs(v);
            this.p2 = w;
            this.p1 = x;
        }
        else{
        this.voltage = v;
        this.p1 = w;
        this.p2 = x;
        }
        this.ID = nextID;
        nextID ++;
        Circuit circuit = Circuit.getInstance();
        circuit.add(this);
        
    }
    public String toString(){
        Node [] nodes = this.getNodes();
        return ("V" + ID + " " + nodes[0] + " " + nodes[1] + " " + "DC" + " " + this.voltage);
    }
    public Node[] getNodes(){
        Node[] k = new Node[2];
        k[0] = p1;
        k[1] = p2;
        return k;
    }

    public int getID() {
        return ID;
    }
    
}
