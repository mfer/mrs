import java.util.*;
import java.lang.Math;

public class Link{
    double x_sender;
    double y_sender;
    double x_receiver;
    double y_receiver;
    double length;
    double interference_radius;
    double weight;

    public Link(double x_sender, double y_sender,
                double x_receiver, double y_receiver,
                double interference_radius){

      this.x_sender = x_sender;
      this.x_receiver = x_receiver;
      this.y_sender = y_sender;
      this.y_receiver = y_receiver;
      this.interference_radius = interference_radius;

      length = Math.sqrt(Math.abs(x_receiver - x_sender) * Math.abs(x_receiver - x_sender)
                       + Math.abs(y_receiver - y_sender) * Math.abs(y_receiver - y_sender));
    }

    public Double set_weight(Double value){
      this.weight = value;
      return value;
    }

    public Double w(){
      return weight;
    }
}
