import java.util.*;

import static java.awt.geom.AffineTransform.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import javax.swing.*;



public class GrafoRadial extends Grafo{

  public int adj[][];

  public static void draw(final ArrayList<Link> links, final boolean draw_oval,double scaleD){
    int scale = (int)scaleD;
    JFrame t = new JFrame();
    t.add(new JComponent() {

      private final int ARR_SIZE = 4;

      void drawArrow(Graphics g1, double x1, double y1, double x2, double y2) {
        Graphics2D g = (Graphics2D) g1.create();

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx*dx + dy*dy);
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);

        // Draw horizontal arrow starting in (0, 0)
        g.drawLine(0, 0, len, 0);

        if(draw_oval){
          g.fillPolygon(new int[] {len, len-ARR_SIZE, len-ARR_SIZE, len},
            new int[] {0, -ARR_SIZE, ARR_SIZE, 0}, 4);
        }
      }

      public void paintComponent(Graphics g) {
        for(Link link : links){

          int linkIndex = links.indexOf(link);

          int R = (int) (Math.random( )*256);
          int G = (int)(Math.random( )*256);
          int B= (int)(Math.random( )*256);
          Color randomColor = new Color(R, G, B);
          g.setColor(randomColor);

          int shift = 20;

          drawArrow(g, links.get(linkIndex).sender.x*scaleD+ shift,
            links.get(linkIndex).sender.y*scaleD + shift,
            links.get(linkIndex).receiver.x*scaleD + shift,
            links.get(linkIndex).receiver.y*scaleD + shift );

          g.drawOval((int)(links.get(linkIndex).sender.x*scaleD)-5 + shift,
            (int)(links.get(linkIndex).sender.y*scaleD-5) + shift,
            5,5);

          if(draw_oval){

            g.drawString(""+linkIndex,
              (int)(links.get(linkIndex).sender.x*scaleD)-5 + shift,
              (int)(links.get(linkIndex).sender.y*scaleD)-5 + shift);

            int r = (int)(2*scaleD*links.get(linkIndex).sender.distance(links.get(linkIndex).receiver));

            g.drawOval((int)(links.get(linkIndex).sender.x*scaleD-r/2) + shift,
              (int)(links.get(linkIndex).sender.y*scaleD-r/2 )+ shift,
              r,r);
          }else{
            //g.drawString(""+links.get(0).weight,
            //  (int)(scaleD*links.get(linkIndex).sender.x)-5 + shift,
            //  (int)(scaleD*links.get(linkIndex).sender.y-5 + shift));
          }
        }

      }
    });

    t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    t.setSize(900, 900);
    t.setVisible(true);
  }

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


    links.get(0).set_weight( 1.0 );
    links.get(1).set_weight( 1.0 );
    links.get(2).set_weight( 1.0 );
    links.get(3).set_weight( 1.0 );
    links.get(4).set_weight( 1.0 );
    links.get(5).set_weight( 1.0 );
    links.get(6).set_weight( 1.0 );
  }

  private void set_adjacency(ArrayList<Link> links){
    int i,j;
    int degree[] = new int[links.size()];
    int adj[][] = new int[links.size()][links.size()];
    ArrayList<Link> linked = new ArrayList<Link>();

    draw(links, true, 100);

    for(i=0;i<links.size();i++){
      degree[i]=0;
    }

    for(i=0;i<links.size();i++){
      for(j=i+1;j<links.size();j++){
        if( links.get(i).intersects(links.get(j)) ){
          System.out.println(i + " " + j);
          adj[i][j]=1;
          adj[j][i]=1;

          degree[i]++;
          degree[j]++;

          linked.add(new Link(links.get(i).sender.x, links.get(i).sender.y,
            links.get(j).sender.x, links.get(j).sender.y, 2.0));
        }
      }
    }

    draw(linked, false, 100);

    this.adj=adj;
  }

  private void set_interference_radius(ArrayList<Link> links){
    System.out.println("min_length "+Link.get_min_length(links));
    System.out.println("max_length "+Link.get_max_length(links));
    double delta_length = Link.get_max_length(links)/Link.get_min_length(links);
    System.out.println("delta_length "+delta_length);
  }

/*
  //create a special disk graph (DGz) from the links
  public GrafoRadial(ArrayList<Link> links){
    int i,j;

    //set the interference_radius of the links
    set_interference_radius(links);

    //set the weight of the links
    set_weights(links);

    //set adjacency using geometric intersection
    set_adjacency(links);

    System.out.print("-  ");
    for(i=0;i<links.size();i++)
      System.out.print(i+" ");
    System.out.println();
    for(i=0;i<links.size();i++){
      System.out.print(i+": ");
      for(j=0;j<=i;j++){
          System.out.print(adj[i][j]+" ");
      }
      System.out.println();
    }

  }
*/

  GrafoRadial(int nLinks, boolean bidirecional) {
    super(nLinks, bidirecional);
  }

  GrafoRadial(ArrayList<Link> links) {
    super(links);
  }

}
