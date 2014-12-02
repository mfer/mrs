import java.io.*;
import java.util.*;

public class Instance {

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