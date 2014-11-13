import java.util.*;

//problem var
Grafo DGz;
Grafo D;
String filename = "instance";
ArrayList<ArrayList<Integer>> Ins;
ArrayList<ArrayList<Integer>> Outs;
double eps = 0.2;
Set<Integer> IS;

ArrayList<Link> B_links;
ArrayList<ArrayList<Integer>> B_Ins;
ArrayList<ArrayList<Integer>> B_Outs;
Stack<Integer> S;
Stack<Integer> S_reverse;
Set<Integer> I;

//drawing var
int escala = 60;
int widthPan = 100;
int heightPan = 100;
PFont font;
int estado = 0;
int maxEstado = 4;
Thread thread;
boolean draw = true;
color cor;

void keyReleased(){
  if (key == CODED) {
    if (keyCode == RIGHT) {
      if(estado == 2){
        Semaforo.post(0);
      }   
      if(estado < 2) estado += 1;
      
    }/*else if (keyCode == LEFT) {
      estado -= 1;
      if(estado < 0) estado = 0;      
    }*/
  } 
  desenha();
}

void drawLink( ArrayList<Link> links){
  for(Link l : links){
    int id = l.id;
    float x_sender = (float)l.x_sender + widthPan;
    float y_sender = (float)l.y_sender+ heightPan;
    float r = (float)l.interference_radius;
    
    float x_receiver = (float)l.x_receiver + widthPan;
    float y_receiver = (float)l.y_receiver + heightPan;
    
    
    strokeWeight(1.5);
    ellipseMode(RADIUS);
    noFill();
    stroke(0);
    ellipse(x_sender,y_sender,r-10,r-10);//interference radius
  
    
    fill(cor);
    if((estado == maxEstado-1)&& (!thread.isAlive()) &&IS.contains(id)) fill(200,0,0);
    //if((estado == 2)&& (thread.isAlive())) fill(0,200,200); 
    stroke(0);
    ellipse(x_sender,y_sender,10,10);//sender
    //AQUI escrever weigth e weight bar
    
    textFont(font); 
    fill(255);
    textAlign(CENTER);
    text(id,x_sender,y_sender+5);  
  
  
    if(estado < 2){
    fill(127);
    stroke(0);
    ellipse(x_receiver,y_receiver,10,10);//receiver
    
    textFont(font); 
    fill(255);    
    textAlign(CENTER);
    text(id,x_receiver,y_receiver+5); 
    }    
            
    
    
    
  }
}

void drawEdges(Grafo D, boolean orient){
  for(Link l : D.links){
    for(Integer adj : D.adjacencia.get(l.id)){
      float x_start = (float)l.x_sender + widthPan;
      float y_start = (float)l.y_sender + heightPan;
      float x_stop = (float)D.links.get(adj).x_sender + widthPan;
      float y_stop = (float)D.links.get(adj).y_sender + heightPan;
      strokeWeight(2);  
      noFill();
      stroke(0);
      line(x_start,y_start,x_stop,y_stop);
      if(orient){         
         pushMatrix();         
         float a = atan2(x_start-x_stop, y_stop-y_start);
         translate(x_stop+sin(a)*10, y_stop-cos(a)*10);
         rotate(a);
         line(0, 0, -10, -10);
         line(0, 0, 10, -10);
         popMatrix();
      }
      //println(l.id,adj);
    }
  }
}

void setup(){
  size(600,600);    
  font = createFont("Arial",12,true); 
  cor  = color(0.0,0.0,0.0);
  BufferedReader reader = createReader(filename);  
  try{
    DGz = InstanceReader.readInstance(reader);
  }catch(IOException e){
    print(e.getMessage());
    exit();
  }
  
  Ins = new ArrayList<ArrayList<Integer>>();
  Outs = new ArrayList<ArrayList<Integer>>();
  
  for(int i = 0; i < DGz.links.size(); i++){
    Ins.add(new ArrayList<Integer>());
    Outs.add(new ArrayList<Integer>());        
  }
 
   thread = new Thread(new Runnable(){
               public void run() {
                         IS = PG.run(eps, DGz.links,Ins,Outs,DGz,B_links,B_Ins,B_Outs,S,S_reverse,I);
                         System.out.println(IS);
                         //Semaforo.post(1);
                         //draw = true;
                         System.out.println("posted");
                         estado = 3;
                    }     
               }); 
 
   desenha(); 
}

void desenha(){
  background(250);
  switch(estado){
  
      case 0:
        drawEdges(DGz,false);        
        drawLink(DGz.links);                
        break;
        
     case 1:
       D = Orientate.edge(DGz);
       GetInOut.getInOut(D,Ins,Outs);
       PDA.run(eps, D.links,Ins,Outs);  
       drawEdges(D,true);       
       drawLink(D.links);               
       break;  
        
     case 2:     
       if(!thread.isAlive()){
        B_links = new ArrayList<Link>(D.links);
        B_Ins = new ArrayList<ArrayList<Integer>>(Ins);
        B_Outs = new ArrayList<ArrayList<Integer>>(Outs);
        S = new Stack<Integer>();
        S_reverse = new Stack<Integer>();
        I = new TreeSet<Integer>();
        thread.start();
        }
        //println("draw lock");
        Semaforo.wait(1);    
        drawEdges(D,true);
        cor = color(0,0,0);        
        drawLink(B_links);
        ArrayList S_links = new ArrayList<Link>();
        for(Link l : D.links){
         if(S.search(l.id) > 0)
           S_links.add(l);
         if(S_reverse.search(l.id) > 0)
           S_links.add(l);     
        }
        cor = color(0,127,200);
        drawLink(S_links); 
        
        cor = color(200,0,0); 
        ArrayList IS_links = new ArrayList<Link>();
        for(Link l : D.links){
        if(I.contains(l.id))
           IS_links.add(l);    
        }   
        drawLink(IS_links);
        break;
  
      default:
        background(127);
        cor = color(0,127,200);
        drawLink(D.links);
           
      break;     
  }
}

void draw(){
}
