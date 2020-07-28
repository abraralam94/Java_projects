/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package project;

/**
 *
 * @author XXXXX
 */
public class Silver extends CustomerState {

    @Override
    protected void changeState(Customer c) {
        if (c.getBalance() < 10000) {
            c.setState(new Silver());
        } else if ((c.getBalance() >= 10000) && (c.getBalance() < 20000)) {
            c.setState(new Gold());
        } else if (c.getBalance() >= 20000) {
            c.setState(new Platinum());
        }
    }

    @Override
    protected double calculateFee(Customer c) {
        return 20;
    }

    @Override
    public String toString() {
        return "silver";
    }

}
