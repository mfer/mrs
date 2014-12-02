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

  String networkType;
  static HashMap<Double,Integer> dataRateHash_802_11_b = new HashMap<Double,Integer>();
  static HashMap<Double,Integer> dataRateHash_802_11_n = new HashMap<Double,Integer>();
  Double dataRateHashIndex_802_11_b []= {4.0,6.0,8.0,10.0};
  private static void createDataRateTable_802_11_b(){
    dataRateHash_802_11_b.put(new Double(4), new Integer(1));
    dataRateHash_802_11_b.put(new Double(6), new Integer(2));
    dataRateHash_802_11_b.put(new Double(8), new Integer(6));
    dataRateHash_802_11_b.put(new Double(10), new Integer(11));
  }
  Double dataRateHashIndex_802_11_n []= {14.0,17.0,19.0,22.0,26.0,30.0,31.0,32.0};
  private static void createDataRateTable_802_11_n(){
    dataRateHash_802_11_n.put(new Double(14), new Integer(30));
    dataRateHash_802_11_n.put(new Double(17), new Integer(60));
    dataRateHash_802_11_n.put(new Double(19), new Integer(90));
    dataRateHash_802_11_n.put(new Double(22), new Integer(120));
    dataRateHash_802_11_n.put(new Double(26), new Integer(180));
    dataRateHash_802_11_n.put(new Double(30), new Integer(240));
    dataRateHash_802_11_n.put(new Double(31), new Integer(270));
    dataRateHash_802_11_n.put(new Double(32), new Integer(300));
  }
  double beta=1.0;
  int dataRate=1;
  public static final int MAPSIZE=20000;
  public static final int MAXRADIUS=2000;
  public static final int ADDITIVEMAXRADIUS=4;

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
  }

  public Link(double x_sender, double y_sender,
              double x_receiver, double y_receiver,
              double interference_radius, String nt, double betaRate){

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

    networkType=nt;
    beta= betaRate;
    switch (networkType) {
      case "802_11_b" : //System.out.println("802_11_b");
        dataRate = dataRateHash_802_11_b.get(beta).intValue();
        break;
      case "802_11_n" : //System.out.println("802_11_n");
        dataRate = dataRateHash_802_11_n.get(beta).intValue();
        break;
      default: //System.out.println("Default");
    }
    //weight = dataRate;
  }

  public Link(String nt){
    if (nlinks == 0) {
      createDataRateTable_802_11_b();
      createDataRateTable_802_11_n();
    }

    id=nlinks;
    nlinks++;

    receiver= new Point( (int)Math.round(Math.random()*MAPSIZE), (int)Math.round(Math.random()*MAPSIZE) );
    //sender= new Point(receiver.x + ADDITIVEMAXRADIUS, receiver.y +ADDITIVEMAXRADIUS);
    sender= new Point(receiver.x + (int)Math.round(Math.random()*MAXRADIUS)+ ADDITIVEMAXRADIUS, receiver.y + (int)Math.round(Math.random()*MAXRADIUS) +ADDITIVEMAXRADIUS); // length (2V2-4V2)

    center = sender;
    radius = receiver.distance(sender);

    length=receiver.distance(sender);
    this.interference_radius = length*interference_radius;

    networkType=nt;
    switch (networkType) {
      case "802_11_b" : //System.out.println("802_11_b");
        beta= ((int)Math.floor(Math.random()*4) +2)*2;
        dataRate = dataRateHash_802_11_b.get(beta).intValue();
        break;
      case "802_11_n" : //System.out.println("802_11_n");
        int indexBeta = (int)Math.floor(Math.random()*8);
        beta= dataRateHashIndex_802_11_n[indexBeta];
        dataRate = dataRateHash_802_11_n.get(beta).intValue();
        break;
      default: //System.out.println("Default");
    }
    //weight = dataRate;
  }

  public ArrayList<Link> getAllRates(){
    ArrayList<Link> links = new ArrayList<Link>();
    switch (networkType) {
      case "802_11_b" : //System.out.println("Add link with all datarates - 802.11b");
        for (int  i=0; i<dataRateHashIndex_802_11_b.length; i++){
          //System.out.print(" "+i+" ");
          Link link = new Link(receiver.x, receiver.y, sender.x, sender.y, 1.0, "802_11_b", dataRateHashIndex_802_11_b[i]);
          links.add(link);
        }
        break;
      case "802_11_n" : //System.out.println("Add link with all datarates - 802.11n");
        for (int  i=0; i<dataRateHashIndex_802_11_n.length; i++){
          //System.out.print(" "+i+" ");
          Link link = new Link(receiver.x, receiver.y, sender.x, sender.y, 1.0, "802_11_n", dataRateHashIndex_802_11_n[i]);
          links.add(link);
        }
        break;
      default: //System.out.println("Default");
    }
    return links;
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

    //returning the one last element that not satisfies xNinD >= xNout
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
