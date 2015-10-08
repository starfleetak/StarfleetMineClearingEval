package com.company.eval;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A class to track the position of the ship and functionality to add its
 * movement and firing capabilities.
 * 
 * @author Vijay
 */
public class Ship extends ObjectInSpace {

  public Ship(int x, int y, int z) {
    super(x, y, z);
  }
  
  // Movement or firing direction (represents both) capability of the ship
  public enum Offset {
  
    NORTH (0, 1),
    
    SOUTH (0, -1),
    
    EAST (1, 0),
    
    WEST (-1, 0),
    
    NO_OFFSET (0, 0),
    
    NORTH_EAST (1, 1),
    
    NORTH_WEST (-1, 1),
    
    SOUTH_EAST (1, -1),
    
    SOUTH_WEST (-1, -1);
    
    private final int xOffset;
    private final int yOffset;
    
    private Offset(int xOffset, int yOffset) {
      this.xOffset = xOffset;
      this.yOffset = yOffset;
    }
  };
  
  // Firing pattern of the ship
  public enum Pattern {
  
    ALPHA (Arrays.asList(Offset.SOUTH_WEST, Offset.NORTH_WEST, Offset.SOUTH_EAST, Offset.NORTH_EAST)),
    
    BETA (Arrays.asList(Offset.WEST, Offset.SOUTH, Offset.NORTH, Offset.EAST)),
    
    GAMMA (Arrays.asList(Offset.WEST, Offset.NO_OFFSET, Offset.EAST)),
    
    DELTA (Arrays.asList(Offset.SOUTH, Offset.NO_OFFSET, Offset.NORTH));
    
    private final List<Offset> fireOffsets;
    
    private Pattern(List<Offset> fireOffsets) {
      this.fireOffsets = fireOffsets;
    }
  };
  
  /**
   * Move the ship in the given direction. Ship always drops by 1km by default!
   * 
   * @param direction the direction to move.
   */
  public void move (Offset direction) {
    this.setxAxis(this.getxAxis() + direction.xOffset);
    this.setyAxis(this.getyAxis() + direction.yOffset);
    this.setzAxis(this.getzAxis() - 1);
  }
  
  /**
   * Create list of torpedos in CuboidSpace for each firing event.
   * 
   * @param pattern the pattern of torpedo fire
   * 
   * @return Torpedos (essentially their positions) fired 
   */
  public List<Torpedo> fire (Pattern pattern) {
    List<Torpedo> torpedos = new ArrayList<Torpedo>();
    for (Offset o : pattern.fireOffsets) {
      torpedos.add(new Torpedo(this.getxAxis()+o.xOffset, this.getyAxis()+o.yOffset, this.getzAxis()));
    }
    return torpedos;
  }
  
}
