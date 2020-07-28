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
import java.util.ArrayList;
public class Circuit {
    private static Circuit k = null;
    //instance variables
    private ArrayList elements = new ArrayList();
    
    public static Circuit getInstance(){
        if (k == null){
            k = new Circuit();
        }
        return k;
    }
    private Circuit(){
        
    };
    
    public void add (Object r){
        if (r instanceof Resistor){
            Resistor temp = (Resistor)r;
            elements.add(temp);
        }
        else if (r instanceof VoltageSource){
               VoltageSource temp = (VoltageSource)r;  
               elements.add(temp);
        }
    }

    public ArrayList getElements() {
        return elements;
    }
    public  String toString(){
        String s = "";
        for (Object r : elements){
            if (r instanceof Resistor){
                Resistor temp = (Resistor)r;
                s += temp.toString() + "\n";
            }
            else if (r instanceof VoltageSource){
                   VoltageSource temp = (VoltageSource)r;
                   s += temp.toString() + "\n";
            }
        }
        return s;
    }
    
    
    
    
}

