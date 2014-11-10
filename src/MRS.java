import java.io.*;
import java.util.*;

public class MRS{

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
        System.out.println(linkIndex+" <-- "+adj);
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
    double eps = 1.0;
    System.out.println("PDA");
    PDA.run(eps, links, Ins, Outs);


//__________________________________________________________________________________________________________
  //I PG(w, A, x)
  // Prune-and-Grow algorithm
    System.out.println("PG");
    System.out.println("I = " + PG.run(links));


//__________________________________________________________________________________________________________
    System.out.println("END");
  }
}
