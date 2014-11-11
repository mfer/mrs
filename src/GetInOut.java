import java.util.*;

//N^in_D,N^out_D get_in_out (D)
// Establish for all links the in and out edges

public class GetInOut{

  static void getInOut(Grafo D,
                       ArrayList<ArrayList<Integer>> Ins,
                       ArrayList<ArrayList<Integer>> Outs){
    // out neighbors are simply the adjacents in graph D

    Ins.clear();
    Outs.clear();

    for(int i = 0; i < D.links.size(); i++){
      Ins.add( new ArrayList<Integer>() );
    }

    for(Link link : D.links){
      int linkIndex = D.links.indexOf(link);
      //System.out.println("OUT: adding "+D.adjacencia.get(linkIndex)+" to "+linkIndex);
      Outs.add(D.adjacencia.get(linkIndex));
      for(int out : Outs.get(linkIndex)){
        //in neighbors of A are out neighbors of other links
        //System.out.println("IN: adding "+linkIndex+" to "+out);
        Ins.get(out).add(linkIndex);
      }
    }
  }
}
