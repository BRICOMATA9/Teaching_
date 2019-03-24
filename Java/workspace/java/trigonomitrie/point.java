public class point
{
 private double x,y;
 public point (double x, double y)
 {
  this.x=x;
  this.y=y;
 }
 
 public point ()
 {
  this.centre=0;
 }

 public double getcentre()
 {return centre;
 }
 public String toString()
 {return ("("+x+","+y+")");
 }
 public void affichepoint()
 {System.put.println(toString());
 }
}

