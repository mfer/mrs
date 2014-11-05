import java.util.*;

public class Grafo{

    ArrayList<Link> links;
    ArrayList<ArrayList<Integer>> adjacencia;
    boolean bidirecional;

    public Grafo (int nLinks, boolean bidirecional){

      this.links = new ArrayList<Link>();
      this.adjacencia = new ArrayList<ArrayList<Integer>>();
      this.bidirecional = bidirecional;

      for(int i = 0; i < nLinks; i++){

        this.adjacencia.add( new ArrayList<Integer>() );
      }
    }

    public void setAdjacencia(int linkId1, int linkId2){
      //se o grafo for bidirecional,linkId1 < linkId2
      //ou seja, a adjacencia será marcada na lista do link de menor id
      if(bidirecional) {
          if(linkId1 > linkId2){
            if(!adjacencia.get(linkId2).contains(linkId1))
              adjacencia.get(linkId2).add(linkId1);
          }
          else if(!adjacencia.get(linkId1).contains(linkId2))
            adjacencia.get(linkId1).add(linkId2);
      }
      else if(!adjacencia.get(linkId1).contains(linkId2)){
        adjacencia.get(linkId1).add(linkId2);
      }
    }
}
