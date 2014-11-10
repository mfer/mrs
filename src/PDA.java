import java.util.*;
import java.lang.Double;

public class PDA{



  public static void run(double eps, ArrayList<Link> links,
                          ArrayList<ArrayList<Integer>> Ins,
                          ArrayList<ArrayList<Integer>> Outs){
    int a, iteration=0;
    //∀a ∈ A, x (a) ← 0, y (a) ← 1; τ ← 0;
    for(a=0; a<links.size();a++){
      links.get(a).set_x(0.0);
      links.get(a).set_y(1.0);
    }
    double tau = 0.0;
    double yprice;
    //while max a∈A x (N in D [a] ≥ (1 + ε) τ ) do
    while( Link.get_max_x(links,Ins) >= (1+eps)*tau ){
      System.out.println("Iteration: "+iteration); iteration++;
      System.out.println(Link.get_max_x(links,Ins));

      //a ← arg min a∈A y ( N out D [a] ) / w(a);
//      a = get_min(links,Outs);
      double argmin = Double.POSITIVE_INFINITY;
      for(int i=0; i<links.size();i++){
        yprice = yPrice(i,links,Outs);
        if(yprice < argmin){
          argmin = yprice;
          a = i;
        }
      }
      //τ ← τ + y (N out D [a]) / y(A);
      tau = tau + ((Outs.get(a).size() + 1)/links.size());
//      tau = tau + 1;

      //x (a) ← x (a) + 1;
      double x = links.get(a).x;
      links.get(a).set_x(x + 1);

      //∀b ∈ N out D[a], y(b) ← (1 + ε) y (b) ;
      update_y(links, Outs, a, eps);
    }
  }

  public static double yPrice(int a,
                       ArrayList<Link> links,
                       ArrayList<ArrayList<Integer>> Outs){
    //y can be any positive function
    //for first draft using y as cardinality of N out D [a]
    double y,w;

    y = Outs.get(a).size() + 1;//+1 so a is included
    w = links.get(a).weight;

    return y/w;
  }

  public static void update_y(ArrayList<Link> links,
                       ArrayList<ArrayList<Integer>> Outs,
                       int a, double eps){

      links.get(a).y *= 1 + eps;

      for(int i = 0; i <  Outs.get(a).size(); i++){
        links.get( Outs.get(a).get(i) ).y *= 1 + eps;
      }
  }
}
