//N^in_D,N^out_D get_in_out (D)
// Establish for all links the in and out edges

public class GetInOut{

  static void getInOut(Grafo D,
                       ArrayList<ArrayList<Integer>> Ins,
                       ArrayList<ArrayList<Integer>> Outs){
    // out neighbors are simply the adjacents in graph D

    for(Link link : D.links)
      int linkIndex = D.links.indexOf(link);
      Outs.get(linkIndex).add(D.adjacencia.get(linkIndex));
        for(Integer out : Outs.get(linkIndex)){
          //in neighbors of A are out neighbors of other links
          Ins.get(out).add(linkIndex);
        }
  }
}
