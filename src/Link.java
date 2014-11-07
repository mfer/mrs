import java.util.*;
import java.lang.Math;

public class Link extends Circle{

  Point sender;
  Point receiver;

  double x_sender;
  double y_sender;
  double x_receiver;
  double y_receiver;

  double length;
  double interference_radius;
  double weight;
  int x_surplus;

  public Link(double x_sender, double y_sender,
              double x_receiver, double y_receiver,
              double interference_radius){

    sender = new Point(x_sender, y_sender);
    receiver = new Point(x_receiver, y_receiver);

    center = sender;
    radius = receiver.distance(sender);

    this.x_sender = x_sender;
    this.x_receiver = x_receiver;
    this.y_sender = y_sender;
    this.y_receiver = y_receiver;

    this.interference_radius = interference_radius;

    length = receiver.distance(sender);
  }

  public Double set_weight(Double value){
    this.weight = value;
    return value;
  }

  public Double w(){
    return weight;
  }

  public Integer set_x_surplus(int value){
    this.x_surplus = value;
    return value;
  }    

  public Integer x_surplus(){
    return x_surplus;
  }
}
