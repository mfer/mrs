import java.util.*;

public class GrafoRadial{

    public GrafoRadial(ArrayList<Link> links){

      for(int i = 0; i < links.size(); i++){
        System.out.println(i+" "+links.get(i).interference_radius);        
      }
    }
}
