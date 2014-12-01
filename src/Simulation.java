import java.io.*;
import java.util.*;
/*
*   call me like that:
*      make simulation INSTANCES=<number_of_instances>
*   before generate the samples:
*      instance_generator.sh nLinks minLength maxLength minZ maxZ escala
*      ./instance_generator.sh 10 0.1 1.0 1.0 2.0 2.0
*/

public class Simulation {
  private static Set<Integer> run(String filename, double eps)  throws FileNotFoundException, IOException{
    Link.nlinks=0;
    Grafo G = InstanceReader.readInstance("./instances/"+filename);
    //GrafoRadial.draw(G.links, true);
    Grafo D = Orientate.edge(G);
    ArrayList<ArrayList<Integer>> Ins = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> Outs = new ArrayList<ArrayList<Integer>>();
    GetInOut.getInOut(D, Ins, Outs);
    PDA.run(eps, G.links, Ins, Outs);
//    return PG.run(eps, G.links, Ins, Outs, G);

    Set<Integer> I = new TreeSet<Integer>();
    return I;
  }

  /**
  * @param args
  */
  public static void main(String[] args) throws FileNotFoundException, IOException{
    if(args.length<1){
      System.out.println("Usage:");
      System.out.println("  java Simulation <number_of_instances>");
      System.out.println("You should generate the instances before using:");
      System.out.println("  python gerador.py filename nLinks minLength maxLength minZ maxZ escala");
      System.exit(0);
    }


    int nLinks;    
    //IMPORTANT: for epsMin lower than 10^(-10)=0.0000000001 change the String.format below...
    double eps, epsMin = 0.001, epsMax = 0.100, epsStep=0.01; 

    int numInstances = Integer.parseInt(args[0]), i;
    int numLinks = 32;
    double minLength=0.1;
    double maxLength=1.0;
    double minZ=1.0;
    double maxZ=1.1;
    double escala=3.0;
    

    for (eps = epsMin; eps <= epsMax;eps=eps+epsStep) {
    for (nLinks = 16; nLinks <= numLinks; nLinks=nLinks*2) {

      String filename;
      String fixed="nLinks_"+nLinks+"__minLength_"+minLength+"__maxLength_"+maxLength+"__minZ_"+minZ+"__maxZ_"+maxZ+"__escala_"+escala;
      String logFile = "./logs/eps_"+String.format("%.10f", eps)+"__"+fixed+".log";
      PrintStream log;
      try {
        log = new PrintStream(logFile);
        log.println("START"); log.println();
        log.println("sol.size totalTime sol");

        DataSeries time = new DataSeries();
        DataSeries ds = new DataSeries();

        for (i = 1; i <= numInstances; i++) {

          filename = fixed+"__instance_"+i;
          long startTime = System.currentTimeMillis();
          Set<Integer> sol = run(filename, eps);
          long endTime   = System.currentTimeMillis();
          long totalTime = endTime - startTime;

          log.println(sol.size()+" "+totalTime+" "+sol);

          time.addSample(totalTime);
          ds.addSample(sol.size());
        }

          log.println("END");
          log.println("TIME MEAN, VAR, STD: "+time.getMean()+", "+
            time.getVariance()+", "+time.getStandardDeviation());
          log.println("ISSIZE MEAN, VAR, STD: "+ds.getMean()+", "+
            ds.getVariance()+", "+ds.getStandardDeviation());

      } catch (FileNotFoundException e) {
        e.printStackTrace();
        System.exit(1);
      }

    }
  }
  }
}
