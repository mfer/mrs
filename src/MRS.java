import java.io.*;
import java.util.*;

import static java.awt.geom.AffineTransform.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;

public class MRS{
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
  private static Set<Integer> PG(Stack<Integer> x, ArrayList<Link> links){
    int i=0, a;
    links.get(0).set_weight(0.0);
    links.get(1).set_weight(1.0);
    links.get(2).set_weight(2.0);
    links.get(3).set_weight(3.0);
    links.get(4).set_weight(4.0);
    links.get(5).set_weight(5.0);
    links.get(6).set_weight(6.0);
    links.get(7).set_weight(7.0);

    
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

        //System.out.println("setting "+a);
        links.get(a).set_weight(links.get(a).w() - links.get(b).w());
        //if w(a) > 0, push a onto S;
        if(links.get(a).w() > 0.0) S.push(new Integer(a));
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

  public static void draw(ArrayList<Link> links, boolean draw_oval){
    JFrame t = new JFrame();
    t.add(new JComponent() {

      private final int ARR_SIZE = 4;

      void drawArrow(Graphics g1, double x1, double y1, double x2, double y2) {
        Graphics2D g = (Graphics2D) g1.create();

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx*dx + dy*dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, len, 0);

        if(draw_oval){
          g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
            new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
        }
      }

      public void paintComponent(Graphics g) {
        for(Link link : links){

          int linkIndex = links.indexOf(link);

          int R = (int) (Math.random( )*256);
          int G = (int)(Math.random( )*256);
          int B= (int)(Math.random( )*256);
          Color randomColor = new Color(R, G, B);
          g.setColor(randomColor);

          int shift = 20;

          drawArrow(g, 100*links.get(linkIndex).sender.x + shift, 
            100*links.get(linkIndex).sender.y + shift,
            100*links.get(linkIndex).receiver.x + shift, 
            100*links.get(linkIndex).receiver.y + shift );          

          g.drawOval((int)(100*links.get(linkIndex).sender.x)-5 + shift, 
            (int)(100*links.get(linkIndex).sender.y-5) + shift,
            10,10);

          if(draw_oval){


            g.drawString(""+linkIndex,
              (int)(100*links.get(linkIndex).sender.x)-5 + shift, 
              (int)(100*links.get(linkIndex).sender.y-5 + shift));

            int r = (int)(200*links.get(linkIndex).sender.distance(links.get(linkIndex).receiver));
            g.drawOval((int)(100*links.get(linkIndex).sender.x)-r/2 + shift, 
              (int)(100*links.get(linkIndex).sender.y-r/2 + shift),
              r,r);
          }
        }       
        
      }
    });

    t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    t.setSize(500, 500);
    t.setVisible(true);
  }

//########################################################################################
  public static void main(String[] args) {
    for(int i = 0;i<args.length;i++) {
      System.out.println(args[i]);
    }

    ArrayList<Link> links = new ArrayList<Link>();
    links.add(new Link(3.0, 3.0, 3.0, 1.0, 2.0));
    links.add(new Link(2.0, 1.0, 4.0, 4.0, 2.0));
    links.add(new Link(0.0, 4.0, 2.0, 2.0, 2.0));
    links.add(new Link(1.0, 2.0, 4.0, 2.0, 2.0));
    links.add(new Link(4.0, 1.0, 0.0, 1.0, 2.0));
    links.add(new Link(2.0, 4.0, 3.0, 2.0, 2.0));
    links.add(new Link(0.0, 0.0, 1.0, 1.0, 2.0));
    links.add(new Link(3.0, 0.0, 0.0, 3.0, 2.0));

//  draw(links);
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
    //and at Link as the set_weight method called by th constructor.
    System.out.println("link_into_graph");
    GrafoRadial DGz = new GrafoRadial(links);

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
    System.out.println("I = " + PG(x, links));


//__________________________________________________________________________________________________________
    System.out.println("END");
  }
}
