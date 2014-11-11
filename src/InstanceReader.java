import java.io.*;
import java.lang.Double;
import java.lang.Integer;
import java.util.*;

public class InstanceReader{

  static Grafo readInstance(String filename) throws FileNotFoundException, IOException{
    Grafo g;
    ArrayList<Link> links = new ArrayList<Link>();
    BufferedReader file = new BufferedReader(new FileReader(filename));

    String buffer = new String();

    //min link length
    buffer = file.readLine();
    double minLength = Double.parseDouble(buffer);
    //max link length
    buffer = file.readLine();
    double maxLength = Double.parseDouble(buffer);
    //min z function value
    buffer = file.readLine();
    double minZ = Double.parseDouble(buffer);
    //max z function value
    buffer = file.readLine();
    double maxZ = Double.parseDouble(buffer);
    //number of links
    buffer = file.readLine();
    int n = Integer.parseInt(buffer);

    //linkId x_sender y_sender x_receiver y_receiver z_function_value
    g = new Grafo(n,true);
    for(int i = 0; i < n; i++){
      buffer = file.readLine();
      String[] link = buffer.split(" ");
      links.add(new Link(Double.parseDouble(link[0]), Double.parseDouble(link[1]),
                        Double.parseDouble(link[2]), Double.parseDouble(link[3]),
                        Double.parseDouble(link[4])));
      // for(String s : link){
      //   System.out.println(s);
      // }
    }

    g.links = links;
    //linkId1 linkId2
    //arestas
    while((buffer = file.readLine())!=null){
      String[] adj = buffer.split(" ");
      g.setAdjacencia(Integer.parseInt(adj[0]),Integer.parseInt(adj[1]));
    }
    return g;
  }
}
