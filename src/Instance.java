import java.io.*;
import java.util.*;

public class Instance {
  int n; //Number of copies of this type of Instance
  String filename;
  double eps;
  int nLinks;

  double minLength=0.1;
  double maxLength=1.0;
  double minZ=1.0;
  double maxZ=1.1;
  double escala=3.0;
  String fixed;
  int current;

  Grafo DGz, D;
  ArrayList<ArrayList<Integer>> Ins, Outs;
  Set<Integer> IS;

  ArrayList<Link> B_links;
  ArrayList<ArrayList<Integer>> B_Ins;
  ArrayList<ArrayList<Integer>> B_Outs;
  Stack<Integer> S;
  Set<Integer> I;



  private void setCurrent(int i){
    current=i;
  }

  public void setFixed(){
    fixed="nLinks_"+nLinks+"__minLength_"+minLength+"__maxLength_"+maxLength+"__minZ_"+minZ+"__maxZ_"+maxZ+"__escala_"+escala;
  }

  public String getFixed(){
    return fixed;
  }

  public void setFilename(int i){
    setFixed();
    setCurrent(i);
    filename = "../instances/"+fixed+"__instance_"+current;
  }

  public String getFilename(){
    return filename;
  }

  public void create_DGz_File(int i) throws IOException{
    try{
      Process p = Runtime.getRuntime().exec("python ../gerador.py "+filename+" "+nLinks+" "+minLength+" "+maxLength+" "+minZ+" "+maxZ+" "+escala);
      BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
    }catch(Exception e){}

    try {
        Thread.sleep(1000);                 //1000 milliseconds is one second waiting for the above file generation.
    } catch(InterruptedException ex) {
        Thread.currentThread().interrupt();
    }
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
    Link.nlinks=0; //this line is a terrible cheat to make the damn PDA.xNinD works well...

    ArrayList<Link> links=new ArrayList<Link>();
    ArrayList<Link> linksWithAllRates=new ArrayList<Link>();

    for(int i=0;i<nLinks;i++){
      //System.out.print(i+1+" ");
      Link newestLink = new Link("802_11_b");
      links.add(newestLink);
      linksWithAllRates.addAll(newestLink.getAllRates());
    }

      for(Link l : links){
        System.out.println(l.dataRate+" "+l.radius);
      }

    GrafoRadial.draw(links, true,  800.0 / (double) Link.MAPSIZE );

    DGz = new Grafo(links);
    //DGz = new Grafo(n,true);
    //DGz.links = links;

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
    PDA.run(eps, DGz.links,Ins,Outs);

    B_links = new ArrayList<Link>(D.links);
    B_Ins = new ArrayList<ArrayList<Integer>>(Ins);
    B_Outs = new ArrayList<ArrayList<Integer>>(Outs);
    S = new Stack<Integer>();
    I = new TreeSet<Integer>();

    return PG.run(eps, DGz.links,Ins,Outs,DGz,B_links,B_Ins,B_Outs,S,I);
  }
}