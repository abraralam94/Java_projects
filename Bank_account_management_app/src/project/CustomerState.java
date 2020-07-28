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
public abstract class CustomerState {
    
    protected abstract void changeState(Customer c);
    
    protected abstract double calculateFee(Customer c);
    
    @Override
    public abstract String toString();
}
