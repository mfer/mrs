import java.util.*;
import java.lang.Math;

public class Link{

    int id;
    int senderId;
    int receiverId;

    double length;
    double x;
    double y;

    static ArrayList<Sender> senders = new ArrayList<Sender>();//lista com todos senders
    static ArrayList<Receiver> receivers = new ArrayList<Receiver>();;//lista com todos receivers

    public Link(int id, int sender, int receiver){

      this.id = id;
      this.senderId = sender;
      this.receiverId = receiver;

      x = senders.get(senderId).x;
      y = senders.get(senderId).y;

      double rX = receivers.get(receiverId).x;
      double rY = receivers.get(receiverId).y;

      length = Math.sqrt(Math.abs(rX - x) + Math.abs(rY - y));
    }
}
