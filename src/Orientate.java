// D orientate_edge (G)
// Assign an orientation on the conflict edges (needs more study on how to do)
/*

  c > 1
  c --> constant
  sender interference radius >= c * link length

  consider links a and b

  if a receiver in b sender inteference
  orientation b-->a

  ties are broken arbitrarily


*/
public class Orientate{

  static Grafo D;

  static Grafo edge(Grafo G){
    D = new Grafo(G.links.size(),false);
    //for each node N in G
    for(Link link : G.links){
      //for each adjacent M of N
      D.links.add(link);
      int linkIndex = G.links.indexOf(link);      
      for(Integer adj : G.adjacencia.get(linkIndex)){
        Link adjacente = G.links.get(adj);
        //System.out.print(linkIndex+" "+adj+" ");
        if(adjacente.interference_radius >= 
            link.receiver.distance(adjacente.sender)){
          // Direcao D de adjacente para link
          D.adjacencia.get(adj).add(linkIndex);          
          //System.out.print("out");
        }else{//estah em conflito, se a direcao nao eh b-->a, entao...
          D.adjacencia.get(linkIndex).add(adj);
          //System.out.print("in");
        }
        //System.out.println();
      }
    }
    return D;
  }

}
