/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coe318.lab7;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mahmu
 */
public class VoltageSourceTest {
    
    public VoltageSourceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of toString method, of class VoltageSource.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        Node n1 = new Node();
        Node n2 = new Node();
        VoltageSource instance = new VoltageSource(-12.0, n1, n2);
        String expResult = "V2 3 2 DC 12.0";
        String result = instance.toString();
        assertEquals(expResult, result);
        //fail("The test case is a prototype.");
        // TODO review the generated test code and remove the default call to fail.
        
    }

    /**
     * Test of getNodes method, of class VoltageSource.
     */
    @Test
    public void testGetNodes() {
        System.out.println("getNodes");
        Node x = new Node();
        Node y = new Node();
        VoltageSource instance = new VoltageSource(-112, x, y) ;
        Node[] expResult = new Node[2];
        expResult[0] = y;
        expResult[1] = x;
        Node[] result = instance.getNodes();
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getID method, of class VoltageSource.
     */
    @Test
    public void testGetID() {
        /*System.out.println("getID");
        VoltageSource instance = null;
        int expResult = 0;
        int result = instance.getID();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");*/
    }
    
}
