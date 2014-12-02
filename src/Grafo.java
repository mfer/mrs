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

    //make the graph for the unidirectional model using a given set of links
    public Grafo (ArrayList<Link> links){
      int i,j;
      this.links = links;
      this.adjacencia = new ArrayList<ArrayList<Integer>>();
      this.bidirecional = false;

      for(i=0;i<links.size();i++){
        this.adjacencia.add( new ArrayList<Integer>() );

        for(j=i+1;j<links.size();j++){
          //System.out.print(i + "  " + j + ":");
          if( links.get(i).intersects(links.get(j)) ){
            //System.out.println(" instersects");
            setAdjacencia(i,j);
          }
          //System.out.println();
        }
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
