import java.util.concurrent.Semaphore;

public class Semaforo{

  static Semaphore pg = new Semaphore(0);
  static Semaphore draw = new Semaphore(0);

  public static void wait(int i){
    try{
      if(i == 0)
        pg.acquire();
      else
        draw.acquire();
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
  }

  public static void post(int i){

    try{
      if(i == 0)
        pg.release();
      else
        draw.release();
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
    
  }
}
