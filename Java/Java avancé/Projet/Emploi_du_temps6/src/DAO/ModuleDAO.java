package DAO;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import javax.swing.JOptionPane;

import BDD.Module;
import SQL.DB;
import SQL.SQL;

public class ModuleDAO implements IDAO<Module> {

    private DB db;
	private SQL queryString = new SQL();

    private ModuleDAO() {
    	db = DB.getInstance();
    }
    
    private static IDAO<Module> iModuleDAO;

    public static IDAO<Module> getInstance() {
        if (iModuleDAO == null) {
            iModuleDAO = new ModuleDAO();
        }
        return iModuleDAO;
    }
    
	public <V> Module getById(V idModule){
		Module module = new Module();
		try{
			String[] table = { "Module" };
			String t = queryString.Query_Select(table, moduleAtt, "idModule = '"+idModule+"'");
			ResultSet result = db.Get_DB( t);
			while (result.next()){
				module.setIdModule(result.getString(1));
				module.setNom(result.getString(2));
				module.setCoefficient(result.getInt(3));
			}
		}catch(Exception x){
			JOptionPane.showMessageDialog(null,x.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
		}
		return module;
	}
	
	public Stream<Module> getAll(){
		List<Module> listAllModule = new ArrayList<Module>();
		try{
			String[] table = { "Module" };
			String t = queryString.Query_Select(table, moduleAtt, "");
			ResultSet result = db.Get_DB( t);
			while (result.next()) {
				Module module = new Module();
				module.setIdModule(result.getString(1));
				module.setNom(result.getString(2));
				module.setCoefficient(result.getInt(3));
				listAllModule.add(module);
			}
		}catch(Exception x){
			JOptionPane.showMessageDialog(null,x.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
		}
		return listAllModule.stream();
	}
	
	public Stream<String> getId(){
		List<String> listAllModule = new ArrayList<String>();
		try{
			String[] table = { "Module" };
			String[] champ = { "idModule" };
			String t = queryString.Query_Select(table, champ, "");
			ResultSet result = db.Get_DB( t);
			while (result.next())
				listAllModule.add(result.getString(1));
		}catch(Exception x){
			JOptionPane.showMessageDialog(null,x.getMessage(),"Erreur",JOptionPane.ERROR_MESSAGE);
		}
		return listAllModule.stream();
	}
	
	public void ajouter(Module module) throws Exception {
		String t = queryString.Query_Insert("Module", moduleAtt, moduleVal(module));
		if (db.Execute_DB(t)) System.out.println("Ok!");
	}

	public Boolean supprimer(Module module) throws Exception {
		String conditionE = "idModule= '" + module.getIdModule() + "'";
		String tE = queryString.Query_Delete("Module", conditionE);
		return (db.Execute_DB(tE));
	}

	public Boolean modifier(Module module) throws Exception {
		String condition = "idModule= '" + module.getIdModule() + "'";
		String t = queryString.Query_Update("Module", moduleAtt, moduleVal(module),condition);
		return (db.Execute_DB(t));
	}
	
	private String[] moduleAtt = { "idModule","nom","coef" };
	private String[] moduleVal(Module module) {
		String[] res = new String[3];
		res[0] = module.getIdModule().toString();
		res[1] = module.getNom();
		res[2] = module.getCoefficient().toString();
		return res;
	}
}
