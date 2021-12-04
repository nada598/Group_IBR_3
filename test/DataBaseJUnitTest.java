/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import KAULibrary.Database;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author DELL
 */
public class DataBaseJUnitTest {
    
    public DataBaseJUnitTest() {
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

        @Test
     public void loginTest() {
     
         Database db = new Database();
         assertTrue(db.login("0007364163", "7088819")); //sabah
     }
    
     @Test
     public void queryTest() {
     
     Database db = new Database();
     String id="0007364163";
     String password="7088819";
     
     assertEquals("SELECT admin_ID, password FROM admin WHERE admin_ID = '" + id + "'" + "and password = '" + password + "'",db.query(id,password)); //sabah
     
     }
     
}//
