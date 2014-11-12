import java.io.*;
import java.util.*;

public class MRS{

  static HashMap<Double,Integer> dataRateHashB=new HashMap<Double,Integer>();
  static HashMap<Double,Integer> dataRateHashN=new HashMap<Double,Integer>();

  private static void createDataRateTableB(){
    dataRateHashB.put(new Double(4), new Integer(1));
    dataRateHashB.put(new Double(6), new Integer(2));
    dataRateHashB.put(new Double(8), new Integer(6));
    dataRateHashB.put(new Double(10), new Integer(11));
  }
  private static void createDataRateTableN(){
    dataRateHashN.put(new Double(14), new Integer(30));
    dataRateHashN.put(new Double(17), new Integer(60));
    dataRateHashN.put(new Double(19), new Integer(90));
    dataRateHashN.put(new Double(22), new Integer(120));
    dataRateHashN.put(new Double(26), new Integer(180));
    dataRateHashN.put(new Double(30), new Integer(240));
    dataRateHashN.put(new Double(31), new Integer(270));
    dataRateHashN.put(new Double(32), new Integer(300));
  }

  public static void main(String[] args) {
    for(int i = 0;i<args.length;i++) {
      System.out.println(args[i]);
    }

    ArrayList<Link> links = new ArrayList<Link>();


    links.add(new Link(2.0, 2.0, 2.5, 1.5, 1.0));
    links.add(new Link(3.5, 2.0, 3.5, 3.0, 1.0));
    links.add(new Link(2.5, 1.0, 3.5, 1.5, 1.0));
    links.add(new Link(0.8, 1.0, 2.0, 0.8, 1.0));
    links.add(new Link(0.5, 2.0, 0.5, 3.0, 1.0));
    links.add(new Link(1.5, 3.0, 1.5, 2.5, 1.0));
    links.add(new Link(2.5, 3.0, 3.5, 2.5, 1.0));

    links.add(new Link(2.0, 0.0, 2.0, 1.5, 1.0));
    links.add(new Link(0.0, 1.0, 1.5, 0.5, 1.0));
    links.add(new Link(4.0, 1.0, 3.0, 0.5, 1.0));
    links.add(new Link(2.0, 2.0, 3.0, 1.2, 1.0));
    links.add(new Link(0.0, 3.0, 0.5, 1.5, 1.0));
    links.add(new Link(4.0, 3.0, 3.2, 3.8, 1.0));
    links.add(new Link(1.0, 4.0, 1.5, 3.1, 1.0));
    links.add(new Link(3.0, 4.0, 2.5, 3.1, 1.0));


/*    
    System.out.println(links.size());
    for(int i = 0;i<links.size();i++) {
      System.out.println("link: " + i);
      System.out.println("    sender:   (" + links.get(i).x_sender + ", " + links.get(i).y_sender + ")");
      System.out.println("    receiver: (" + links.get(i).x_receiver + ", " + links.get(i).y_receiver + ")");
      System.out.println("    length: " + links.get(i).length);
      System.out.println("    interference: " + links.get(i).interference_radius);
    }    
*/

//__________________________________________________________________________________________________________
    System.out.println("BEGIN");        
//__________________________________________________________________________________________________________     

  //w,DG_z link_into_graph (L)
  // Construct the Disk Graph DGz and the weight w for the links using the data-rates
  // definition-4.4 from olga:2012, using the set of links L
    //implemented at GrafoRadial as a constructor
    //and at Link as the set_weight method called by the constructor.
    System.out.println("link_into_graph");
    GrafoRadial DGz = new GrafoRadial(links);

    //TODO: finish GrafoRadial.set_weight
//__________________________________________________________________________________________________________

  //G,A graph_into_protocol (DGz)
  // Construct the conflict graph G and the vertex set A from DGz
    Grafo G = new Grafo(links);
    System.out.println("graph_into_protocol");
//__________________________________________________________________________________________________________

  //D orientate_edge (G)
  // Assign an orientation on the conflict edges
    Grafo D;
    System.out.println("orientate_edge");
    D = Orientate.edge(G);    

/*
    GrafoRadial.draw(D.links, true);
    
    for(ArrayList<Integer> link : D.adjacencia){
      int linkIndex = D.adjacencia.indexOf(link);
      for(int adj : link){
        System.out.println(linkIndex+" -- "+adj);
      }
    }
*/
//__________________________________________________________________________________________________________

  //N^in_D,N^out_D get_in_out (D)
  // Establish for all links the in and out edges
    System.out.println("get_in_out");
    ArrayList<ArrayList<Integer>> Ins = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> Outs = new ArrayList<ArrayList<Integer>>();
    GetInOut.getInOut(D, Ins, Outs);

/*
    System.out.println("Outs");
    for(ArrayList<Integer> link : Outs){
      int linkIndex = Outs.indexOf(link);
      for(int adj : link){
        System.out.println(linkIndex+" --> "+adj);
      }
    }

    System.out.println("Ins");
    for(ArrayList<Integer> link : Ins){
      int linkIndex = Ins.indexOf(link);
      for(int adj : link){
        System.out.println(linkIndex+" <-- "+adj);
      }
    }
*/
//__________________________________________________________________________________________________________
  //x PDA(w, N^in_D, N^out_D, eps)
  // Price-Directive Algorithm
    double eps = 0.15;
    System.out.println("PDA");
    PDA.run(eps, links, Ins, Outs);
/*
    for(Link link : links){
      int a = links.indexOf(link);
      double xNoutD = 0.0;
      for(int adj : Outs.get(a)){ 
        xNoutD = xNoutD + links.get(adj).x;
      }
      //System.out.println("x(NoutD["+links.indexOf(link)+"])= "+xNoutD);

      double xNinD = 0.0;
      for(int adj : Ins.get(a)){ 
        xNinD = xNinD + links.get(adj).x;
      }
      //System.out.println("x(NinD["+links.indexOf(link)+"])= "+sum);
      if(xNinD >= xNoutD){
        System.out.println("x-surplus link= "+a);
      }
    }
*/
/*
    for(Link link : links){
      int a = links.indexOf(link);
      System.out.println("x("+a+")= "+link.x);
    }
*/
//__________________________________________________________________________________________________________
  //I PG(w, A, x)
  // Prune-and-Grow algorithm
    System.out.println("PG");
    System.out.println("I = " + PG.run(eps, links, Ins, Outs, G));


//__________________________________________________________________________________________________________
    System.out.println("END");
  }
}
