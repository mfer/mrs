import java.io.*;
import java.util.*;

/*
*   call me like that:
*   make simulation FILENAME=wolfram nLinks=10 minLength=0.1 maxLength=1.0 minZ=0.1 maxZ=1.0 escala=1.0
*/

public class Simulation{

  public static void main(String[] args) throws FileNotFoundException, IOException {
    if(args.length<1){
      System.out.println("Usage:");
      System.out.println("  java Simulation <filename>");
      System.out.println("You can generate the <filename> using:");
      System.out.println("  python gerador.py filename nLinks minLength maxLength minZ maxZ escala");
      System.exit(0);
    }

    Grafo G = InstanceReader.readInstance(args[0]);

    GrafoRadial.draw(G.links, true);

    Grafo D = Orientate.edge(G);
    ArrayList<ArrayList<Integer>> Ins = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> Outs = new ArrayList<ArrayList<Integer>>();
    GetInOut.getInOut(D, Ins, Outs);
    double eps = 0.15;
    PDA.run(eps, G.links, Ins, Outs);
    System.out.println("I = " + PG.run(eps, G.links, Ins, Outs, G));
  }
}
