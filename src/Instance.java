import java.io.*;
import java.util.*;

public class Instance {

  String filename;
  double eps;

  Grafo DGz, D;
  ArrayList<ArrayList<Integer>> Ins, Outs;
  Set<Integer> IS;

  ArrayList<Link> B_links;
  ArrayList<ArrayList<Integer>> B_Ins;
  ArrayList<ArrayList<Integer>> B_Outs;
  Stack<Integer> S;
  Set<Integer> I;    

  public void setup(){
    Link.nlinks=0; //this line is terrible cheat to make the damn PDA.xNinD works well...
    try{
      DGz = InstanceReader.readInstance("../instances/"+filename);      
    }catch(IOException e){
      System.out.println("Got an IOException: " + e.getMessage());
    }    
    Ins = new ArrayList<ArrayList<Integer>>();
    Outs = new ArrayList<ArrayList<Integer>>();    
    for(int i = 0; i < DGz.links.size(); i++){
      Ins.add(new ArrayList<Integer>());
      Outs.add(new ArrayList<Integer>());        
    }    
  }

  public Set<Integer> run() throws FileNotFoundException, IOException{

    //GrafoRadial.draw(DGz.links, true);

    D = Orientate.edge(DGz);
    GetInOut.getInOut(D,Ins,Outs);

    PDA.run(eps, D.links,Outs,Ins);  

    B_links = new ArrayList<Link>(D.links);
    B_Ins = new ArrayList<ArrayList<Integer>>(Ins);
    B_Outs = new ArrayList<ArrayList<Integer>>(Outs);
    S = new Stack<Integer>();
    I = new TreeSet<Integer>();

    return PG.run(eps, DGz.links,Outs,Ins,DGz,B_links,B_Outs,B_Ins,S,I);
  }
}