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
  double weight_bar;
  double x, y; //auxiliary functions

  int dataRate=1;
  double beta=1.0;

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

    length = receiver.distance(sender);
    //this.interference_radius = length*interference_radius;    

//    interference_radius = lmin*Math.pow(beta*4*ALPHA*CIDDD*Math.pow(2,ALPHA)/
//      ((ALPHA-2)*(Math.pow(raioMinimo/lmin,2))),1.0/(ALPHA-2));


//    int indexBeta = (int)Math.floor(Math.random()*8); 
//    this.beta= dataRateIndexHash[indexBeta];  
//    dataRate = setDataRate(this.beta);
  }

  public Double set_weight(Double value){
    this.weight = value;
    return value;
  }

  public Double set_weight_bar(Double value){
    this.weight_bar = value;
    return value;
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

  //Return the minimum length given a set of links
  public static Double get_min_length(ArrayList<Link> links){
    double min_length = links.get(0).length;
    for ( Link link : links){
      if(link.length < min_length) min_length = link.length;
    }
    return min_length;
  }

  //Return the maximum length given a set of links
  public static Double get_max_length(ArrayList<Link> links){
    double max_length = links.get(0).length;
    for ( Link link : links){
      if(link.length > max_length) max_length = link.length;
    }
    return max_length;
  }

  //Return the maximum x given a set of Ins
  public static Double get_max_x(ArrayList<Link> links, ArrayList<ArrayList<Integer>> Ins){
    double max_x = links.get(0).x;
    for ( ArrayList<Integer> link : Ins){
      int i = Ins.indexOf(link);
      for ( int j : Ins.get(i)){
        //System.out.println("i "+i+" j "+j+" x="+links.get(j).x);
        if(max_x < links.get(j).x) max_x = links.get(j).x;
      }
    }
    return max_x;
  }

  //Return the id of the first link that satisfy the x-surplus property
  public static Integer get_x_surplus(ArrayList<Link> links, Set<Integer> B,
                          ArrayList<ArrayList<Integer>> Ins,
                          ArrayList<ArrayList<Integer>> Outs){

    int x_surplus = -1;

    for(Link link : links){
      int a = links.indexOf(link);

      double xNinD = 0.0;
      for(int adj : Ins.get(a)){ 
        xNinD = xNinD + links.get(adj).x;
      }
      //System.out.print("x(NinD["+links.indexOf(link)+"])= "+xNinD);

      double xNoutD = 0.0;
      for(int adj : Outs.get(a)){ 
        xNoutD = xNoutD + links.get(adj).x;
      }
      //System.out.println(" x(NoutD["+links.indexOf(link)+"])= "+xNoutD);



      if(xNinD >= xNoutD){
        //System.out.println("x-surplus link of A= "+a);

        if(B.contains(a)){
          //System.out.println("x-surplus link of B= "+a);
          x_surplus = a;
          break;
        }

      }

    }

    return x_surplus;
  }
}
