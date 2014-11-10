import java.util.*;

public class PDA{

  public static void run(double eps, ArrayList<Link> links, ArrayList<ArrayList<Integer>> Ins, ArrayList<ArrayList<Integer>> Outs){
    int a, iteration=0;
    //∀a ∈ A, x (a) ← 0, y (a) ← 1; τ ← 0;
    for(a=0; a<links.size();a++){
      links.get(a).set_x(0.0);
      links.get(a).set_y(1.0);
    }
    double tau = 0.0;

    //while max a∈A x (N in D [a] ≥ (1 + ε) τ ) do
    while( Link.get_max_x(links,Ins) >= (1+eps)*tau ){
      System.out.println("Iteration: "+iteration); iteration++;
      System.out.println(Link.get_max_x(links,Ins));
      //a ← arg min a∈A y ( N out D [a] ) / w(a); 
//      a = get_min(links,Outs);
      //τ ← τ + y (N out D [a]) / y(A);
//      tau = tau + get_y_sum(links, Outs, a)/get_y_sum(links);
      tau = tau + 1;
      //x (a) ← x (a) + 1;
//      links.get(0).set_x(0.0);
      //∀b ∈ N out D[a], y(b) ← (1 + ε) y (b) ;
//      update_y(links, Outs, a);
    }
  }
}