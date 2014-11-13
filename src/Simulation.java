import java.io.*;
import java.util.*;
/*
*   call me like that:
*   make simulation FILENAME=random nLinks=10 minLength=0.1 maxLength=1.0 minZ=0.1 maxZ=1.0 escala=1.0
*/

public class Simulation {
  private static Set<Integer> run(String[] args, double eps)  throws FileNotFoundException, IOException{
    Link.nlinks=0;
    Grafo G = InstanceReader.readInstance(args[0]);
    //GrafoRadial.draw(G.links, true);
    Grafo D = Orientate.edge(G);
    ArrayList<ArrayList<Integer>> Ins = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> Outs = new ArrayList<ArrayList<Integer>>();
    GetInOut.getInOut(D, Ins, Outs);

    System.out.println("prePDA");
    PDA.run(eps, G.links, Ins, Outs);
    System.out.println("posPDA");

    return PG.run(eps, G.links, Ins, Outs, G);
  }

  /**
   * @param args
   */
  public static void main(String[] args) throws FileNotFoundException, IOException{
    if(args.length<1){
      System.out.println("Usage:");
      System.out.println("  java Simulation <filename>");
      System.out.println("You can generate the <filename> using:");
      System.out.println("  python gerador.py filename nLinks minLength maxLength minZ maxZ escala");
      System.exit(0);
    }

    double eps = 0.5;
    int numRuns = 2;

    String logFile = "log_file.txt";
    PrintStream log;
    try {
      log = new PrintStream(logFile);
      log.println("START"); log.println();

        DataSeries ds = new DataSeries();
        int r;
        for (r = 0; r < numRuns; r++)
        {
            ds.addSample(r);
            Set<Integer> sol = run(args, eps);
            System.out.println(sol.size());
            System.out.println(sol);
        }

      log.println("END");
      log.println("MEAN, VAR, STD: "+ds.getMean()+", "+
        ds.getVariance()+", "+ds.getStandardDeviation());



    } catch (FileNotFoundException e) {
      e.printStackTrace();
      System.exit(1);
    }

  }
}
