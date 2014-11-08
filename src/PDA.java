import java.util.*;

public class PDA{

  public static Stack<Integer> run(double eps){
    //∀a ∈ A, x (a) ← 0, y (a) ← 1; τ ← 0;
    Stack<Integer> x = new Stack<Integer>();

    //while max a∈A x (N in D [a] ≥ (1 + ε) τ ) do
      //a ← arg min a∈A y ( N out D [a] ) / w(a); 
      //τ ← τ + N out D [a] / w(a);
      //x (a) ← x (a) + 1;
      x.push(new Integer(4));
      //∀b ∈ N out D[a], y(b) ← (1 + ε) y (b) ;            


    x.push(new Integer(4));
    x.push(new Integer(5));
    x.push(new Integer(1));
    x.push(new Integer(3));
    x.push(new Integer(6));
    x.push(new Integer(7));
    x.push(new Integer(2));
    //return x.
    return x;
  }
}