package com.company.eval;

/**
 * Represents the location of "Object In Space". All types of physical objects 
 * extend this to track their position.
 * 
 * @author Vijay
 */
public class ObjectInSpace extends PointInPlane {
  
  // (x, y, z) denotes the location of an object in space, with (x,y) representing
  // the object in a plane, and z representing the depth of the object, relative to
  // some reference. 
  
  private int zAxis; // Depth of the object relative to surface (start position)
  
  
  public ObjectInSpace (int x, int y, int z) {
    super(x, y);
    this.setzAxis(z);
  }

  public int getzAxis() {
    return zAxis;
  }

  final void setzAxis(int z) {
    this.zAxis = z;
  }

  @Override
  public String toString() {
    return String.format ("(%d, %d, %d)", this.getxAxis(), this.getyAxis(), this.getzAxis());
  }
  
}
