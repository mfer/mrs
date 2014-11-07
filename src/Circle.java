public class Circle{
  protected Point center;
  protected double radius;

  public Circle(){
  }

  public Circle(Point center, double radius) {
    this.center = center;
    this.radius = radius;
  }

  public boolean contains(Point p) {  return p.distance(center) <= radius; }

  public double area()      { return Math.PI * radius * radius; }
  public double perimeter() { return 2 * Math.PI * radius;      }

  public boolean intersects(Circle c) {
    return center.distance(c.center) <= radius + c.radius;
  }

  public boolean hitsVertical(double line){
    return ((line - radius) < center.x) && ((line + radius) >= center.x);
  }

  public boolean hitsHorizontal(double line){
    return ((line - radius) < center.y) && ((line + radius) >= center.y);
  }

}