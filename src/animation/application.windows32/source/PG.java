import java.util.*;

public class PG extends Thread{

  private static boolean IS(Set<Integer> C, Grafo G){
    //return true if the set is independent, false otherwise
    //check pairwise if the solution is an independent set 
    //(no edge between the selected nodes)

    for(int a : C){
      for(int b : C){
        if(b>a){
          //System.out.println(a+" "+b);
          //System.out.println(" adj:"+G.adjacencia.get(a).contains(b));
          if(G.adjacencia.get(a).contains(b)) return false;
        }
      }
    }
    return true;
  }

  public static Set<Integer> run(double eps, ArrayList<Link> links,
                          ArrayList<ArrayList<Integer>> Ins,
                          ArrayList<ArrayList<Integer>> Outs,
                          Grafo G,
                          ArrayList<Link> B_links,
                          ArrayList<ArrayList<Integer>> B_Ins,
                          ArrayList<ArrayList<Integer>> B_Outs,
                          Stack<Integer> S,
                          Stack<Integer> S_reverse,
                          Set<Integer> I){
    int i=0, a;

    

   
    //System.out.println("  Prune-Phase");
      //B ← A, S ← ∅; //S is a stack
    Set<Integer> B = new TreeSet<Integer>();
      
      for (i=0;i<links.size();i++){
        B.addAll(Arrays.asList(links.get(i).id));
      }

    //S = new Stack<Integer>();
      while(!B.isEmpty()){
        //System.out.println("=================================");

        //a ← a x-surplus link of B;                
        a = Link.get_x_surplus(links, B, Ins, Outs);
        //System.out.println("a= "+a);

        //B ← B ∖ {a};
        B.removeAll(Arrays.asList(a));
        //System.out.println(B);
          
        
            B_links.remove(links.get(a));
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
        double sum_weight_bar = 0.0;
        for(int adj : Outs.get(a)){
          //System.out.println("outs_adj: "+adj);
          if(S.search(adj)>0){
            sum_weight_bar = sum_weight_bar + links.get(adj).weight_bar;
          }
        }
        for(int adj : Ins.get(a)){ 
          //System.out.println(" ins_adj: "+adj);
          if(S.search(adj)>0){
            sum_weight_bar = sum_weight_bar + links.get(adj).weight_bar;
          }
        }
        //w_bar(a) ← w (a) − w_bar (S ∩ N (a));        
        links.get(a).set_weight_bar(links.get(a).weight - sum_weight_bar);

        //if w(a) > 0, push a onto S;
        if(links.get(a).weight_bar > 0.0) S.push(new Integer(a));
        //System.out.println("weight_bar("+a+")= "+links.get(a).weight_bar);
      
      Semaforo.post(1);
      Semaforo.wait(0);
      }//end while
     
      //I ← ∅;
      //I = new TreeSet<Integer>();

                  //Stack<Integer> S_reverse = new Stack<Integer>();
                  while (!S.empty()) {
                    S_reverse.push(S.pop());
                  }
                  
                  //System.out.println(S_reverse);
                  //System.out.println(S);

      //while S ∕= ∅,
      while(!S_reverse.empty()){
        //System.out.println("=================================");
        //pop the top link a from S; 
        a = S_reverse.pop();
        //System.out.println("pop "+a);

        //if I ∪ {a} is independent, I ← I ∪ {a};
        Set<Integer> Sa = new TreeSet<Integer>();
        Sa.add(a);
        Set<Integer> union = new TreeSet<Integer>();
        union.addAll(I);
        union.addAll(Sa);

        //System.out.println("set_candidate: "+union);
        if(IS(union, G)) {
          I.clear();
          I.addAll(union);
        }
        
      Semaforo.post(1);
      Semaforo.wait(0);
      }//end while
      Semaforo.post(1);
    //return I.    
    return I;
  }
  
  public static void pgInit(ArrayList<Link> links,
                        ArrayList<ArrayList<Integer>> Ins,
                        ArrayList<ArrayList<Integer>> Outs,
                        ArrayList<Link> B_links,
                        ArrayList<ArrayList<Integer>> B_Ins,
                        ArrayList<ArrayList<Integer>> B_Outs,
                        Set<Integer> B,
                        Stack<Integer> S){
    
    B_links = new ArrayList<Link>(links);
    B_Ins = new ArrayList<ArrayList<Integer>>(Ins);
    B_Outs = new ArrayList<ArrayList<Integer>>(Outs);

   
    //System.out.println("  Prune-Phase");
      //B ← A, S ← ∅; //S is a stack
      B = new TreeSet<Integer>();
      for (int i=0;i<links.size();i++){
        B.addAll(Arrays.asList(links.get(i).id));
      }
      S = new Stack<Integer>();
  }
  /*
  public static Set<Integer> pruneStep(double eps, ArrayList<Link> B_links,
                          ArrayList<ArrayList<Integer>> B_Ins,
                          ArrayList<ArrayList<Integer>> B_Outs,
                          Grafo G,
                          Set<Integer> B,
                          Stack<Integer> S,
                          Integer i , Integer a){                       
        

    
    
      //while(!B.isEmpty()){
        
        //System.out.println("=================================");

        //a ← a x-surplus link of B;                
        a = Link.get_x_surplus(B_links, B, B_Ins, B_Outs);
        //System.out.println("a= "+a);

        //B ← B ∖ {a};
        B.removeAll(Arrays.asList(a));
        //System.out.println(B);
          

            B_links.remove(links.get(a));
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
        double sum_weight_bar = 0.0;
        for(int adj : Outs.get(a)){
          //System.out.println("outs_adj: "+adj);
          if(S.search(adj)>0){
            sum_weight_bar = sum_weight_bar + links.get(adj).weight_bar;
          }
        }
        for(int adj : Ins.get(a)){ 
          //System.out.println(" ins_adj: "+adj);
          if(S.search(adj)>0){
            sum_weight_bar = sum_weight_bar + links.get(adj).weight_bar;
          }
        }
        //w_bar(a) ← w (a) − w_bar (S ∩ N (a));        
        links.get(a).set_weight_bar(links.get(a).weight - sum_weight_bar);

        //if w(a) > 0, push a onto S;
        if(links.get(a).weight_bar > 0.0) S.push(new Integer(a));
        //System.out.println("weight_bar("+a+")= "+links.get(a).weight_bar);

      //}//end while  -------------------------    
  }
*/
}
