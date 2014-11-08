import java.util.*;

public class PG{

  private static boolean IS(Set<Integer> C){
    //return true if the set is independent, false otherwise
    //implement this
    return true;
  }

  public static Set<Integer> run(Stack<Integer> x, ArrayList<Link> links){
    int i=0, a;
    links.get(0).set_weight(0.0);
    links.get(1).set_weight(1.0);
    links.get(2).set_weight(2.0);
    links.get(3).set_weight(3.0);
    links.get(4).set_weight(4.0);
    links.get(5).set_weight(5.0);
    links.get(6).set_weight(6.0);
    links.get(7).set_weight(7.0);

    
    System.out.println("Prune-Phase");
      //B ← A, S ← ∅; //S is a stack
      Set<Integer> B = new TreeSet<Integer>();
      B.addAll(Arrays.asList(1,2,3,4,5));
      Stack<Integer> S = new Stack<Integer>();
      while(!B.isEmpty()){
        //a ← a x-surplus link of B;
        a = x.pop();
        //B ← B ∖ {a};
        B.removeAll(Arrays.asList(a));
        //w(a) ← w (a) − w (S ∩ N (a));
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