/*
#	Le livre de Java 1er langage
#	A. Tasso
#	Chapitre 12 : Java et Internet
#	Section  : Une application c�t� serveur : la servlet
#	Fichier  : CalculPerimetreAvecPost.java
#	Class    : CalculPerimetreAvecPost
*/
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class CalculPerimetreAvecPost extends HttpServlet {
  public void doPost( HttpServletRequest req, HttpServletResponse rep)throws IOException, ServletException {
   Cercle C = new Cercle(0,0, 0);
	 
   rep.setContentType("text/html");
   PrintWriter pw = rep.getWriter();
   
   pw.println("<html>");
   pw.println("<head>");
   pw.println("<title> Une servlet avec POST </title>");
   pw.println("</head>");
   pw.println("<body bgcolor='white'>");
   pw.println("La requ�te a �t� envoy�e avec POST <br>");
   if (req.getParameter("rayon") == null) {
		pw.print("<font size=+1> ");		
		pw.println("J'ai besoin de connaitre la valeur du rayon !</font>");
   }
   else {
   	int tmp=Integer.parseInt(req.getParameter("rayon"));
   	C = new Cercle(0, 0, tmp);
   	double p = C.p�rim�tre();
   	pw.println("Le cercle de rayon "+tmp+" a pour p�rim�tre "+ p);
   }
   pw.println("</body>");
   pw.println("</html>");
 }   

}