import java.util.*;

public class PG{

  private static boolean IS(Set<Integer> C){
    //return true if the set is independent, false otherwise
    //implement this
    return true;
  }

  public static Set<Integer> run(double eps, ArrayList<Link> links,
                          ArrayList<ArrayList<Integer>> Ins,
                          ArrayList<ArrayList<Integer>> Outs){
    int i=0, a;

    ArrayList<Link> B_links = links;
    ArrayList<ArrayList<Integer>> B_Ins = Ins;
    ArrayList<ArrayList<Integer>> B_Outs = Outs;
    
    System.out.println("Prune-Phase");
      //B ← A, S ← ∅; //S is a stack
      Set<Integer> B = new TreeSet<Integer>();
      for (i=0;i<links.size();i++){
        B.addAll(Arrays.asList(i));
      }
      
      Stack<Integer> S = new Stack<Integer>();
      while(!B.isEmpty()){
        System.out.println("=================================");
        System.out.println(B);

        //a ← a x-surplus link of B;                
        a = Link.get_x_surplus(B_links, B, B_Ins, B_Outs);

        //B ← B ∖ {a};
        B.removeAll(Arrays.asList(a));

        //w_bar(a) ← w (a) − w_bar (S ∩ N (a));
        double sum_weight_bar = 0.0;
        for(int adj : Outs.get(a)){
          if(S.search(adj)>0){
            sum_weight_bar = sum_weight_bar + links.get(adj).weight_bar;
          }
        }
        for(int adj : Ins.get(a)){ 
          if(S.search(adj)>0){
            sum_weight_bar = sum_weight_bar + links.get(adj).weight_bar;
          }
        }
        links.get(a).set_weight_bar(links.get(a).weight - sum_weight_bar);

        //if w(a) > 0, push a onto S;
        if(links.get(a).weight > 0.0) S.push(new Integer(a));


/*
        if (B.size()==1){
          Iterator<Integer> it = B.iterator();
          Integer current = 0;
          while(it.hasNext() ) {
            a = it.next();
          }
        }else{
          System.out.println("a= "+a);
          //B_links.remove(links.get(a));
          System.out.println("B_links "+B_links);
          //B_Ins.remove(Ins.get(a));
          System.out.println("B_Ins "+B_Ins);
          //B_Outs.remove(Outs.get(a));
          System.out.println("B_Outs "+B_Outs);

          PDA.run(eps, B_links, B_Ins, B_Outs);

          for(Link link : links){
            System.out.println("x("+links.indexOf(link)+")= "+link.x);
          }
        }
*/
      }

    System.out.println("Grow-Phase");
      //I ← ∅;
      Set<Integer> I = new TreeSet<Integer>();
      //while S ∕= ∅,
      while(!S.empty()){
        //pop the top link a from S; 
        a = S.pop();
//        System.out.println("pop "+a);

        //if I ∪ {a} is independent, I ← I ∪ {a};
        Set<Integer> Sa = new TreeSet<Integer>();
        Sa.add(a);
        Set<Integer> union = new TreeSet<Integer>();
        union.addAll(I);
        union.addAll(Sa);
        if(IS(union)) {
          I.clear();
          I.addAll(union);
        }
      }

    //return I.    
    return I;
  }

}
