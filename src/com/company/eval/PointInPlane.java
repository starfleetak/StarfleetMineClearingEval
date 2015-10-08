package com.company.eval;

/**
 * The problem deals with 2D planes and 3D spaces, so have a way to represent
 * points on a plane independently. This class turned out not to be very useful,
 * that is, never used independently.
 * 
 * @author Vijay
 */
public class PointInPlane {
  
  private int xAxis; // X dimension on a plane
  private int yAxis; // Y dimention on a plane
  
  public PointInPlane (int x, int y) {
    this.setxAxis(x);
    this.setyAxis(y);
  }
  public int getxAxis() {
    return xAxis;
  }

  final void setxAxis(int x) {
    this.xAxis = x;
  }

  public int getyAxis() {
    return yAxis;
  }

  final void setyAxis(int y) {
    this.yAxis = y;
  }
}
