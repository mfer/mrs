import java.util.*;
import java.lang.Math;

public class Link extends Circle{

  public static int nlinks=0;
  int id;

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

    id=nlinks;
    nlinks++;

    sender = new Point(x_sender, y_sender);
    receiver = new Point(x_receiver, y_receiver);

    center = sender;
    radius = receiver.distance(sender);

    this.x_sender = x_sender;
    this.x_receiver = x_receiver;
    this.y_sender = y_sender;
    this.y_receiver = y_receiver;

    length = receiver.distance(sender);
    this.interference_radius = length*interference_radius;    
    weight = 1.0;    

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

  //Return the id of the first link that satisfy the x-surplus property
  // when x_surplus remains -1, it was observed that there is only one element, so we returned it.
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

    //returning the one las element that not satisfies xNinD >= xNout
    if (x_surplus == -1){
      if(B.size()>1 || B.size()==0){
        System.out.println(B);        
        System.out.println("What a hell! B has more than one element. And none x-surplus?");
        //System.exit(1);
        
        //Innocent tentative: take the last. DEAD CODE: make it alive commenting the System.exit(1) line.
        for (Iterator<Integer> it = B.iterator(); it.hasNext(); ) {
          x_surplus = it.next();

          double xNinD = 0.0;
          for(int adj : Ins.get(x_surplus)){ 
            xNinD = xNinD + links.get(adj).x;
          }
          System.out.print("x(NinD["+x_surplus+"])= "+xNinD);

          double xNoutD = 0.0;
          for(int adj : Outs.get(x_surplus)){ 
            xNoutD = xNoutD + links.get(adj).x;
          }
          System.out.println(" <  x(NoutD["+x_surplus+"])= "+xNoutD);
        }              

      }else{
        for (Iterator<Integer> it = B.iterator(); it.hasNext(); ) {
          x_surplus = it.next();          
        }              
      }
    } 

    return x_surplus;
  }
}
