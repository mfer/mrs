import java.util.*;

public class GrafoRadial {

  private void set_weights(ArrayList<Link> links){
    int idx_lx; //link with min(beta_x^(1/alpha)*l_x)
    double len_lx;
    double zMin; //mult constant for link lx
    double ringWidth; //aux var
    
    double betaLenMin = 1.0;//Math.pow(links.get(0).beta, (double)1/(double)Link.ALPHA)*links.get(0).length; //min(beta_x^(1/alpha)*l_x)

    idx_lx = 0;
    for(int i=0;i<links.size();i++){
      double tmp = 1.0; //Math.pow(links.get(i).beta, (double)1/(double)Link.ALPHA)*links.get(i).length;
        if(tmp < betaLenMin) {
          betaLenMin=tmp;
          idx_lx = i;
        }
    }
    len_lx = links.get(idx_lx).length;

    int KinFormula = 1;
    zMin = 1.0;//KinFormula*(Math.pow((double)(links.get(idx_lx).beta * Link.ALPHA * 4.0 * (double)Link.CIDDD/(double)(Link.ALPHA-2)), (double)1.0/(double)Link.ALPHA)); 
    ringWidth = zMin * len_lx/ KinFormula;
    
    double betax = 1.0;//links.get(idx_lx).beta;
    
//      links.get(idx_lx).setRadius(zMin * len_lx);
    
    //System.out.println("idx_lx="+idx_lx+", betaLenMin="+betaLenMin+", len_lx="+
    //    len_lx+", betax="+betax+", zMin="+zMin+", ringW="+
    //    ringWidth + ", rx = "+links.get(idx_lx).radius);
    
    for(int i=0; i < links.size(); i++){
      if (i != idx_lx) {
        double c = 1.0;//Link.CIDDD;
        double li = links.get(i).length;
        double bi = 1.0;//links.get(i).beta;
        double a = 1.0;//Link.ALPHA;

        double gi1 = Math.pow( ((double)(a-1)/(double)(a-2)) * Math.pow((double)(KinFormula*li)/((double)zMin*len_lx), a) * bi * a * 4.0 * c, 
            (double)1/(double)(a-2));
        double gi2 = Math.pow( ((double)1/(double)(a-2)) * Math.pow((double)(KinFormula*li)/(double)(zMin*len_lx), a) * bi * a * 4.0 * c, 
            (double)1/(double)(a-2)) + 1;

        double gMin = Math.min(gi1, gi2);
        gMin = Math.max(gMin, 2);
        
        double zi = gMin * (double)ringWidth / li;

        double raio1 = gi1 * ringWidth;
        double raio2 = gi2 * ringWidth;
        double t1 = Math.pow((double)(2*li)/((double)zMin*len_lx), a);
//        links.get(i).setRadius(gMin * ringWidth);
      }
    } 


    for(int i = 0;i<links.size();i++) {
      links.get(i).set_weight( new Double(i) );
    }
  }

  private void set_adjacency(ArrayList<Link> links){
    int i,j;
    int degree[] = new int[links.size()];
    int adj[][] = new int[links.size()][links.size()];
    ArrayList<Link> linked = new ArrayList<Link>();

    //MRS.draw(links, true);

    for(i=0;i<links.size();i++){
      degree[i]=0;
    }

    for(i=0;i<links.size();i++){      
      for(j=i+1;j<links.size();j++){
        if( links.get(i).intersects(links.get(j)) ){
          //System.out.println("  i: " + i + "  j: " + j);          
          adj[i][j]=1;
          adj[j][i]=1;
          linked.add(new Link(links.get(i).sender.x, links.get(i).sender.y, 
            links.get(j).sender.x, links.get(j).sender.y, 2.0));
          degree[i]++;
          degree[j]++;
        }
      }      
    }

    //MRS.draw(linked, false);
  }

  //create a special disk graph (DGz) from the links
  public GrafoRadial(ArrayList<Link> links){

    //set adjacency using geometric intersection
    set_adjacency(links);

    //set the weight of the links
    set_weights(links);
  }    
}
