import java.util.*;

public class PG{

  private static boolean IS(Set<Integer> C, Grafo G){
    //return true if the set is independent, false otherwise
    //check pairwise if the solution is an independent set 
    //(no edge between the selected nodes)

    for(int a : C){
      for(int b : C){
        if(b>a){
          System.out.println(a+" "+b);
          //System.out.println(" adj:"+G.adjacencia.get(a).contains(b));
          if(G.adjacencia.get(a).contains(b)) return false;
        }
      }
    }
    return true;
  }
/*  
    static boolean isIndependentSet(int adj[][],ArrayList<Integer> solution){

        int i,j;
        int nelements=solution.size();

        for(i=0;i<nelements;i++){
            for(j=i+1;j<nelements;j++){
              if((adj[solution.get(i)][solution.get(j)]>0)||
              (adj[solution.get(j)][solution.get(i)]>0)) return false;
            }
        }

        return true;
    }
*/

  public static Set<Integer> run(double eps, ArrayList<Link> links,
                          ArrayList<ArrayList<Integer>> Ins,
                          ArrayList<ArrayList<Integer>> Outs,
                          Grafo G){
    int i=0, a;

    ArrayList<Link> B_links = new ArrayList<Link>(links);
    ArrayList<ArrayList<Integer>> B_Ins = new ArrayList<ArrayList<Integer>>(Ins);
    ArrayList<ArrayList<Integer>> B_Outs = new ArrayList<ArrayList<Integer>>(Outs);

   
    System.out.println("Prune-Phase");
      //B ← A, S ← ∅; //S is a stack
      Set<Integer> B = new TreeSet<Integer>();
      for (i=0;i<links.size();i++){
        B.addAll(Arrays.asList(links.get(i).id));
      }

          System.out.println("Outs");
          for(ArrayList<Integer> link : Outs){
            int linkIndex = Outs.indexOf(link);
            for(int adj : link){
              System.out.println(linkIndex+" --> "+adj);
            }
          }

          System.out.println("Ins");
          for(ArrayList<Integer> link : Ins){
            int linkIndex = Ins.indexOf(link);
            for(int adj : link){
              System.out.println(linkIndex+" <-- "+adj);
            }
          }
      
      Stack<Integer> S = new Stack<Integer>();
      while(!B.isEmpty()){
        //System.out.println("=================================");

        //a ← a x-surplus link of B;                
        a = Link.get_x_surplus(links, B, Ins, Outs);
        //System.out.println("a= "+a);

        //B ← B ∖ {a};
        B.removeAll(Arrays.asList(a));
        //System.out.println(B);
          

          //**************************************************************************
          // I do not understand why this part of the code makes exist x-surplus
          // My initial idea was to be able to execute
                //PDA.run(eps, B_links, B_Ins, B_Outs);
          // TODO: dedicate time to understand...        
          //**************************************************************************
            B_links.remove(links.get(a));
            //System.out.print("B_links [");
            //for(Link l : B_links)
            //  System.out.print(l.id+", ");
            //System.out.println("]");
            B_Ins.remove(Ins.get(a));
            for(ArrayList<Integer> bins : B_Ins){ 
              if(bins.contains(a)) bins.remove(bins.indexOf(a));
            }
            //System.out.println("B_Ins "+B_Ins);
            B_Outs.remove(Outs.get(a));
            for(ArrayList<Integer> bouts : B_Outs){ 
              if(bouts.contains(a)) bouts.remove(bouts.indexOf(a));
            }
            //System.out.println("B_Outs "+B_Outs);
          //**************************************************************************


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
        if(links.get(a).weight_bar > 0.0) S.push(new Integer(a));
        //System.out.println("weight_bar("+a+")= "+links.get(a).weight_bar);

      }

    System.out.println("Grow-Phase");
      //I ← ∅;
      Set<Integer> I = new TreeSet<Integer>();

                  Stack<Integer> S_reverse = new Stack<Integer>();
                  while (!S.empty()) {
                    S_reverse.push(S.pop());
                  }
                  System.out.println(S_reverse);
                  System.out.println(S);

      //while S ∕= ∅,
      while(!S_reverse.empty()){
        System.out.println("=================================");
        //pop the top link a from S; 
        a = S_reverse.pop();
        System.out.println("pop "+a);

        //if I ∪ {a} is independent, I ← I ∪ {a};
        Set<Integer> Sa = new TreeSet<Integer>();
        Sa.add(a);
        Set<Integer> union = new TreeSet<Integer>();
        union.addAll(I);
        union.addAll(Sa);

        System.out.println("set_candidate: "+union);
        if(IS(union, G)) {
          I.clear();
          I.addAll(union);
        }
      }

    //return I.    
    return I;
  }

}
