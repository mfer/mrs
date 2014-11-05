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

        //distancia entre o receiver de link e o sender do adjacente
        double dist_ab = Math.sqrt(Math.abs(link.x_receiver - adjacente.x_sender) *
                              Math.abs(link.x_receiver - adjacente.x_sender) +
                              Math.abs(link.y_receiver - adjacente.y_sender) *
                              Math.abs(link.y_receiver - adjacente.y_sender));
        if(adjacente.interference_radius >= dist_ab){
          // Direcao D de adjacente para link
          D.adjacencia.get(adj).add(linkIndex);
        }else{//estah em conflito, se a direcao nao eh b-->a, entao...
          D.adjacencia.get(linkIndex).add(adj);
        }
      }
    }
    return D;
  }

}
