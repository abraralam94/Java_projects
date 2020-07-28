/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe318.lab7;

import java.util.Scanner;
import java.util.ArrayList;

/**
 *
 * @author Abrar
 */
public class UserInterface implements SimpleUI {

    private ArrayList<String> type = new ArrayList<String>();
    private ArrayList nodes = new ArrayList();
    private ArrayList values = new ArrayList();
    private ArrayList<Node> NodeList = new ArrayList<Node>();
    private int numOfElementsCreatedSoFar;
    private int nodeIDMax;

    public void promptUser() {
        System.out.println("Please enter the component's name in the"
                + " following format: \n" + "v i j k" + " or r i j k\n" + "Where v denotes DC voltage source"
                + " and r denotes a resistor, i, j are integers\nrepresenting nodes"
                + " k is a double representing a resistive or voltage value");
        System.out.println("To start analysis after the desired values of elements, please enter 'spice';\nTo exit, enter 'end'");
    }

    public void scanInput() {
        Scanner myScanner = new Scanner(System.in);
        String seg1;
        int seg2 = 0;
        int seg3 = 0;
        double seg4 = 0;
        seg1 = myScanner.next();

        while (!(seg1.equals("end"))) {
            if (!(seg1.equals("end")) && !(seg1.equals("spice"))) {
                seg2 = myScanner.nextInt();
                seg3 = myScanner.nextInt();
                seg4 = myScanner.nextDouble();
                (this.type).add(seg1);
                (this.nodes).add(seg2);
                (this.nodes).add(seg3);
                (this.values).add(seg4);
            }
            if (seg1.equals("spice")) {
                this.constructElements();
                Circuit c = Circuit.getInstance();
                System.out.println(c.toString());
                seg1 = myScanner.next();
            } else {
                seg1 = myScanner.next();
            }
        }
        if (seg1.equals("end")) {
            System.out.println("All done");
        }

    }

    private void constructElements() {
        int[] tempNodes = new int[(this.nodes).size()];
        double[] tempValues = new double[(this.values).size()];
        for (int i = numOfElementsCreatedSoFar; i < (this.values).size(); i++) {
            tempValues[i] = (Double) ((this.values).get(i));
        }
        for (int i = 2 * numOfElementsCreatedSoFar; i < (this.nodes).size(); i++) {
            tempNodes[i] = (Integer) ((this.nodes).get(i));
        }
        int highestNodeID = this.findMax(tempNodes);

        //ArrayList<Node> j = new ArrayList<Node>();
        //Constructing Node  objects
        if (numOfElementsCreatedSoFar == 0) {
            for (int i = nodeIDMax; i <= highestNodeID; i++) {
                NodeList.add(new Node());
                nodeIDMax = i;
            }
        } else {
            if (highestNodeID > nodeIDMax) {
                for (int i = nodeIDMax + 1; i <= highestNodeID; i++) {
                    NodeList.add(new Node());
                    nodeIDMax = i;
                }
            }
        }

        // Constructing either a resistor or a voltage source
        for (int i = numOfElementsCreatedSoFar; i < tempValues.length; i++) {
            if (((this.type).get(i)).equals("r")) {
                new Resistor(tempValues[i], NodeList.get((Integer) (nodes.get(2 * i))), NodeList.get((Integer) (nodes.get((2 * i) + 1))));
                numOfElementsCreatedSoFar++;
                //nodes.remove(0);
                //nodes.remove(0);
            } else if (((this.type).get(i)).equals("v")) {
                new VoltageSource(tempValues[i], NodeList.get((Integer) (nodes.get(2 * i))), NodeList.get((Integer) (nodes.get((2 * i) + 1))));
                numOfElementsCreatedSoFar++;
                //nodes.remove(0);
                //nodes.remove(0);
            }
        }

    }

    private int findMax(int[] k) {
        int tempMax = k[0];
        for (int i = 0; i < k.length; i++) {
            if (k[i] > tempMax) {
                tempMax = k[i];
            } else {
                tempMax = tempMax;
            }
        }
        return tempMax;
    }

}
