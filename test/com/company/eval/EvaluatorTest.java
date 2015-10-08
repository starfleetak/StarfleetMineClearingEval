/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eval;

import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vijay
 */
public class EvaluatorTest {
  
  public EvaluatorTest() {
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
   * Test of runScript method, of class Evaluator.
   */
  @Test
  public void testRunScript() {
    System.out.println("runScript");
    Evaluator instance = null;
    instance.runScript();
    // TODO review the generated test code and remove the default call to fail.
    fail("The test case is a prototype.");
  }

 

  /**
   * Test of constructor method, of class Evaluator.
   */
  @Test
  public void testConstructor() throws IOException {
    System.out.println("constructor");
    
    Evaluator e = new Evaluator("C:\\Work\\src\\StarfleetTestFiles\\fieldTest1.txt", 
            "C:\\Work\\src\\StarfleetTestFiles\\scriptTest1.txt");
    e.runScript();
  }
  
  @Test
  public void testEx1() throws IOException {
    Evaluator e = new Evaluator("C:\\Work\\src\\StarfleetTestFiles\\fieldEx1and3.txt", 
            "C:\\Work\\src\\StarfleetTestFiles\\scriptEx1.txt");
    e.runScript();
  }
  
  @Test
  public void testEx2() throws IOException {
    Evaluator e = new Evaluator("C:\\Work\\src\\StarfleetTestFiles\\fieldEx2and5.txt", 
            "C:\\Work\\src\\StarfleetTestFiles\\scriptEx2.txt");
    e.runScript();
  }
  
  @Test
  public void testEx3() throws IOException {
    Evaluator e = new Evaluator("C:\\Work\\src\\StarfleetTestFiles\\fieldEx1and3.txt", 
            "C:\\Work\\src\\StarfleetTestFiles\\scriptEx3.txt");
    e.runScript();
  }
  
  @Test
  public void testEx4() throws IOException {
    Evaluator e = new Evaluator("C:\\Work\\src\\StarfleetTestFiles\\fieldEx4.txt", 
            "C:\\Work\\src\\StarfleetTestFiles\\scriptEx4.txt");
    e.runScript();
  }
  
  @Test
  public void testEx5() throws IOException {
    Evaluator e = new Evaluator("C:\\Work\\src\\StarfleetTestFiles\\fieldEx2and5.txt", 
            "C:\\Work\\src\\StarfleetTestFiles\\scriptEx5.txt");
    e.runScript();
  }
}
