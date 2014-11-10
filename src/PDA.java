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
    
    //while max a∈A x (N in D [a] ≥ (1 + ε) τ ) do
    while( Link.get_max_x(links,Ins) >= (1+eps)*tau ){
      System.out.println("Iteration: "+iteration); iteration++;
      System.out.println(Link.get_max_x(links,Ins));

      //a ← arg min a∈A y ( N out D [a] ) / w(a);
      a = get_min(links,Outs);
      System.out.println("a= "+a);
      
      //τ ← τ + y (N out D [a]) / y(A);
      tau = tau + yNoutD(a,links,Outs)/yA(links);
      System.out.println("yNoutD= "+yNoutD(a,links,Outs)+" yA= "+yA(links)+" tau= "+tau);

      
      //x (a) ← x (a) + 1;
      double x = links.get(a).x;
      links.get(a).set_x(x + 1);

      System.out.println("x("+a+")= "+links.get(a).x);
      
      //∀b ∈ N out D[a], y(b) ← (1 + ε) y (b) ;
      update_y(links, Outs, a, eps);
      
    }
  }

  private static int get_min(ArrayList<Link> links,
                       ArrayList<ArrayList<Integer>> Outs){
    int a = 0;
    double argmin = yNoutD(0,links,Outs)/links.get(0).weight;
    

    for(int i=0; i<links.size();i++){
      double yprice = yNoutD(i,links,Outs)/links.get(i).weight;
      System.out.println("i= "+i+" yNoutD= "+yNoutD(i,links,Outs)+" w= "+links.get(i).weight);
      System.out.println("  yprice= "+yprice);
      if(yprice < argmin){
        argmin = yprice;
        a = i;
      }
    }
    return a;
  }

  public static double yNoutD(int a, ArrayList<Link> links,
                        ArrayList<ArrayList<Integer>> Outs){

    double ySum=links.get(a).y;
    for(int j = 0; j <  Outs.get(a).size(); j++){
      //System.out.println("a "+a+" adj "+Outs.get(a).get(j)+" y "+links.get( Outs.get(a).get(j) ).y);
      ySum = ySum + links.get( Outs.get(a).get(j) ).y;
    }
    //System.out.println("ySum("+a+")= "+ySum);
    return ySum;
  }

  public static double yA(ArrayList<Link> links){

    double ySum=0.0;
    for(int i = 0; i <  links.size(); i++){
      ySum = ySum + links.get(i).y;
    }

    return ySum;
  }


  public static void update_y(ArrayList<Link> links,
                       ArrayList<ArrayList<Integer>> Outs,
                       int a, double eps){

      double y = links.get(a).y;
      links.get(a).set_y(y*(1+eps));
      //System.out.println("y("+a+")= "+links.get(a).y);

      for(int i = 0; i < Outs.get(a).size(); i++){
        links.get(i).set_y(links.get(i).y*(1+eps));
        //System.out.println("y("+Outs.get(a).get(i)+")= "+links.get(i).y);
      }
  }
}
