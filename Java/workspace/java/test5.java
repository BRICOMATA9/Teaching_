import java.util.Scanner;
import java.lang.String;
public class test5
{
 private static Scanner scan =new Scanner(System.in);
 private static boolean fonction(char ch)
 {
  switch (ch)
  {
  case'e':
  case'o':
  case'i':
  case'u':
  case'y':
  case'a':
  case'I':
  case'O':
  case'U':
  case'A':
  case'E':
  case'Y':return(true);
  default:return(false);
  }
 }
 
 public static void main (String[] args)
 {
  String chaine=scan.nextLine();
  int cpt;
  int i,j;
  for(i=0;i<chaine.length()-1;i++)
  {
   cpt=1;
   if(fonction(chaine.charAt(i)))
   { 
     for(j=i+1;j<chaine.length();)
     {
      if ((chaine.charAt(i)==chaine.charAt(j))||(chaine.charAt(i)==(chaine.charAt(j))-32)||(chaine.charAt(i)==(chaine.charAt(j))+32))
      { 
       chaine=chaine.substring(0,j)+chaine.substring(j+1,chaine.length());
       cpt++;
      } 
      else j++;
     }
      System.out.println(cpt+" fois la lettre "+chaine.charAt(i));
   }
  } 
 }
} 
 
