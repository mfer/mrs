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
    while( get_max_x(links,Ins) >= (1+eps)*tau ){
      iteration++;
//      System.out.println("Iteration: "+iteration); 
//      System.out.println("  "+get_max_x(links,Ins)+" >= "+(1+eps)*tau);

      //a ← arg min a∈A y ( N out D [a] ) / w(a);
      a = get_min(links,Outs);
//      System.out.println("  a= "+a);
      
      //τ ← τ + y (N out D [a]) / y(A);
      tau = tau + yNoutD(a,links,Outs)/yA(links);
//      System.out.println("  yNoutD= "+yNoutD(a,links,Outs)+" yA= "+yA(links)+" tau= "+tau);

      
      //x (a) ← x (a) + 1;
      double x = links.get(a).x;
      links.get(a).set_x(x + 1);
      
      //∀b ∈ N out D[a], y(b) ← (1 + ε) y (b) ;
      update_y(links, Outs, a, eps);

    }

//    for(Link link : links){
//      System.out.println("x("+links.indexOf(link)+")= "+link.x);
//    }
//    System.out.println("Iterations= "+iteration);
  }

  private static double get_max_x(ArrayList<Link> links,
                       ArrayList<ArrayList<Integer>> Ins){
    int a = 0;
    double argmax = xNinD(0,links,Ins);    

    for(int i=1; i<links.size();i++){
      double xSum = xNinD(links.get(i).id,links,Ins);
      if(xSum > argmax){
        argmax = xSum;
      }
    }

    return argmax;    
  }

  public static double xNinD(int a, ArrayList<Link> links,
                        ArrayList<ArrayList<Integer>> Ins){

    double xSum=links.get(a).x;

    try {
      //System.out.println("a "+a+" adj ");
      
      for(int j = 0; j < Ins.get(a).size(); j++){
        //System.out.print(Ins.get(a).get(j)+"com x "+links.get( Ins.get(a).get(j) ).x+" ");

        //pegando o x dos links q tem os indices em Ins.get(a)
        xSum = xSum + links.get( Ins.get(a).get( links.get(j).id ) ).x;
      }
      //System.out.println();
      //System.out.println("xSum("+a+")= "+xSum);
      
    } catch (IndexOutOfBoundsException e) {

        System.err.println("Caught IndexOutOfBoundsException: "
                           +  e.getMessage());
                                 
    }

    return xSum;
  }

  private static int get_min(ArrayList<Link> links,
                       ArrayList<ArrayList<Integer>> Outs){
    int a = links.get(0).id;
    double argmin = yNoutD(a,links,Outs)/links.get(a).weight;
    

    for(int i=1; i<links.size();i++){
      int b = links.get(i).id;
      double yprice = yNoutD(b,links,Outs)/links.get(b).weight;
      //System.out.println("i= "+i+" yNoutD= "+yNoutD(i,links,Outs)+" w= "+links.get(i).weight);
      //System.out.println("  yprice= "+yprice);
      if(yprice < argmin){
        argmin = yprice;
        a = b;
      }
    }
    return a;
  }

  public static double yNoutD(int a, ArrayList<Link> links,
                        ArrayList<ArrayList<Integer>> Outs){

    double ySum=links.get(a).y;
    for(int j = 0; j <  Outs.get(a).size(); j++){
      //System.out.println("a "+a+" adj "+Outs.get(a).get(j)+" y "+links.get( Outs.get(a).get(j) ).y);

      ySum = ySum + links.get( Outs.get(a).get(links.get(j).id) ).y;
    }
    //System.out.println("ySum("+a+")= "+ySum);
    return ySum;
  }

  public static double yA(ArrayList<Link> links){

    double ySum=0.0;
    for(int i = 0; i <  links.size(); i++){
      ySum = ySum + links.get(links.get(i).id).y;
    }

    return ySum;
  }


  public static void update_y(ArrayList<Link> links,
                       ArrayList<ArrayList<Integer>> Outs,
                       int a, double eps){

      double y = links.get(a).y;
      links.get(a).set_y(y*(1+eps));
      //System.out.println("    y("+a+")= "+links.get(a).y);

      for(int i = 0; i < Outs.get(a).size(); i++){
        int b = links.get(i).id;
        links.get(b).set_y(links.get(b).y*(1+eps));
        //System.out.println("    y("+Outs.get(a).get(b)+")= "+links.get(b).y);
      }      
      //System.out.println();
      //**************************************************************************
      // TODO: understand it is right the y goes to infinity...
      //**************************************************************************      
  }
}
