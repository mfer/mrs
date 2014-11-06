import java.io.*;
import java.util.*;

public class MRS{
  //w,DG_z link_into_graph (L)
  // Construct the Disk Graph DGz nd the weight w for the links using the data-rates
  // definition-4.4 from olga:2012, using the set of links L
  static GrafoRadial link_into_graph(ArrayList<Link> links, ArrayList<Double> w){
    GrafoRadial DGz = new GrafoRadial(10,false);

    for(int i = 0; i < links.size(); i++){

      System.out.println(i+" "+links.get(i).interference_radius);
      w.add(links.get(i).interference_radius);
    }

    return DGz;    
  }

//########################################################################################
  //G,A graph_into_protocol (DGz)
  // Construct the conflict graph G and the vertex set A from DGz
  static Grafo graph_into_protocol(){
    Grafo G = new Grafo(10,false);;
    return G;
  }

//########################################################################################
  //D orientate_edge (G)
  // Assign an orientation on the conflict edges (needs more study on how to do)
  static Grafo edge(Grafo G){
    Grafo D;
    D = new Grafo(G.links.size(),false);
    //for each node N in G
    for(Link link : G.links){
      //for each adjacent M of N
      D.links.add(link);
      int linkIndex = G.links.indexOf(link);
      for(Integer adj : G.adjacencia.get(linkIndex)){

        Link adjacente = G.links.get(adj);

        //distancia entre o receiver de link e o sender do adjacente
        double dist_ab = Math.sqrt(Math.abs(link.x_receiver - adjacente.x_sender) *
                              Math.abs(link.x_receiver - adjacente.x_sender) +
                              Math.abs(link.y_receiver - adjacente.y_sender) *
                              Math.abs(link.y_receiver - adjacente.y_sender));
        if(adjacente.interference_radius >= dist_ab){
          // Direcao D de adjacente para link
          D.adjacencia.get(adj).add(linkIndex);
        }else{//estah em conflito, se a direcao nao eh b-->a, entao...
          D.adjacencia.get(linkIndex).add(adj);
        }
      }
    }
    return D;
  }

//########################################################################################
  //N^in_D,N^out_D get_in_out (D)
  // Establish for all links the in and out edges
  static void getInOut(Grafo D,
                       ArrayList<ArrayList<Integer>> Ins,
                       ArrayList<ArrayList<Integer>> Outs){
    // out neighbors are simply the adjacents in graph D

    Ins.clear();
    Outs.clear();

    for(Link link : D.links){
      int linkIndex = D.links.indexOf(link);
      Outs.add(D.adjacencia.get(linkIndex));
        for(Integer out : Outs.get(linkIndex)){
          //in neighbors of A are out neighbors of other links
          Ins.get(out).add(linkIndex);
        }
    }
  }

//########################################################################################
  //x PDA(w, N^in_D, N^out_D, eps)
  // Price-Directive Algorithm
  private static Stack<Integer> PDA(double eps){
    //∀a ∈ A, x (a) ← 0, y (a) ← 1; τ ← 0;
    Stack<Integer> x = new Stack<Integer>();

    //while max a∈A x (N in D [a] ≥ (1 + ε) τ ) do
      //a ← arg min a∈A y ( N out D [a] ) / w(a); 
      //τ ← τ + N out D [a] / w(a);
      //x (a) ← x (a) + 1;
      x.push(new Integer(4));
      //∀b ∈ N out D[a], y(b) ← (1 + ε) y (b) ;            


    x.push(new Integer(4));
    x.push(new Integer(5));
    x.push(new Integer(1));
    x.push(new Integer(3));
    x.push(new Integer(6));
    x.push(new Integer(7));
    x.push(new Integer(2));
    //return x.
    return x;
  }

//########################################################################################
  private static boolean IS(Set<Integer> C){
    //return true if the set is independent, false otherwise
    //implement this
    return true;
  }

  //I PG(w, A, x)
  // Prune-and-Grow algorithm
  private static Set<Integer> PG(Stack<Integer> x){
    int i=0, a;
    ArrayList<Double> w = new ArrayList<Double>();
    w.add(0.0);
    w.add(1.0);
    w.add(2.0);
    w.add(3.0);
    w.add(4.0);
    w.add(5.0);
    w.add(6.0);
    w.add(7.0);
    
    System.out.println("Prune-Phase");
      //B ← A, S ← ∅; //S is a stack
      Set<Integer> B = new TreeSet<Integer>();
      B.addAll(Arrays.asList(1,2,3,4,5));
      Stack<Integer> S = new Stack<Integer>();
      while(!B.isEmpty()){
        //a ← a x-surplus link of B;
        a = x.pop();
        //B ← B ∖ {a};
        B.removeAll(Arrays.asList(a));
        //w(a) ← w (a) − w (S ∩ N (a));
        int b = 0;
        w.set(a,w.get(a) - w.get(b));
        System.out.println(a+" "+w.get(a));
        //if w(a) > 0, push a onto S;
        if(w.get(a) > 0.0) S.push(new Integer(a));
        i++;
      }

    System.out.println("Grow-Phase");
      //I ← ∅;
      Set<Integer> I = new TreeSet<Integer>();
      //while S ∕= ∅,
      while(!S.empty()){
        //pop the top link a from S;
        a = S.pop();
//        System.out.println("pop "+a);

        //if I ∪ {a} is independent, I ← I ∪ {a};
        Set<Integer> Sa = new TreeSet<Integer>();
        Sa.add(a);
        Set<Integer> union = new TreeSet<Integer>();
        union.addAll(I);
        union.addAll(Sa);
        if(IS(union)) {
          I.clear();
          I.addAll(union);
        }
      }

    //return I.    
    return I;
  }

//########################################################################################
  public static void main(String[] args) {
    for(int i = 0;i<args.length;i++) {
      System.out.println(args[i]);
    }

    ArrayList<Link> links = new ArrayList<Link>();
    links.add(new Link(0.0,0.0,2.0,0.0, 2.0));
    links.add(new Link(2.0,1.0,2.0,3.0, 4.0));    
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
    GrafoRadial DGz;
    ArrayList<Double> w = new ArrayList<Double>();
    System.out.println("link_into_graph");
    DGz = link_into_graph(links, w);

    for(int i = 0;i<links.size();i++) {
      System.out.println("  link: " + i);
      System.out.println("    w:   " + w.get(i));
    }    


//__________________________________________________________________________________________________________
    Grafo G;
    System.out.println("graph_into_protocol");
    G = graph_into_protocol();


//__________________________________________________________________________________________________________
    Grafo D;
    System.out.println("orientate_edge");
    D = edge(G);


//__________________________________________________________________________________________________________
    System.out.println("get_in_out");
    ArrayList<ArrayList<Integer>> Ins = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> Outs = new ArrayList<ArrayList<Integer>>();
    getInOut(D, Ins, Outs);


//__________________________________________________________________________________________________________
    double eps = 1.0;
    System.out.println("PDA");
    Stack<Integer> x = PDA(eps);


//__________________________________________________________________________________________________________
    System.out.println("PG");
    System.out.println("I = " + PG(x));


//__________________________________________________________________________________________________________
    System.out.println("END");
  }
}
