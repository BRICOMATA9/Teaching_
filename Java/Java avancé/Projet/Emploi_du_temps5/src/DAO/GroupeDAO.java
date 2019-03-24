package DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.JOptionPane;

import BDD.Groupe;
import IDAO.IGroupeDAO;
import SQL.DB;
import SQL.SQL;

public class GroupeDAO implements IGroupeDAO{
    private DB db;
	private SQL queryString = new SQL();

    private GroupeDAO() {
    	db = DB.getInstance();
    }
    private static IGroupeDAO iGroupeDAO;

    public static IGroupeDAO getInstance() {
        if (iGroupeDAO == null) {
            iGroupeDAO = new GroupeDAO();
        }
        return iGroupeDAO;
    }
   
    public Groupe getGroupeById(Integer idGroupe) throws Exception{
    	Groupe groupe = new Groupe();
		String[] table = { "Groupe" };
		String t = queryString.Query_Select(table, groupeAtt, "idGroupe = '"+idGroupe+"'");
		ResultSet result = db.Get_DB(t);
		while (result.next()) {
			groupe.setIdGroupe(result.getInt(1));
			groupe.setIdSection(SectionDAO.getInstance().getSectionById(result.getInt(2)));
		}   	
    	return groupe;
    }
    
	public Stream<Integer> getIdGroupe() {
		List<Integer> listGroupe = new ArrayList<Integer>();
		try{
			String[] table = { "Groupe" };
			String[] champ = { "idGroupe" };
			String t = queryString.Query_Select(table, champ, "");
			ResultSet result = db.Get_DB(t);
			while (result.next()) 
				listGroupe.add(result.getInt(1));
		}catch(Exception x){
			JOptionPane.showMessageDialog(null,x.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
		}
		return listGroupe.stream();
	}
	
	public Stream<Groupe> getAllGroupe() {
		List<Groupe> listGroupe = new ArrayList<Groupe>();
		try{
			String[] table = { "Groupe" };
			String t = queryString.Query_Select(table, groupeAtt, "");
			ResultSet result = db.Get_DB(t);
			while (result.next()) {
				Groupe groupe = new Groupe();
				groupe.setIdGroupe(result.getInt(1));
				groupe.setIdSection(SectionDAO.getInstance().getSectionById(result.getInt(2)));
				listGroupe.add(groupe);
			}
		}catch(Exception x){
			JOptionPane.showMessageDialog(null,x.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
		}
		return listGroupe.stream();
	}
    
	public void ajouterGroupe(Groupe groupe) throws Exception {
		String t = queryString.Query_Insert("Groupe", groupeAtt, groupeVal(groupe));
		if (db.Execute_DB(t)) System.out.println("Ok!");
	}

	public void supprimerGroupe(Groupe groupe) throws Exception {
		String condition = "idGroupe = '" + groupe.getIdGroupe() + "'";
		String t = queryString.Query_Delete("Groupe", condition);
		if (db.Execute_DB( t)) System.out.println("Ok!");
	}

	public void modifierGroupe(Groupe groupe) throws Exception {
		String condition = "idGroupe = '" + groupe.getIdGroupe() + "'";
		String t = queryString.Query_Update("Groupe", groupeAtt, groupeVal(groupe), condition);
		if (db.Execute_DB(t)) System.out.println("Ok!");
	}
	
	private String[] groupeAtt = { "idGroupe","idSection" };
	private String[] groupeVal(Groupe groupe) {
		String[] res = new String[2];
		res[0] = groupe.getIdGroupe().toString();
		res[1] = groupe.getIdSection().getIdSection().toString();
		return res;
	}
	
}
