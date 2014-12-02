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

    Instance instance = new Instance();


    //IMPORTANT: for epsMin lower than 10^(-10)=0.0000000001 change the String.format below...
    double epsMin = 0.001, epsMax = 0.100, epsStep=0.005; 
    int nLinksMin = 16, nLinksMax = 32;

    int numInstances = Integer.parseInt(args[0]), i;
    
    double minLength=0.1;
    double maxLength=1.0;
    double minZ=1.0;
    double maxZ=1.1;
    double escala=3.0;
    

    for (instance.eps = epsMin; instance.eps <= epsMax;instance.eps=instance.eps+epsStep) {
    for (instance.nLinks = nLinksMin; instance.nLinks <= nLinksMax; instance.nLinks=instance.nLinks*2) {

      String fixed="nLinks_"+instance.nLinks+"__minLength_"+minLength+"__maxLength_"+maxLength+"__minZ_"+minZ+"__maxZ_"+maxZ+"__escala_"+escala;
      String logFile = "../logs/eps_"+String.format("%.10f", instance.eps)+"__"+fixed+".log";
      PrintStream log;
      try {
        log = new PrintStream(logFile);
        log.println("START"); log.println();
        log.println("sol.size totalTime sol");

        DataSeries time = new DataSeries();
        DataSeries ds = new DataSeries();

        for (i = 1; i <= numInstances; i++) {

          instance.filename = fixed+"__instance_"+i;
          instance.setup_DGz_File();

          long startTime = System.currentTimeMillis();
          Set<Integer> sol = instance.run();
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
