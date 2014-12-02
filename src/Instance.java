import java.io.*;
import java.util.*;

public class Instance {
  int n; //Number of copies of this type of Instance
  String filename;
  double eps;
  int nLinks;    


  Grafo DGz, D;
  ArrayList<ArrayList<Integer>> Ins, Outs;
  Set<Integer> IS;

  ArrayList<Link> B_links;
  ArrayList<ArrayList<Integer>> B_Ins;
  ArrayList<ArrayList<Integer>> B_Outs;
  Stack<Integer> S;
  Set<Integer> I;    


  public String create_DGz_File() throws IOException{
    double minLength=0.1;
    double maxLength=1.0;
    double minZ=1.0;
    double maxZ=1.1;
    double escala=3.0;
    String fixed="nLinks_"+nLinks+"__minLength_"+minLength+"__maxLength_"+maxLength+"__minZ_"+minZ+"__maxZ_"+maxZ+"__escala_"+escala;

    for(int i=1; i<=n; i++){
      try{
        String fn="../instances/"+fixed+"__instance_"+i;
        Process p = Runtime.getRuntime().exec("python ../gerador.py "+fn+" "+nLinks+" "+minLength+" "+maxLength+" "+minZ+" "+maxZ+" "+escala);
        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));        
      }catch(Exception e){}

      try {
          Thread.sleep(1000);                 //1000 milliseconds is one second.
      } catch(InterruptedException ex) {
          Thread.currentThread().interrupt();
      }    
    }
    return fixed;        
  }

  public void setup_DGz_File(){

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

  public void setup(){
    Link.nlinks=0; //this line is terrible cheat to make the damn PDA.xNinD works well...

    ArrayList<Link> links=new ArrayList<Link>();
    ArrayList<Link> linksWithAllRates=new ArrayList<Link>();

/*
    for(int i=0;i<nLinks;i++){
      Link newestLink;
      newestLink = new Link();
      
      links.add(newestLink);
      //Add link with all datarates - 802.11b
      if(inputTipo == 0){
        linksWithAllRates.add(new Link(newestLink.receiver.x, newestLink.receiver.y, newestLink.sender.x, newestLink.sender.y, 4.0));
        linksWithAllRates.add(new Link(newestLink.receiver.x, newestLink.receiver.y, newestLink.sender.x, newestLink.sender.y, 6.0));
        linksWithAllRates.add(new Link(newestLink.receiver.x, newestLink.receiver.y, newestLink.sender.x, newestLink.sender.y, 8.0));
        linksWithAllRates.add(new Link(newestLink.receiver.x, newestLink.receiver.y, newestLink.sender.x, newestLink.sender.y, 10.0));
      } else {
      //Add link with all datarates - 802.11n
        linksWithAllRates.add(new Link(newestLink.receiver.x, newestLink.receiver.y, newestLink.sender.x, newestLink.sender.y, 14.0));
        linksWithAllRates.add(new Link(newestLink.receiver.x, newestLink.receiver.y, newestLink.sender.x, newestLink.sender.y, 17.0));
        linksWithAllRates.add(new Link(newestLink.receiver.x, newestLink.receiver.y, newestLink.sender.x, newestLink.sender.y, 19.0));
        linksWithAllRates.add(new Link(newestLink.receiver.x, newestLink.receiver.y, newestLink.sender.x, newestLink.sender.y, 22.0));
        linksWithAllRates.add(new Link(newestLink.receiver.x, newestLink.receiver.y, newestLink.sender.x, newestLink.sender.y, 26.0));
        linksWithAllRates.add(new Link(newestLink.receiver.x, newestLink.receiver.y, newestLink.sender.x, newestLink.sender.y, 30.0));
        linksWithAllRates.add(new Link(newestLink.receiver.x, newestLink.receiver.y, newestLink.sender.x, newestLink.sender.y, 31.0));
        linksWithAllRates.add(new Link(newestLink.receiver.x, newestLink.receiver.y, newestLink.sender.x, newestLink.sender.y, 32.0));
      //links.get(i).radius = 4;
      }
    }    
*/
    DGz = new GrafoRadial(links);

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
    PDA.run(eps, D.links,Ins,Outs);  

    B_links = new ArrayList<Link>(D.links);
    B_Ins = new ArrayList<ArrayList<Integer>>(Ins);
    B_Outs = new ArrayList<ArrayList<Integer>>(Outs);
    S = new Stack<Integer>();
    I = new TreeSet<Integer>();

    return PG.run(eps, DGz.links,Ins,Outs,DGz,B_links,B_Ins,B_Outs,S,I);
  }
}