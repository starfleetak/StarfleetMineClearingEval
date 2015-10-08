/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.eval;

import java.util.List;
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
public class CuboidSpaceTest {
  
  public CuboidSpaceTest() {
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
   * Test of sampleCuboidSpace1 method, of class CuboidSpace.
   */
  @Test
  public void testSampleCuboidSpace1() {
    System.out.println("sampleCuboidSpace1");
    CuboidSpace space = CuboidSpace.sampleCuboidSpace1();
    space.computeCuboidGrid();
    space.executeMove(Ship.Pattern.GAMMA, Ship.Offset.NO_OFFSET);
    space.computeCuboidGrid();
    space.executeMove(Ship.Pattern.DELTA, Ship.Offset.WEST);
    space.computeCuboidGrid();
    space.executeMove(Ship.Pattern.DELTA, Ship.Offset.NO_OFFSET);
    space.computeCuboidGrid();
  }
  
  /**
   * Test of sampleCuboidSpaceEx1and3 method, of class CuboidSpace.
   */
  @Test
  public void testSampleCuboidSpaceEx1and3_1() {
    System.out.println("sampleCuboidSpaceEx1and3");
    CuboidSpace space = CuboidSpace.sampleCuboidSpaceEx1and3();
    space.computeCuboidGrid();
    space.executeMove(Ship.Pattern.GAMMA, Ship.Offset.NO_OFFSET);
    space.computeCuboidGrid();
  }
  
  /**
   * Test of sampleCuboidSpaceEx2and5 method, of class CuboidSpace.
   */
  @Test
  public void testSampleCuboidSpaceEx2and5() {
    System.out.println("sampleCuboidSpaceEx2and5");
    CuboidSpace space = CuboidSpace.sampleCuboidSpaceEx2and5();
    space.computeCuboidGrid();
    space.executeMove(null, Ship.Offset.NORTH);
    space.computeCuboidGrid();
    space.executeMove(Ship.Pattern.DELTA, Ship.Offset.SOUTH);
    space.computeCuboidGrid();
    space.executeMove(null, Ship.Offset.WEST);
    space.computeCuboidGrid();
    space.executeMove(Ship.Pattern.GAMMA, Ship.Offset.EAST);
    space.computeCuboidGrid();
    space.executeMove(null, Ship.Offset.EAST);
    space.computeCuboidGrid();
    space.executeMove(Ship.Pattern.GAMMA, Ship.Offset.WEST);
    space.computeCuboidGrid();
    space.executeMove(null, Ship.Offset.SOUTH);
    space.computeCuboidGrid();
    space.executeMove(Ship.Pattern.DELTA, Ship.Offset.NO_OFFSET);
    space.computeCuboidGrid();
  }
  
  /**
   * Test of sampleCuboidSpaceEx1and3 method, of class CuboidSpace.
   */
  @Test
  public void testSampleCuboidSpaceEx1and3_3() {
    System.out.println("sampleCuboidSpaceEx1and3");
    CuboidSpace space = CuboidSpace.sampleCuboidSpaceEx1and3();
    space.computeCuboidGrid();
    space.executeMove(Ship.Pattern.GAMMA, Ship.Offset.NO_OFFSET);
    space.computeCuboidGrid();
    space.executeMove(Ship.Pattern.ALPHA, Ship.Offset.NORTH);
    space.computeCuboidGrid();
  }
  
  /**
   * Test of sampleCuboidSpaceEx4 method, of class CuboidSpace.
   */
  @Test
  public void testSampleCuboidSpaceEx4() {
    System.out.println("sampleCuboidSpaceEx4");
    CuboidSpace space = CuboidSpace.sampleCuboidSpaceEx4();
    space.computeCuboidGrid();
    space.executeMove(null, Ship.Offset.NORTH);
    space.computeCuboidGrid();
    space.executeMove(Ship.Pattern.DELTA, Ship.Offset.SOUTH);
    space.computeCuboidGrid();
    space.executeMove(null, Ship.Offset.SOUTH);
    space.computeCuboidGrid();
    space.executeMove(null, Ship.Offset.SOUTH);
    space.computeCuboidGrid();
    space.executeMove(Ship.Pattern.DELTA, Ship.Offset.NO_OFFSET);
    space.computeCuboidGrid();
  }
  
  /**
   * Test of sampleCuboidSpaceEx2and5 method, of class CuboidSpace.
   */
  @Test
  public void testSampleCuboidSpaceEx2and5_5() {
    System.out.println("sampleCuboidSpaceEx2and5");
    CuboidSpace space = CuboidSpace.sampleCuboidSpaceEx2and5();
    space.computeCuboidGrid();
    space.executeMove(null, Ship.Offset.NORTH);
    space.computeCuboidGrid();
    space.executeMove(Ship.Pattern.DELTA, Ship.Offset.SOUTH);
    space.computeCuboidGrid();
    space.executeMove(null, Ship.Offset.WEST);
    space.computeCuboidGrid();
    space.executeMove(Ship.Pattern.GAMMA, Ship.Offset.EAST);
    space.computeCuboidGrid();
    space.executeMove(null, Ship.Offset.EAST);
    space.computeCuboidGrid();
    space.executeMove(Ship.Pattern.GAMMA, Ship.Offset.WEST);
    space.computeCuboidGrid();
  }
  
}
