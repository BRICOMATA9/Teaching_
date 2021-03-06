package Systeme;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.sql.Time;
import java.util.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import bdd.*;
import Data.*;

public class Gestion_BDD {

	private Vector<Personne> utilisateurs;
	private Vector<Promotion> promotions;
	private Vector<Groupe> groupes;
	private Vector<Matiere> matieres;
	private Vector<Salle> salles;
	private Vector<Cours> cours; 
	
	//Création d'un singleton
	private static Gestion_BDD instance;
	private ConnectToDataBase connect;
	private Connection conn;
	private ProcessDB process;

	private Gestion_BDD(Boolean chargeXml) {

		connect = new ConnectToDataBase();
		conn = connect.Open_ConnectDB();
		process = new ProcessDB();

		utilisateurs = new Vector<Personne>();
		promotions = new Vector<Promotion>();
		groupes = new Vector<Groupe>();
		salles = new Vector <Salle>();
		cours = new Vector<Cours>();
		matieres = new Vector<Matiere>();
		
		//chargement();			
		//chargeBDD();
	}
	
	public static Gestion_BDD getInstance(){	
		if(instance==null){
			instance = new Gestion_BDD(true);
		}
		return instance;
	}

	public static Gestion_BDD getInstance(boolean ok){	
		if(instance==null){
			instance = new Gestion_BDD(ok);
		}
		return instance;
	}

	public synchronized void chargement() throws Exception {
		//Initialisation des vecteurs
		utilisateurs = new Vector<Personne>();
		promotions = new Vector<Promotion>();
		groupes = new Vector<Groupe>();
		salles = new Vector <Salle>();
		cours = new Vector<Cours>();
		matieres = new Vector<Matiere>();
		
		chargePromotion();
		chargeResp();
		chargeEns();
		chargeGroupes();
		chargeMatieres();
		chargeSalles();
		chargeEdt();
	}

	private void chargeResp() throws Exception{

	}

	private void chargeEns(){

	}

	private void chargePromotion(){

	}

	private Vector<Etudiant> ChargeEtudiants(){
		Vector<Etudiant> v = new Vector<Etudiant>();
		return v;	
	}

	private void chargeGroupes() throws Exception{

	}

	private void chargeMatieres() throws Exception{

	}

	private void chargeSalles() throws Exception{

	}

	private void chargeEdt() throws Exception{

	}

/*	public synchronized void sauvegarde() {
		racine = new Element("bdedt");
		document = new Document(racine);
		
		//Ajout des responsables et des enseignants
		Element inspecteurs = new Element("inspecteurs");
		racine.addContent(inspecteurs);
		Element enseignants = new Element("enseignants");
		racine.addContent(enseignants);
		sauvegardeUtilisateurs(inspecteurs, enseignants,utilisateurs,false);
		
		//Ajout des promotions
		Element etudiants = new Element("étudiants");
		racine.addContent(etudiants);
		sauvegardePromotion(etudiants);
		
		//Ajout des groupes
		Element gpes = new Element("groupes");
		racine.addContent(gpes);
		sauvegardeGroupe(gpes);
		
		//Ajout des matieres
		Element mats = new Element("matières");
		racine.addContent(mats);
		sauvegardeMatiere(mats);
		
		//Ajouts des salles
		Element sals = new Element("salles");
		racine.addContent(sals);
		sauvegardeSalles(sals);
		
		//Ajout des reservations
		Element edt= new Element("edt");
		racine.addContent(edt);
		sauvegardeCours(edt, cours);
		
		document.setDocType(new DocType(nomDtd,nomDtd+".dtd"));
				
		try
		   {
		      //On utilise ici un affichage classique avec getPrettyFormat()
		      XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
		      sortie.output(document, new FileOutputStream(ficXml2));
		   }
		   catch (java.io.IOException e){}
	}
	
	public static void sauvegardeCours(Element edt, Vector<Cours> listeCours) {
		Iterator i = listeCours.iterator();
		
		while(i.hasNext()) {
			Cours c = (Cours)i.next();
			Element reservation = new Element("réservation");
			
			reservation.setAttribute("date", c.getCreneau().date());
			reservation.setAttribute("heure", c.getCreneau().heure());
			reservation.setAttribute("durée", c.getCreneau().duree());
			reservation.setAttribute("salle", c.getSalle().getNom_salle());
			reservation.setAttribute("groupe", c.getGroupe().getnum_groupe());
			reservation.setAttribute("matière", c.getMatiere().getNum_matiere());
			
			edt.addContent(reservation);
		}
	}

	private void sauvegardeSalles(Element sals) {
		Iterator i = salles.iterator();
		while(i.hasNext())
		{
			Salle s = (Salle) i.next();
			Element sal = new Element("salle");
			sal.setAttribute("id", s.getNom_salle());
			sal.setAttribute("taille", ""+s.getTaille());
			
			switch(s.getType_Salle())
			{
				case Salle.AMPHI : sal.setAttribute("type", "amphi"); break;
				case Salle.COURS : sal.setAttribute("type", "cours"); break;
				case Salle.TP : sal.setAttribute("type", "tp"); break;
			}
			
			sals.addContent(sal);
		}
	}

	private void sauvegardeMatiere(Element mats) {
		Iterator i = matieres.iterator();
		
		while(i.hasNext())
		{
			Matiere mat =(Matiere)i.next();
			Element matier = new Element("matière");
			
			matier.setAttribute("id",mat.getNum_matiere());
			switch(mat.getType())
			{
				case Matiere.obligatoire: matier.setAttribute("type", "obligatoire");break;
				case Matiere.optionnelle: matier.setAttribute("type", "facultatif");break;
			}
			
			Element intitule = new Element("intitulé");
			intitule.setText(mat.getIntitule());
			matier.addContent(intitule);
			
			Element enseignements = new Element("enseignements");
			matier.addContent(enseignements);
			
			Iterator j = mat.getListe_enseignement().iterator();
			while(j.hasNext())
			{
				Enseignement ens = (Enseignement)j.next();
				String typeEns="";
				switch(ens.getType_enseignement())
				{
					case Enseignement.BE:typeEns ="BE";break;
					case Enseignement.CM:typeEns ="CM";break;
					case Enseignement.TD:typeEns ="TD";break;
					case Enseignement.TP:typeEns ="TP";break;
				}
				
				Element enseignement = new Element(typeEns);
				enseignement.setAttribute("enseignants", ens.recupEnseignants());
				enseignement.setAttribute("groupes", ens.recupGroupes());
				enseignement.setAttribute("volume", ens.recupvolume());
				
				//Ajout de l'enseignement a la matiere
				enseignements.addContent(enseignement);
			}
			
			//Ajout de la matiere
			mats.addContent(matier);
		}	
	}

	private void sauvegardeGroupe(Element gpes) {
		Iterator i = groupes.iterator();
		
		while(i.hasNext())
		{
			Groupe gp =(Groupe)i.next();
			Element gpe = new Element("groupe");
			gpe.setAttribute("id", gp.getnum_groupe());
			gpe.setAttribute("étudiants",recupEtudiant(gp));
			
			gpes.addContent(gpe);
		}
	
	}

	private String recupEtudiant(Groupe gp) {
		String etudiants="";
		
		for (int i=0; i<utilisateurs.size(); i++)
		{
			if(utilisateurs.elementAt(i).getClass()==Etudiant.class)
			{
				Etudiant etud = (Etudiant) utilisateurs.elementAt(i);
				if (etud.estDuGroupe(gp))
				{
					etudiants += etud.getNum_personne()+" ";
				}
			}
		}
		return etudiants.substring(0, etudiants.length()-1);
	}

	public static void sauvegardeUtilisateurs(Element inspecteurs, Element enseignants, Vector<Personne> listeUtilisateurs, Boolean email) {
		for (int i=0; i<listeUtilisateurs.size(); i++)
		{
			if(listeUtilisateurs.elementAt(i).getClass()==Responsable.class)
			{
				//Création du responsable
				Responsable resp = (Responsable)listeUtilisateurs.elementAt(i);
				Element inspecteur = new Element("inspecteur");
				Attribute id = new Attribute("id",resp.getNum_personne());
				Attribute promotion = new Attribute("promotion",resp.getPromo().getNom_promotion());
				
				inspecteur.setAttribute(id);
				inspecteur.setAttribute(promotion);
				
				Element nom = new Element("nom");
				nom.setText(resp.getNom());
				inspecteur.addContent(nom);
				Element prenom = new Element("prénom");
				prenom.setText(resp.getPrenom());
				inspecteur.addContent(prenom);
				Element mel = new Element("mél");
				mel.setText(resp.getEmail());
				inspecteur.addContent(mel);
				if(!email)
				{
					Element login = new Element("login");
					login.setText(resp.getUsername());
					inspecteur.addContent(login);
					Element pass = new Element("pass");
					pass.setText(resp.getPassword());
					inspecteur.addContent(pass);
				}
				
				
				//Ajout du responsable
				inspecteurs.addContent(inspecteur);
			}
			else if (listeUtilisateurs.elementAt(i).getClass()==Enseignant.class)
			{
				//création de l enseignant
				Enseignant ens = (Enseignant)listeUtilisateurs.elementAt(i);
				Element enseignant = new Element("enseignant");
				Attribute id = new Attribute("id",ens.getNum_personne());
				
				enseignant.setAttribute(id);
				
				Element nom = new Element("nom");
				nom.setText(ens.getNom());
				enseignant.addContent(nom);
				Element prenom = new Element("prénom");
				prenom.setText(ens.getPrenom());
				enseignant.addContent(prenom);
				Element mel = new Element("mél");
				mel.setText(ens.getEmail());
				enseignant.addContent(mel);
				
				if(!email)
				{
					Element login = new Element("login");
					login.setText(ens.getUsername());
					enseignant.addContent(login);
					Element pass = new Element("pass");
					pass.setText(ens.getPassword());
					enseignant.addContent(pass);
				}
				
				//Ajout de l'enseignant
				enseignants.addContent(enseignant);
			}
		}
		
		
	}

	private void sauvegardePromotion(Element etudiants) {
		Iterator i = promotions.iterator();
		
		while(i.hasNext())
		{
			Promotion promo = (Promotion)i.next();
			Element promotion = new Element("promotion");
			promotion.setAttribute("id", promo.getNom_promotion());
			
			Iterator j = promo.getListe_etudiant().iterator();
			while(j.hasNext())
			{
				//Creation de l etudiant
				Etudiant etud = (Etudiant) j.next();
				Element etudiant = new Element("étudiant");
				Attribute id = new Attribute("id",etud.getNum_personne());
				
				etudiant.setAttribute(id);
				
				Element nom = new Element("nom");
				nom.setText(etud.getNom());
				etudiant.addContent(nom);
				Element prenom = new Element("prénom");
				prenom.setText(etud.getPrenom());
				etudiant.addContent(prenom);
				//Element mel = new Element("mél");
				//mel.setText(etud.getEmail());
				//etudiant.addContent(mel);
				Element login = new Element("login");
				login.setText(etud.getUsername());
				etudiant.addContent(login);
				Element pass = new Element("pass");
				pass.setText(etud.getPassword());
				etudiant.addContent(pass);
				
				//ajout de l etudiant
				promotion.addContent(etudiant);
			}
			etudiants.addContent(promotion);
		}
		
	}
*/	
//==============================================================
//  Accesseurs
//==============================================================
	
	/**
	 * Fonction qui retourne une promotion à partir de son nom
	 * @param name - nom de la promotion
	 * @return la promotion correspondant
	 * @throws Exception si la promotion n'existe pas
	 */
	public Promotion getPromotion(String name) throws Exception
	{
		Promotion promo= null;
		boolean trouve = false;
		
		Iterator i = promotions.iterator();
		while( i.hasNext() && (!trouve))
		{
			promo = (Promotion)i.next();
			if(promo.getNom_promotion().compareTo(name)==0)
			{
				trouve = true;
			}
		}
		if(!trouve) throw new Exception("Promotion inexistante");
		return promo;
	}
	
	

	/**
	 * Fonction qui retourne un groupe à partir de son nom
	 * @param name - nom du groupe
	 * @return le groupe correspondant
	 * @throws Exception si le groupe n'existe pas
	 */
	public Groupe getGroupe(String name) throws Exception
	{
		Groupe gp= null;
		boolean trouve = false;
		
		Iterator i = groupes.iterator();
		while( i.hasNext() && (!trouve))
		{
			gp = (Groupe)i.next();
			if(gp.getnum_groupe().compareTo(name)==0)
			{
				trouve = true;
			}
		}
		if(!trouve) throw new Exception("Groupe inexistant: " +name);
		return gp;
	}
	
	/**
	 * Fonction qui retourne un étudiant à partir de son id
	 * @param id - identifiant de l'étudiant
	 * @return l'étudiant correspondant
	 * @throws Exception si l'étudiant n'existe pas
	 */
	
	private Etudiant getEtudiant(String id) throws Exception
	{
		Etudiant etudiant = null;
		boolean trouve = false;
		int i = 0;
		
		while (i<utilisateurs.size() && (!trouve))
		{
			if(utilisateurs.elementAt(i).getClass()==Etudiant.class)
			{
				etudiant = (Etudiant) utilisateurs.elementAt(i);
				if(etudiant.getNum_personne().compareTo(id)==0)
				{
					trouve = true;
				}
			}
			i++;
		}
		if(!trouve) throw new Exception("Etudiant inexistant");
		return etudiant;
	}
	
	/**
	 * Fonction qui retourne un enseignant à partir de son id
	 * @param id - identifiant de l'enseignant
	 * @return l'enseignant correspondant
	 * @throws Exception si l'enseignant n'existe pas
	 */
	private Enseignant getEnseignant(String id) throws Exception
	{
		Enseignant enseignant = null;
		boolean trouve = false;
		int i = 0;
		
		while (i<utilisateurs.size() && (!trouve))
		{
			if(utilisateurs.elementAt(i).getClass()==Enseignant.class)
			{
				enseignant = (Enseignant) utilisateurs.elementAt(i);
				if(enseignant.getNum_personne().compareTo(id)==0)
				{
					trouve = true;
				}
			}
			i++;
		}
		if(!trouve) throw new Exception("Enseignant inexistant");
		return enseignant;
	}
	
	/**
	 * Fonction qui retourne une salle a partir de son nom
	 * @param name - nom de la salle
	 * @return la salle correspondante
	 * @throws Exception si la salle n'existe pas
	 */
	public Salle getSalle(String name) throws Exception
	{
		Salle s= null;
		boolean trouve = false;
		
		Iterator i = salles.iterator();
		while( i.hasNext() && (!trouve))
		{
			s = (Salle)i.next();
			if(s.getNom_salle().compareTo(name)==0)
			{
				trouve = true;
			}
		}
		if(!trouve) throw new Exception("Salle inexistante");
		return s;
	}


	/**
	 * Fonction qui retourne une matière a partir de son nom
	 * @param name - nom de la matière
	 * @return la matière correspondante
	 * @throws Exception si la matière n'existe pas
	 */
	public Matiere getMatiere(String name) throws Exception
	{
		Matiere mat= null;
		boolean trouve = false;
		
		Iterator i = matieres.iterator();
		while( i.hasNext() && (!trouve))
		{
			mat = (Matiere)i.next();
			if(mat.getNum_matiere().compareTo(name)==0)
			{
				trouve = true;
			}
		}
		if(!trouve) throw new Exception("Matiere inexistante");
		return mat;
	}
	
	/**
	 * Fonction qui retourne les utilisateurs
	 * @return Retourne les utilisateurs.
	 */
	public Vector<Personne> getUtilisateurs() {
		return utilisateurs;
	}
	
	/**
	 * Fonction qui recupere les cours d'un ensemble de groupes
	 * @param liste_groupe - liste des groupes dont on veut les cours
	 * @return la liste de cours
	 */
	public Vector<Cours> getCoursGroupes(Vector<Groupe> liste_groupe)
	{
		int j;
		boolean ok;
		Vector<Cours> liste_cours = new Vector<Cours>();
		
		Iterator i = cours.iterator();
		while(i.hasNext())
		{
			ok = false;
			Cours c = (Cours)i.next();
			if (liste_groupe == null){
				if(c.getGroupe()==null && c.getEnseignant() ==null && c.getMatiere() == null)
					ok=true;
			}
			else
			{
				j=0;
				while(j<liste_groupe.size() && !ok)
				{
					if(c.getGroupe().egal(liste_groupe.elementAt(j)))
					{
						ok=true;
					}
					j++;
				}
			}
			if(ok) liste_cours.add(c);
		}
		
		return liste_cours;
	}
	
	/**
	 * Fonction qui recupere les cours de la promotion d'un responsable
	 * @param resp - responsable dont on veut récupérer les cours
	 * @return la liste de cours
	 */
	public Vector<Cours> getCoursPromotion(Responsable resp)
	{
		Vector<Cours> liste_cours = new Vector<Cours>();
		
		Iterator i = cours.iterator();
		while(i.hasNext())
		{
			Cours c = (Cours)i.next();
			if(c.getGroupe()!=null)
			{
				if(c.getGroupe().getResponsable().egal(resp))
				{
					liste_cours.add(c);
				}
			}
		}
		
		return liste_cours;
	}

	/**
	 * Fonction qui recupere les groupes de la promotion d'un responsable
	 * @param resp - responsable dont on veut récupérer les
	 * @return la liste des groupes
	 */
	public Vector<Groupe> getGroupesResp(Responsable resp)
	{
		Vector<Groupe> liste_groupe = new Vector<Groupe>();
		
		Iterator i = groupes.iterator();
		while(i.hasNext())
		{
			Groupe g = (Groupe)i.next();
			if(g.getResponsable().egal(resp))
			{
					liste_groupe.add(g);
			}
		}
		
		return liste_groupe;
	}
	
	/**
	 * Fonction qui recupere les cours d'une salle
	 * @param s - la salle dont on veut les cours
	 * @return la liste de cours
	 */
	public Vector<Cours> getCoursSalle(Salle s)
	{
		Vector<Cours> liste_cours = new Vector<Cours>();
		
		Iterator i = cours.iterator();
		while(i.hasNext())
		{
			Cours c = (Cours)i.next();
			if(c.getSalle().egal(s))
			{
				liste_cours.add(c);
			}
		}
		
		return liste_cours;
	}
	
	/**
	 * Fonction qui ajoute un cours à la liste des cours
	 * dans l'ordre croissant
	 * @param c - le cours a ajouter
	 * @throws Exception si le cours est en même temps qu'un autre
	 * ayant le même enseignant, la même salle ou le même groupe
	 */
	public boolean addCours(Cours c) throws Exception
	{
		boolean ok = false;
		int pos = 0;
		
		//if(cours.size()!=0)
		//{
			pos = cherchePosition(c, 0, cours.size());	
		//}
		cours.add(pos,c);
		//cours.add(c);
		
		return ok;
	}

	/**
	 * Fonction qui indique la position ou inserer le cours
	 * @param c - le cours a ajouter
	 * @param deb - debut de la liste de cours 
	 * @param nbElts - nombre d'elements
	 * @return la position a laquelle inserer le cours
	 * @throws Exception si le cours est en même temps qu'un autre
	 * ayant le même enseignant, la même salle ou le même groupe
	 */
	private int cherchePosition(Cours c, int deb, int nbElts) throws Exception
	{
		int pos=0;
		//System.out.println("*********************");
		//System.out.println("deb: "+deb +" et nbElts: "+nbElts);
		//System.out.println("date du cours a inserer "+c.getCreneau().date()+" "+c.getCreneau().heure());
		
		if(nbElts<=0)
		{
			pos = deb;
		}
		else if(nbElts==1)
		{
			//System.out.println("date du cours au debut "+cours.elementAt(deb).getCreneau().date()+" "+cours.elementAt(deb).getCreneau().heure());
			
			switch(cours.elementAt(deb).getCreneau().compare(c.getCreneau()))
			{
				case Creneau.AVANT : pos=deb+1;break;
				case Creneau.APRES : pos=deb;break;
				case Creneau.ERREUR : 
				{
					if(c.getGroupe().egal(cours.elementAt(deb).getGroupe()) || c.getSalle().egal(cours.elementAt(deb).getSalle()) || c.getEnseignant().egal(cours.elementAt(deb).getEnseignant()))
					{
						throw new Exception("Probleme de créneau" + c +" conflit avec "+cours.elementAt(deb));
					}
					pos= deb+1;
				}
			}
		}
		else
		{
			pos = deb+(nbElts/2);

			//System.out.println("deb("+deb+") "+cours.elementAt(deb).getCreneau().date()+" "+cours.elementAt(deb).getCreneau().heure());
			//System.out.println("c "+c.getCreneau().date()+" "+c.getCreneau().heure());
			//System.out.println("pos ("+pos+")" +cours.elementAt(pos).getCreneau().date()+" "+cours.elementAt(pos).getCreneau().heure());
			//System.out.println("fin("+fin+")" +cours.elementAt(fin).getCreneau().date()+" "+cours.elementAt(fin).getCreneau().heure());
			switch(cours.elementAt(pos).getCreneau().compare(c.getCreneau())) {
				case Creneau.AVANT : pos=cherchePosition(c,pos+1, nbElts-(pos-deb+1));break;
				case Creneau.APRES : pos = cherchePosition(c,deb,pos-deb);break;
				case Creneau.ERREUR : {
					if(c.getGroupe().egal(cours.elementAt(pos).getGroupe()) || 
							c.getSalle().egal(cours.elementAt(pos).getSalle()) || 
							c.getEnseignant().egal(cours.elementAt(pos).getEnseignant())){
									throw new Exception("Probleme de créneau: "+c);
					}
					pos=pos+1;
					break;
				}
			}			
				
		}
		//System.out.println("Position finale : "+pos);
		return pos;
	}

	//Fonction qui retourne la liste des responsables et des enseignants
	public Vector<Personne> getRespEns() {
		Vector<Personne> liste_personne = getResp();
		
		liste_personne.addAll(getEns());
		
		return liste_personne;
	}

	//Fonction qui retourne la liste des enseignants
	public Vector<Personne> getEns() {
		Vector<Personne> liste_personne = new Vector<Personne>();
		
		for(int i =0; i<utilisateurs.size(); i++){
			if(utilisateurs.elementAt(i).getClass()!= Etudiant.class  && utilisateurs.elementAt(i).getClass()!= Responsable.class){
				liste_personne.add(utilisateurs.elementAt(i));
			}
		}
		return liste_personne;
	}

	//Fonction qui retourne la liste des responsables
	public Vector<Personne> getResp() {
		Vector<Personne> liste_personne = new Vector<Personne>();
		
		for(int i =0; i<utilisateurs.size(); i++){
			if(utilisateurs.elementAt(i).getClass()!= Etudiant.class  && utilisateurs.elementAt(i).getClass()!= Enseignant.class){
				liste_personne.add(utilisateurs.elementAt(i));
			}
		}
		return liste_personne;
	}
	
	//Fonction qui retourne les cours
	public Vector<Cours> getCours() {
		return cours;
	}

	//Fonction qui retourne les groupes
	public Vector<Groupe> getGroupes() {
		return groupes;
	}

	//Fonction qui retourne les matieres
	public Vector<Matiere> getMatieres() {
		return matieres;
	}

	//Fonction qui retourne les salles
	public Vector<Salle> getSalles() {
		return salles;
	}

	//Fonction qui retourne un cours a un creneau et une salle en particulier
	public Cours getCours(Creneau creneau, Salle salle) {
		Boolean ok=false;
		int i =0;
		
		while(i<cours.size() && !ok){
			if(cours.elementAt(i).getCreneau().egal(creneau) && cours.elementAt(i).getSalle().egal(salle)){
				ok= true;
			}
			else i++;
		}
		
		if(ok)return cours.elementAt(i);
		else return null;
	}

	//Fonction qui supprime un cours
	public Boolean supprime_cours(Cours cours2) {
		boolean ok = false;
		int i = 0;
		while(i<cours.size() && !ok){
			if(cours.elementAt(i).egal(cours2)){
				//System.out.println(cours.elementAt(i)+" qui devrait etre le meme que "+ cours2);
				ok = cours.remove(cours.elementAt(i));
			}
			i++;
		}
		return ok; 
	}

	//Sauvegarde la base de donnees sur un fichier de sauvegarde
	public synchronized void sauveBDD() throws IOException{
/*		if(fichier.exists())fichier.delete();
		fichier.createNewFile();
		FileOutputStream fos = new FileOutputStream(fichier);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		oos.writeObject(this.utilisateurs);
		oos.writeObject(this.promotions);
		oos.writeObject(this.groupes);
		oos.writeObject(this.matieres);
		oos.writeObject(this.salles);
		oos.writeObject(this.cours);
		oos.close();*/
	}
	
	//Charge la base de donnees a partir d'un fichier de sauvegarde
	private void chargeBDD()throws Exception{
/*		FileInputStream fis = new FileInputStream(fichier);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		this.utilisateurs=(Vector<Personne>)ois.readObject();
		this.promotions=(Vector<Promotion>)ois.readObject();
		this.groupes=(Vector<Groupe>)ois.readObject();
		this.matieres=(Vector<Matiere>)ois.readObject();
		this.salles=(Vector<Salle>)ois.readObject();
		this.cours=(Vector<Cours>)ois.readObject();
		
		ois.close();*/
	}

	//Fonction qui affiche une liste d'objets
	private void afficheObjets(Vector v){
		Iterator i = v.iterator();
		while(i.hasNext()){
			System.out.println((i.next()).toString());
		}
	}

	//Fonction qui fait un test d'affiche de la base de données 
	public void testAffiche(){
		//Vérifications a retirer par la suite
		System.out.println("Fin chargement");
		System.out.println(this.utilisateurs.size()+" utilisateurs");
		System.out.println(this.promotions.size()+" promotions");
		System.out.println(this.groupes.size()+" groupes");
		System.out.println(this.matieres.size()+" matieres");
		System.out.println(this.cours.size()+" cours");
		System.out.println(this.salles.size()+" salles");
		afficheObjets(utilisateurs);
		afficheObjets(cours);
	}

	public static void main(String[] args) {
		Gestion_BDD bd = Gestion_BDD.getInstance(true);
		bd.testAffiche();
		//System.out.println("====================================================");
		
		//bd.sauvegarde();
		try {
			bd.sauveBDD();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
