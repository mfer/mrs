import java.io.*;
import java.util.*;

public class MRS{

  //w,DG_z link_into_graph (L)
  // Construct the Disk Graph DGz, definition-4.4 from olga:2012, using the set of links L
  // And the weight w for the links using the data-rates

  //G,A graph_into_protocol (DGz)
  // Construct the conflict graph G and the vertex set A from DGz

  //D orientate_edge (G)
  // Assign an orientation on the conflict edges (needs more study on how to do)

  //N^in_D,N^out_D get_in_out (D)
  // Establish for all links the in and out edges

  //x PDA(w, N^in_D, N^out_D, eps)
  // Price-Directive Algorithm

  private static boolean IS(Set<Integer> C){
    //return true if the set is independent, false otherwise
    //implement this
    return true;
  }

  //I PG(w, A, x)
  // Prune-and-Grow algorithm
  private static Set<Integer> PG(){
    int i=0, a;
    ArrayList<Double> w = new ArrayList<Double>();
    w.add(0.0);
    w.add(1.0);
    w.add(2.0);
    w.add(3.0);
    w.add(4.0);
    w.add(5.0);
    
    System.out.println("Prune-Phase");
      //B ← A, S ← ∅; //S is a stack
      Set<Integer> B = new TreeSet<Integer>();
      B.addAll(Arrays.asList(1,2,3,4,5));
      Stack<Integer> S = new Stack<Integer>();
      while(!B.isEmpty()){
//      System.out.println(B);
        //a ← a x-surplus link of B;
        a = i;
        //B ← B ∖ {a};
        B.removeAll(Arrays.asList(a));
        //w(a) ← w (a) − w (S ∩ N (a));
        int b = 0;
        w.set(a,w.get(a) - w.get(b));
//        System.out.println(a+" "+w.get(a));
        //if w(a) > 0, push a onto S;
        if(w.get(a) > 0.0 ) S.push(new Integer(a));
        i++;
      }

    System.out.println("Grow-Phase");
      //I ← ∅;
      Set<Integer> I = new TreeSet<Integer>();    
      //while S ∕= ∅,
      while(!S.empty()){
        //pop the top link a from S;
        a = S.pop();
        System.out.println("pop "+a);
        
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

  public static void main(String[] args) {
    for(int i = 0;i<args.length;i++) {
      System.out.println(args[i]);
    }

    ArrayList<Link> links = new ArrayList<Link>();
    links.add(new Link(0.0,0.0,2.0,0.0, 1.0));
    links.add(new Link(2.0,1.0,2.0,3.0, 1.0));
/*
    System.out.println(links.size());
    for(int i = 0;i<links.size();i++) {
      System.out.println("link: " + i);
      System.out.println("    sender:   (" + links.get(i).x_sender + ", " + links.get(i).y_sender + ")");
      System.out.println("    receiver: (" + links.get(i).x_receiver + ", " + links.get(i).y_receiver + ")");
      System.out.println("    size: " + links.get(i).length);
    }    
*/

    System.out.println("link_into_graph");

    System.out.println("graph_into_protocol");

    System.out.println("orientate_edge");

    System.out.println("get_in_out");

    System.out.println("PDA");

    System.out.println("PG");
    System.out.println("I = " + PG());
  }
}