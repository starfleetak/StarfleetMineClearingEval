package com.company.eval;

import com.company.eval.Ship.Offset;
import com.company.eval.Ship.Pattern;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The main setting of the problem, represents a cuboid space. Its main properties
 * are the location of mines and the ship. Its main behaviors are 
 * {@link #executeMove(com.company.eval.Ship.Pattern, com.company.eval.Ship.Offset, java.lang.String) }
 * and {@link #computeCuboidGrid() }.
 * 
 * @author Vijay
 */
public class CuboidSpace {

  private List<Mine> mines = new ArrayList<Mine>();
  private Ship ship;
  
  public CuboidSpace (Ship ship, List<Mine> mines) {
    this.setShip(ship);
    this.setMines(mines);
  }

  /**
   * Executes a single script sentence. Compute the torpedo locations, if fired,
   * and clears the mines destroyed by the torpedos from the space.
   * 
   * @param pattern pattern fired from ship, can be null
   * @param direction direction the ship moves after fire, can never be null, 
   * if input is empty, set Offset.NO_OFFSET
   * @param command actual command, only used to print the result
   */
  public void executeMove(Pattern pattern, Offset direction, String command) {
    this.computeCuboidGrid(); // This will calculate and print the grid
    
    // First fire
    if (pattern != null) {
      List<Torpedo> torpedos = this.getShip().fire(pattern);
      for (Torpedo t : torpedos) {
        // getMines is a defensive copy, so we can iterate without fear of 
        // concurrent modification
        for (Mine m : this.getMines()) {
          if (t.destroys(m)) {
            // modify the state of the CuboidSpace directly i.e. cant use getMines
            mines.remove(m);
          }
        }
      }
    }
    // Then move. 
    this.getShip().move(direction);
    
    System.out.println(command + "\n");
    System.out.println(this.computeCuboidGrid());
  }
  
  /**
   * Compute dynamically the output layout format per requirements, give ObjectSpace. 
   * Key idea is to not have output format influence the space and object location
   * representation. 
   * 
   * 1. Solve mapping of Cartesian coordinate of objects in space to 2d array 
   * indexing for display. (Handle inversion of Y axis)
   * 2. Handle Z-axis to character conversion
   * 
   * @return the display string for the cuboid space
   */
  public String computeCuboidGrid() {
    this.findDimensionOfGrid();
    
    int gridWidth = maxX - minX + 1;
    int extendWidth = (maxX - this.getShip().getxAxis()) - (this.getShip().getxAxis() - minX);
    gridWidth = gridWidth + Math.abs(extendWidth);
    
    int gridHeight = maxY - minY + 1;
    int extendHeight = (maxY - this.getShip().getyAxis()) - (this.getShip().getyAxis() - minY);
    gridHeight = gridHeight + Math.abs(extendHeight);
    
    // TODO: Replace with Log4j
    // System.out.println("Extend grid by (w, h): " + extendWidth + ", " + extendHeight);
    
    int [][] grid = new int[gridHeight][gridWidth];
    for (Mine m : this.getMines()) {
      // The depth is relative to the ship depth!!
      int pointDepth = m.getzAxis() - this.getShip().getzAxis();
      // Instead of having a dual loop to initialize the 2D array to some value 
      // outside the depth range, we set depth to some arbitrary DEPTH_OUT_OF_RANGE
      // during transition when mine and ship are at same depth
      if (pointDepth == 0) {
        pointDepth = DEPTH_OUT_OF_RANGE;
      }
      int mineGridY = (m.getyAxis() - minY) + ((extendHeight > 0) ? extendHeight : 0);
      // Effectively, if moving West, add extendWidth, to offset the grid further
      // otherwise, no offset is required
      int mineGridX = (m.getxAxis() - minX) + ((extendWidth > 0) ? extendWidth : 0) ;
      
      // TODO: Replace with Log4j
      // System.out.println( mineGridY + ", " + mineGridX);
      grid[mineGridY][mineGridX] = pointDepth;
    }

    if (displayShip) {
      grid[ship.getyAxis() - minY + ((extendHeight > 0) ? extendHeight : 0)]
              [ship.getxAxis() - minX + ((extendWidth > 0) ? extendWidth : 0)] = REPRESENT_SHIP;
    }
    
    StringBuilder sb = new StringBuilder();
    for (int y =gridHeight -1; y >= 0; y--) {
      for (int x = 0; x < gridWidth; x++) {
        int pointDepth = grid[y][x];
        if (pointDepth == REPRESENT_SHIP && displayShip) {
          sb.append("5"); // Well looks like an "S" - used for debugging
        } else if (pointDepth == DEPTH_OUT_OF_RANGE) {
          // Technically the ship has not passed the mine! That needs to be 
          // explicitly captured, but to match the output, printing *
          sb.append("*");
        } else if (pointDepth > 0 && pointDepth < DEPTH_OUT_OF_RANGE) {
          // We missed the mine!!
          sb.append("*");
        } else if (pointDepth == 0) {    
          // This is the array initialization value
          sb.append(".");
        } else if (pointDepth >= -26 && pointDepth < 0) {
          sb.append(Character.toChars(Math.abs(pointDepth) + CHAR_OFFSET_LOWER));
        } else {
          sb.append(Character.toChars(Math.abs(pointDepth) + CHAR_OFFSET_UPPER));
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }

  /**
   * Helper method for cuboidGrid. Finds the max and min dimensions along
   * Cartesian co-ordinates among all ships, ignoring depth.
   */
  private void findDimensionOfGrid() {
    this.minX = this.getShip().getxAxis();
    this.maxX = this.getShip().getxAxis();
    
    this.minY = this.getShip().getyAxis();
    this.maxY = this.getShip().getyAxis();
    
    for (Mine m : this.getMines()) {
      if (m.getxAxis() < minX) {
        minX = m.getxAxis();
      }
      if (m.getxAxis() > maxX) {
        maxX = m.getxAxis();
      }
      if (m.getyAxis() < minY) {
        minY = m.getyAxis();
      }
      if (m.getyAxis() > maxY) {
        maxY = m.getyAxis();
      }
    }
  }
  
  /* Accessor for external properties of cuboid*/
  
  public List<Mine> getMines() {
    // Defensive copy - to preserve immutability
    List<Mine> copyOfMines = new ArrayList<>(mines);
    return copyOfMines;
  }

  private void setMines(List<Mine> mines) {
    // Defensive copy - can only modify this.mines at object creation time
    this.mines.addAll(mines);
  }

  public Ship getShip() {
    // Ship is immutable
    return ship;
  }

  private void setShip(Ship ship) {
    this.ship = ship;
  }
  
  /* Internal state variables for implementing behavior of space */
  
  // These are internal dynamic state variables, we will not have 
  // set/get accessors
  private int minX;
  private int maxX;
  
  private int minY;
  private int maxY;
  
  // CHAR_OFFSET is the number added to make the ship depth into the required
  // character in UTF-16/ Java Characters.toChars()
  protected static final int CHAR_OFFSET_UPPER = 38;
  protected static final int CHAR_OFFSET_LOWER = 96;
  
  // Helpful variables, see usage for purpose. Too niche for general explanation
  private static final int DEPTH_OUT_OF_RANGE = 99;
  private static final int REPRESENT_SHIP = 100;
  
  // Compile time switch for testing 
  // It is useful to see the actual ship instead of "." for testing, just to 
  // make sure ship is indeed at the center.
  private final boolean displayShip = false;
  
  /* Following methods generate Cuboids for unit testing */
    
  // A method for internal testing, where we don't display the actual command,
  // since the test has commands programmed directly
  protected void executeMove(Pattern pattern, Offset direction) {
    this.executeMove(pattern, direction, "");
  }
  
  protected static CuboidSpace sampleCuboidSpace1() {
    List<Mine> mines = new ArrayList<Mine>();
    mines.add(new Mine(0, 1, -5));
    mines.add(new Mine(1, 0, -1));
    mines.add(new Mine(-1, -1, -27));
    
    Ship ship = new Ship(0, 0, 0);
    
    return new CuboidSpace(ship, mines);
  }
  
  protected static CuboidSpace sampleCuboidSpaceEx1and3() {
    List<Mine> mines = new ArrayList<Mine>();
    mines.add(new Mine(0, 0, -26));
    Ship ship = new Ship(0, 0, 0);
    
    return new CuboidSpace(ship, mines);
  }
  
  protected static CuboidSpace sampleCuboidSpaceEx2and5() {
    List<Mine> mines = new ArrayList<Mine>();
    mines.add(new Mine(0, 2, -52));
    mines.add(new Mine(2, 0, -52));
    mines.add(new Mine(-2, 0, -52));
    mines.add(new Mine(0, -2, -52));
    
    Ship ship = new Ship(0, 0, 0);
    
    return new CuboidSpace(ship, mines);
  }
  
  protected static CuboidSpace sampleCuboidSpaceEx4() {
    List<Mine> mines = new ArrayList<Mine>();
    mines.add(new Mine(0, 2, -1));
    mines.add(new Mine(0, -2, -1));
    
    Ship ship = new Ship(0, 0, 0);
    
    return new CuboidSpace(ship, mines);
  }
}
