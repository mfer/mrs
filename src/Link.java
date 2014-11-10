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
  double x, y; //auxiliary functions

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

  public Double set_x(Double value){
    this.x = value;
    return value;
  }    

  public Double x(){
    return x;
  }      

  public Double set_y(Double value){
    this.y = value;
    return value;
  }    

  public Double y(){
    return y;
  }      

  //Return the maximum x given a set of Ins
  public static Double get_max_x(ArrayList<Link> links, ArrayList<ArrayList<Integer>> Ins){
    double max_x = 0.0;
    System.out.println("get_max_x");
    for ( int i=0; i< Ins.size(); i++ ){
      //for ( int j=0; j< Ins.get(i).size(); j++ ){
        System.out.println("i "+i);
      //}
    }
    return max_x;
  }

  //Return the id of the first ink that satisfy the x-surplus property
  public static Integer get_x_surplus(ArrayList<Link> links, Set<Integer> B){
    int x_surplus = -1;
    for ( Integer b : B ){      
      System.out.println("surplus? "+b+" "+links.get(b).x);
      if ( true ){
        x_surplus = b;
        break;
      }
    }
    return x_surplus;
  }
}
