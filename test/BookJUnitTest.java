/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import KAULibrary.Book;
import javax.swing.JTable;
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
public class BookJUnitTest {
    
    public BookJUnitTest() {
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
    public void addBookTest() {
        Book book = new Book();
        assertTrue(book.addBook("0003932686", "9780071771818", "General Aviation Law", "Jerry Eichenberger", "Law", 5));
    }

    @Test
    public void issueBookTest() {
        Book book = new Book();
        assertEquals("successful", book.issueBook("1907432", "0130194921"));
    }

    @Test
    public void populateBookTabelTest() {
        Book book = new Book();
        JTable table = new JTable();
        assertTrue(book.populateBookTabel(table));
    }
    
      @Test
     public void borrowCancelationTest() {
     
       Book b = new Book();
       String isbn="0130200387";
       String userID="0007364163 ";
       assertTrue("delete from issued_book where isbn= " + isbn + " AND userID = '" + userID + "'",b.borrowCancelation(isbn, userID));
         
     }
    
}//classbook
