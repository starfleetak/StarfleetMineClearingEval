package com.company.eval;

/**
 * A class to track the position of torpedos fired from the ship 
 * @author Vijay
 */
public class Torpedo extends ObjectInSpace {

  public Torpedo(int x, int y, int z) {
    super(x, y, z);
  }
  
  /**
   * Checks if a mine will be destroyed by this torpedo
   * @param m the mine to be checked
   * 
   * NOTE: It is not clear if torpedo will only clear mines below its level and
   * not the ones at its own level, when fired. Note the ">" comparison below
   * 
   * @return if the mine will be destroyed by this torpedo 
   */
  public boolean destroys(Mine m) {
    if (this.getxAxis() == m.getxAxis() 
            && this.getyAxis() == m.getyAxis()
            && this.getzAxis() > m.getzAxis()) {
      return true;
    }
    return false;
  }
  
}
