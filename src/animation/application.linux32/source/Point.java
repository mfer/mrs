public final class Point{ 
  private static int counter;
  public int id;
  public double x;
  public double y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
    id=counter++;
  }

  public double getX() { return x; }
  public double getY() { return y; }
  public double r() { return Math.sqrt(x*x + y*y); }
  public double theta() { return Math.atan2(y, x); }

  public double distance(Point that) {
    double dx = this.x - that.x;
    double dy = this.y - that.y;
    return Math.sqrt(dx*dx + dy*dy);
  }

  public String toString() {
    return "(" + x + ", " + y + ")";
  } 

}
