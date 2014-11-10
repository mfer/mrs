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
      Outs.add( new ArrayList<Integer>() );
    }

    for(Link link : D.links){
      int linkIndex = D.links.indexOf(link);
      Ins.add(D.adjacencia.get(linkIndex));
      for(int out : Ins.get(linkIndex)){
        //in neighbors of A are out neighbors of other links
        Outs.get(out).add(linkIndex);
      }
    }
  }
}
