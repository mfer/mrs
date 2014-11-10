import java.util.*;

public class PG{

  private static boolean IS(Set<Integer> C){
    //return true if the set is independent, false otherwise
    //implement this
    return true;
  }

  public static Set<Integer> run(ArrayList<Link> links){
    int i=0, a;
    
    System.out.println("Prune-Phase");
      //B ← A, S ← ∅; //S is a stack
      Set<Integer> B = new TreeSet<Integer>();
      for (i=0;i<links.size();i++){
        B.addAll(Arrays.asList(i));
      }
      //System.out.println(B);
      Stack<Integer> S = new Stack<Integer>();
      while(!B.isEmpty()){
        //a ← a x-surplus link of B;
        a = Link.get_x_surplus(links, B);
        //B ← B ∖ {a};
        B.removeAll(Arrays.asList(a));
        //w_bar(a) ← w (a) − w_bar (S ∩ N (a));
        int b = 0;

        //System.out.println("setting "+a);
        links.get(a).set_weight(links.get(a).w() - links.get(b).w());
        //if w(a) > 0, push a onto S;
        if(links.get(a).w() > 0.0) S.push(new Integer(a));
        i++;
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