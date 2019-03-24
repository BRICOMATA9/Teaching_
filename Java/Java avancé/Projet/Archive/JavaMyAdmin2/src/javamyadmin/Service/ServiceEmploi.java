package javamyadmin.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javamyadmin.Data.ProcessDB;
import javamyadmin.Data.Query;
import javamyadmin.Model.*;
import javamyadmin.Mysql;

public class ServiceEmploi {

	private Connection conn;

	public ServiceEmploi() {
		conn = Mysql.getConnection();
	}

	// ***********************************LOGGIN**********************************************************
	public boolean logginUser(String account, String password) {
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();

		String[] table = { "User" };
		String[] fields = { "id_user" };
		String condition = "account= '" + account + "' AND password = '"+ password + "'";

		String t = queryString.Query_Select(table, fields, condition);
		System.out.println(t);
		ResultSet result = null;
		try {
			result = process.Get_DB(conn, t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		User user = new User();
		try {
			while (result.next()) {
				user.setId(result.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (user.getId() == null) {
			System.out.println("N'existe pas !");
			return false;
		} else {
			System.out.println("pass !");
			return true;
		}
	}

	public Promotion findPromotionById(String idPromo) {
		Promotion promotion = new Promotion();
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String[] table = { "Promotion" };
		String[] fields = { "id_promo,name_promo, year" };
		String condition = "id_promo = '" + idPromo + "'";
		String t = queryString.Query_Select(table, fields, condition);
		// System.out.println(t);
		ResultSet result = null;
		try {
			result = process.Get_DB(conn, t);
		} catch (Exception e) {
			System.out.println("Request faile in PromotionID");
		}
		try {
			while (result.next()) {
				promotion.setId(result.getString(1));
				promotion.setClasses(findClasseByPromotion(promotion.getId()));
				promotion.setName(result.getString(2));
				// System.out.println(result.getString(3));
				promotion.setYear(Integer.parseInt(result.getString(3)));
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		// System.out.println(promotion.getClasses().get(0).getEmploi().getListSceance().get(0).getModule().getName());
		return promotion;

	}

	public Classe findClasseById(String idClasse) {
		Classe classe = new Classe();
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String[] table = { "Classe" };
		String[] fields = { "id_classe,id_promo,name_classe" };
		String condition = "id_classe = '" + idClasse + "'";
		String t = queryString.Query_Select(table, fields, condition);
		// System.out.println(t);
		ResultSet result = null;
		try {
			result = process.Get_DB(conn, t);
		} catch (Exception e) {
			System.out.println("Request faile in PromotionID");
		}
		try {
			while (result.next()) {
				classe.setId(result.getString(1));
				classe.setIdPromotion(result.getString(2));
				classe.setName(result.getString(3));
				// classe.setEmploi(findEmploiByClass(classe.getId()));
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// TODO Au
			// e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		// System.out.println(classe.getEmploi().getListSceance().size());
		return classe;

	}

		public Classe findClasseByName(String nameClasse) throws ParseException {
			Classe classe = new Classe();
			ProcessDB process = new ProcessDB();
			Query queryString = new Query();
			String[] table = { "Classe" };
			String[] fields = { "id_classe,id_promo,name_classe" };
			String condition = "name_classe = '" + nameClasse + "'";
			String t = queryString.Query_Select(table, fields, condition);
			// System.out.println(t);
			ResultSet result = null;
			try {
				result = process.Get_DB(conn, t);
			} catch (Exception e) {
				System.out.println("Request faile in PromotionID");
			}
			try {
				while (result.next()) {
					classe.setId(result.getString(1));
					classe.setIdPromotion(result.getString(2));
					classe.setName(result.getString(3));
					classe.setEmploi(findEmploiByClasse(classe.getId()));
				}
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
			System.out.println(classe.getEmploi().getListSceance().size());
			return classe;

		}

	public EmploiDuTemps findEmploiById(String idEmploi) {
		EmploiDuTemps emploi = new EmploiDuTemps();
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String[] table = { "Emploi" };
		String[] fields = { "id_emploi,id_classe" };
		String condition = "id_emploi = '" + idEmploi + "'";
		String t = queryString.Query_Select(table, fields, condition);
		// System.out.println(t);
		ResultSet result = null;
		try {
			result = process.Get_DB(conn, t);
		} catch (Exception e) {
			System.out.println("Request faile in PromotionID");
		}
		try {
			while (result.next()) {
				emploi.setId(result.getString(1));
				emploi.setClasse(result.getString(2));
				try {
					emploi.setListSceance(findSceanceByEmploi(emploi.getId()));
				} catch (ParseException e) {
					System.out.println("Faile in findEmploiById");
					// e.printStackTrace();
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		// System.out.println(emploi.getListSceance().size());
		return emploi;
	}

	public Sceance findSceanceById(String idSceance) {
		Sceance sceance = new Sceance();
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String[] table = { "Sceance" };
		String[] fields = { "id_sceance,id_module,id_emploi, date_sceance, time_sceance" };
		String condition = "id_sceance = '" + idSceance
				+ "' ORDER BY date_sceance";
		String t = queryString.Query_Select(table, fields, condition);
		// System.out.println(t);
		ResultSet result = null;
		try {
			result = process.Get_DB(conn, t);
		} catch (Exception e) {
			System.out.println("Request faile in PromotionID");
		}
		try {
			while (result.next()) {
				SimpleDateFormat formatterDate = new SimpleDateFormat(
						"yy-MM-dd");
				SimpleDateFormat formatterTime = new SimpleDateFormat(
						"HH:mm:ss");
				sceance.setId(result.getString(1));
				sceance.setModule(findModuleById(result.getString(2)));
				sceance.setIdEmploi(result.getString(3));
				try {
					sceance.setDate((Date) formatterDate.parse(result
							.getString(4)));
				} catch (ParseException e) {
					System.out.println("Faile in findSceanceById Date");
					e.printStackTrace();
				}
				try {
					sceance.setTime((Date) formatterTime.parse(result
							.getString(5)));
				} catch (ParseException e) {
					System.out.println("Faile in findSceanceById* Time");
					e.printStackTrace();
				}
				// System.out.println(sceance.getModule().getName());
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return sceance;

	}

	public Module findModuleById(String idModule) {
		Module module = new Module();
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String[] table = { "Module" };
		String[] fields = { "id_module, id_prof, name_module, duree" };
		String condition = "id_module = '" + idModule + "'";
		String t = queryString.Query_Select(table, fields, condition);
		// System.out.println(t);
		ResultSet result = null;
		try {
			result = process.Get_DB(conn, t);
		} catch (Exception e) {
			System.out.println("Request faile in Module");
		}
		try {
			while (result.next()) {
				module.setId(result.getString(1));
				module.setIdProfessor(result.getString(2));
				module.setName(result.getString(3));
				module.setDuree(Integer.parseInt(result.getString(4)));
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		// System.out.println(module.getName());
		return module;

	}

	public Module findModuleByName(String nameModule) {
		Module module = new Module();
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String[] table = { "Module" };
		String[] fields = { "id_module, id_prof, name_module, duree" };
		String condition = "name_module = '" + nameModule + "'";
		String t = queryString.Query_Select(table, fields, condition);
		// System.out.println(t);
		ResultSet result = null;
		try {
			result = process.Get_DB(conn, t);
		} catch (Exception e) {
			System.out.println("Request faile in Module");
		}
		try {
			while (result.next()) {
				module.setId(result.getString(1));
				module.setIdProfessor(result.getString(2));
				module.setName(result.getString(3));
				module.setDuree(Integer.parseInt(result.getString(4)));
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		// System.out.println(module.getName());
		return module;

	}

	public List<Module> findAllNameModule() {
		List<Module> listAllModule = new ArrayList<Module>();
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String[] table = { "Module" };
		String[] fields = { "id_module, id_prof, name_module, duree" };
		String condition = "";
		String t = queryString.Query_Select(table, fields, condition);
		// System.out.println(t);
		ResultSet result = null;
		try {
			result = process.Get_DB(conn, t);
		} catch (Exception e) {
			System.out.println("Request faile in Module");
		}
		try {
			while (result.next()) {
				Module module = new Module();
				module.setId(result.getString(1));
				module.setIdProfessor(result.getString(2));
				module.setName(result.getString(3));
				module.setDuree(Integer.parseInt(result.getString(4)));
				listAllModule.add(module);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		for (Module module : listAllModule) {
			System.out.println(module.getName());
		}
		return listAllModule;

	}

	public Professor findProfessorById(String idProf) {
		Professor professor = new Professor();
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String[] table = { "Professor" };
		String[] fields = { "id_prof, first_name, last_name, email" };
		String condition = "id_prof = '" + idProf + "'";
		String t = queryString.Query_Select(table, fields, condition);
		// System.out.println(t);
		ResultSet result = null;
		try {
			result = process.Get_DB(conn, t);
		} catch (Exception e) {
			System.out.println("Request faile in Module");
		}
		try {
			while (result.next()) {
				professor.setId(result.getString(1));
				professor.setFirstName(result.getString(2));
				professor.setLastName(result.getString(3));
				professor.setEmail(result.getString(4));
				try {
					professor.setModules(findModuleByProfessor(professor
							.getId()));
				} catch (ParseException e) {
					System.out.println("Faile in findProfessorById");
					// e.printStackTrace();
				}
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		System.out.println(professor.getModules().get(0).getName());
		return professor;

	}

	public List<Classe> findClasseByPromotion(String idPromo) {
		List<Classe> listClassePromo = new ArrayList<Classe>();
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String[] table = { "Classe" };
		String[] fields = { "id_classe, id_promo, name_classe" };
		String condition = "id_promo = '" + idPromo + "'";
		String t = queryString.Query_Select(table, fields, condition);
		// System.out.println(t);
		ResultSet result = null;
		try {
			result = process.Get_DB(conn, t);
		} catch (Exception e) {
			System.out.println("Request faile in findClasseByPromotion");
		}
		try {
			while (result.next()) {
				Classe classe = new Classe();
				classe.setId(result.getString(1));
				classe.setIdPromotion(result.getString(2));
				classe.setName(result.getString(3));
				try {
					classe.setEmploi(findEmploiByClasse(classe.getId()));
				} catch (ParseException e) {
					System.out.println("Faile in findEmploiByClasse");
					// e.printStackTrace();
				}
				// System.out.println(classe.getName());
				listClassePromo.add(classe);

			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		// System.out.println(listClassePromo.size());
		return listClassePromo;
	}

	public EmploiDuTemps findEmploiByClasse(String idClasse)
			throws ParseException {
		EmploiDuTemps emploi = new EmploiDuTemps();
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String[] table = { "Emploi" };
		String[] fields = { "id_emploi, id_classe" };
		String condition = "id_classe = '" + idClasse + "'";
		String t = queryString.Query_Select(table, fields, condition);
		// System.out.println(t);
		ResultSet result = null;
		try {
			result = process.Get_DB(conn, t);
		} catch (Exception e) {
			System.out.println("Request faile in  findEmploiByClasse");
		}
		try {
			while (result.next()) {
				emploi.setId(result.getString(1));
				emploi.setClasse(result.getString(2));
				emploi.setListSceance(findSceanceByEmploi(emploi.getId()));
			}
			// System.out.println(emploi.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// System.out.println("Ici");
			// e.printStackTrace();
		}
		// System.out.println(emploi.getListSceance().size());
		return emploi;
	}

	public List<Sceance> findSceanceByEmploi(String idEmploi)
			throws ParseException {
		List<Sceance> listSceanceEmploi = new ArrayList<Sceance>();
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String[] table = { "Sceance" };
		String[] fields = { "id_sceance, id_module, id_emploi, date_sceance, time_sceance" };
		String condition = "id_emploi = '" + idEmploi
				+ "'ORDER BY date_sceance";
		String t = queryString.Query_Select(table, fields, condition);
		// System.out.println(t);
		ResultSet result = null;
		try {
			result = process.Get_DB(conn, t);
		} catch (Exception e) {
			System.out.println("Request faile in findSceanceByEmploi");
		}
		try {
			while (result.next()) {
				Sceance sceance = new Sceance();
				Module module = new Module();
				SimpleDateFormat formatterDate = new SimpleDateFormat(
						"yy-MM-dd");
				SimpleDateFormat formatterTime = new SimpleDateFormat(
						"HH:mm:ss");
				sceance.setId(result.getString(1));
				module = findModuleById(result.getString(2));
				sceance.setModule(module);
				sceance.setIdEmploi(result.getString(3));
				sceance.setDate((Date) formatterDate.parse(result.getString(4)));
				sceance.setTime((Date) formatterTime.parse(result.getString(5)));
				// System.out.println(listModuleProf.size());
				listSceanceEmploi.add(sceance);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		// System.out.println(listSceanceEmploi.size());
		return listSceanceEmploi;
	}

	public List<Sceance> findSceanceByModule(String idModule)
			throws ParseException {
		List<Sceance> listSceanceModule = new ArrayList<Sceance>();
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String[] table = { "Sceance" };
		String[] fields = { "id_sceance, id_module, id_emploi, date_sceance, time_sceance" };
		String condition = "id_module = '" + idModule
				+ "'ORDER BY date_sceance";
		String t = queryString.Query_Select(table, fields, condition);
		// System.out.println(t);
		ResultSet result = null;
		try {
			result = process.Get_DB(conn, t);
		} catch (Exception e) {
			System.out.println("Request faile in findSceanceByEmploi");
		}
		try {
			while (result.next()) {
				Sceance sceance = new Sceance();
				Module module = new Module();
				SimpleDateFormat formatterDate = new SimpleDateFormat(
						"yy-MM-dd");
				SimpleDateFormat formatterTime = new SimpleDateFormat(
						"HH:mm:ss");
				sceance.setId(result.getString(1));
				module = findModuleById(result.getString(2));
				sceance.setModule(module);
				sceance.setIdEmploi(result.getString(3));
				sceance.setDate((Date) formatterDate.parse(result.getString(4)));
				sceance.setTime((Date) formatterTime.parse(result.getString(5)));
				// System.out.println(listModuleProf.size());
				listSceanceModule.add(sceance);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		// System.out.println(listSceanceEmploi.size());
		return listSceanceModule;
	}

	public List<Module> findModuleByProfessor(String idProfessor)
			throws ParseException {
		List<Module> listModuleProfessor = new ArrayList<Module>();
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String[] table = { "Module" };
		String[] fields = { "id_module, id_prof, name_module, duree" };
		String condition = "id_prof = '" + idProfessor + "'";
		String t = queryString.Query_Select(table, fields, condition);
		// System.out.println(t);
		ResultSet result = null;
		try {
			result = process.Get_DB(conn, t);
		} catch (Exception e) {
			System.out.println("Request faile in findModuleByProfessor");
		}
		try {
			while (result.next()) {
				Module module = new Module();
				module.setId(result.getString(1));
				module.setIdProfessor(result.getString(2));
				module.setName(result.getString(3));
				module.setDuree(Integer.parseInt(result.getString(4)));

				listModuleProfessor.add(module);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		// System.out.println(listModuleProfessor.size());
		// System.out.println(listModuleProfessor.get(0).getName());
		return listModuleProfessor;
	}

	public Professors findAllProfessor() throws ParseException {
		Professors allProfs = new Professors();
		List<Professor> listProfs = new ArrayList<Professor>();
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String[] table = { "Professor" };
		String[] fields = { "id_prof, first_name, last_name, email " };
		String condition = "";
		String t = queryString.Query_Select(table, fields, condition);
		// System.out.println(t);
		ResultSet resultProfs = null;
		try {
			resultProfs = process.Get_DB(conn, t);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (resultProfs == null)
			System.out.println("Request faile in findAllProfessor");
		try {
			// Ajouter inforamtion dans la liste
			while (resultProfs.next()) {
				Professor prof = new Professor();
				prof.setId(resultProfs.getString(1));
				prof.setFirstName(resultProfs.getString(2));
				prof.setLastName(resultProfs.getString(3));
				prof.setEmail(resultProfs.getString(4));
				prof.setModules(findModuleByProfessor(prof.getId()));
				listProfs.add(prof);
				// System.out.println(listProfs.size());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		// Chercher des modules de chaque prof
		// Mettre listProfs aux allProfs;
		allProfs.setAllProfs(listProfs);
		// System.out.println(allProfs.getAllProfs().size());
		return allProfs;
	}

	public Promotions findAllPromotion() {
		Promotions allPromo = new Promotions();
		List<Promotion> listPromo = new ArrayList<Promotion>();
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String[] table = { "Promotion" };
		String[] fields = { "id_promo, name_promo, year" };
		String condition = "";
		String t = queryString.Query_Select(table, fields, condition);
		// System.out.println(t);
		ResultSet result = null;
		try {
			result = process.Get_DB(conn, t);
		} catch (Exception e) {
			System.out.println("Faile in findAllPromotion");
			// e.printStackTrace();
		}
		try {
			while (result.next()) {
				Promotion promo = new Promotion();
				promo.setId(result.getString(1));
				promo.setName(result.getString(2));
				promo.setYear(Integer.parseInt(result.getString(3)));
				promo.setClasses(findClasseByPromotion(promo.getId()));
				listPromo.add(promo);
				// System.out.println(listPromo.size());
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		allPromo.setListPromos(listPromo);
		// System.out.println(allPromo.getListPromos().get(1).getClasses().get(0).getEmploi().getListSceance().get(0).getModule().getName());
		return allPromo;
	}

	public EmploiDuTemps findEmploiByPromotionAndClasse(String namePromotion,
			String nameClasse) {
		Promotions promotions = new Promotions();
		EmploiDuTemps emploi = new EmploiDuTemps();
		List<Classe> listClasse = new ArrayList<Classe>();
		promotions = findAllPromotion();
		for (Promotion promo : promotions.getListPromos()) {
			if (promo.getName().equals(namePromotion)) {
				listClasse = promo.getClasses();
				// System.out.println(listClasse.size());
				// System.out.println(listClasse.get(0).getName());
			}
		}
		// System.out.println(listClasse.get(0).getName());
		for (Classe classe : listClasse)
			if (classe.getName().equals(nameClasse)) {
				emploi = classe.getEmploi();
			}
		if (emploi != null) {
			System.out.println(emploi.getId());
		} else {
			System.out.println("Il n'y a pas emploi");
		}
		return emploi;
	}

	public List<Module> findModuleByClasseAndPromotion(
			String namePromotion, String nameClasse) {
		List<Module> listModuleOfClasse = new ArrayList<Module>();
		EmploiDuTemps emploi = new EmploiDuTemps();
		emploi = findEmploiByPromotionAndClasse(namePromotion, nameClasse);
		if (emploi != null) {
			for (Sceance sceance : emploi.getListSceance())
				listModuleOfClasse.add(sceance.getModule());
			HashMap<String, Module> hm = new HashMap<String, Module>();
			for (Module module : listModuleOfClasse) {
				hm.put(module.getId(), module);
			}
			Set set = hm.entrySet();
			java.util.Iterator i = set.iterator();
			List<Module> listAll = new ArrayList<Module>();
			while (i.hasNext()) {
				Entry me = (Map.Entry) i.next();
				String idModule = (String) me.getKey();
				Module module1 = new Module();
				module1= hm.get(idModule);
				listAll.add(module1);
			}
			System.out.println(listAll.size());
			for (Module module:listAll){
				System.out.println(module.getName());
			}
			return listAll;
		} else {
			System.out.println("Il n'y a pas Sceance de ce classe");
			return null;
		}
	}

	// ****************FIND NAME OF ALL PRPMOTION*****************
	public List<String> findNameOfAllPromotion() {
		List<String> allNamePromotion = new ArrayList<String>();
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String[] table = { "Promotion" };
		String[] fields = { "name_promo" };
		String condition = "";
		String t = queryString.Query_Select_Distinct(table, fields, condition);
		// System.out.println(t);
		ResultSet result = null;
		try {
			result = process.Get_DB(conn, t);
		} catch (Exception e) {
			System.out.println("Request faile in  findAllNamePromotion");
		}
		try {
			while (result.next()) {
				String name;
				name = result.getString(1);
				allNamePromotion.add(name);
			}
			// System.out.println(emploi.getId());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		// System.out.println(emploi.getListSceance().size());
		/*
		 * for (String name : allNamePromotion){ System.out.println(name); }
		 */
		return allNamePromotion;

	}

	// ****************FIND NAME OF ALL CLASSE*****************
	public List<String> findNameOfAllClasse() {
		List<String> allNameClasse = new ArrayList<String>();
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String[] table = { "Classe" };
		String[] fields = { "name_classe" };
		String condition = "";
		String t = queryString.Query_Select_Distinct(table, fields, condition);
		// System.out.println(t);
		ResultSet result = null;
		try {
			result = process.Get_DB(conn, t);
		} catch (Exception e) {
			System.out.println("Request faile in  findAllNameClasse");
		}
		try {
			while (result.next()) {
				String name;
				name = result.getString(1);
				allNameClasse.add(name);
			}
			// System.out.println(emploi.getId());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		/*
		 * for (String name : allNameClasse) { System.out.println(name); }
		 */
		return allNameClasse;

	}
	//**********************FIND ALL SCEANCE************************************
	public List<Sceance> findAllSceance() throws ParseException{
		List<Sceance> listAllSceance = new ArrayList<Sceance>();
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String[] table = { "Sceance" };
		String[] fields = { "id_sceance, id_module, id_emploi, date_sceance, time_sceance" };
		String condition = "";
		String t = queryString.Query_Select(table, fields, condition) + " ORDER BY date_sceance";
		System.out.println(t);
		ResultSet result = null;
		try {
			result = process.Get_DB(conn, t);
		} catch (Exception e) {
			System.out.println("Request faile in findSceanceByEmploi");
		}
		try {
			while (result.next()) {
				Sceance sceance = new Sceance();
				Module module = new Module();
				SimpleDateFormat formatterDate = new SimpleDateFormat(
						"yy-MM-dd");
				SimpleDateFormat formatterTime = new SimpleDateFormat(
						"HH:mm:ss");
				sceance.setId(result.getString(1));
				module = findModuleById(result.getString(2));
				sceance.setModule(module);
				sceance.setIdEmploi(result.getString(3));
				sceance.setDate((Date) formatterDate.parse(result.getString(4)));
				sceance.setTime((Date) formatterTime.parse(result.getString(5)));
				// System.out.println(listModuleProf.size());
				listAllSceance.add(sceance);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		// System.out.println(listSceanceEmploi.size());
		return listAllSceance;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public void addSceance(Sceance sceance) {
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		SimpleDateFormat formatterDate = new SimpleDateFormat("yy-MM-dd");
		SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm:ss");
		String table = "Sceance";
		String[] fields = { "id_sceance", "id_module", " id_emploi",
				"date_sceance", "time_sceance" };
		String[] value = { sceance.getId(), sceance.getModule().getId(),
				sceance.getIdEmploi(), formatterDate.format(sceance.getDate()),
				formatterTime.format(sceance.getTime()) };
		String t = queryString.Query_Insert(table, fields, value);
		// System.out.println(t);
		if (process.Execute_DB(conn, t))
			System.out.println("Ok!");

	}

	public void deleteSceance(Sceance sceance) {
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String table = "Sceance";
		String condition = "id_sceance = '" + sceance.getId() + "'";
		String t = queryString.Query_Delete(table, condition);
		System.out.println(t);
		if (process.Execute_DB(conn, t))
			System.out.println("Ok!");
	}

	public void updateSceance(Sceance sceance) {
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		SimpleDateFormat formatterDate = new SimpleDateFormat("yy-MM-dd");
		SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm:ss");
		String table = "Sceance";
		String[] fields = { "id_module", " id_emploi", "date_sceance",
				"time_sceance" };
		String[] value = { sceance.getModule().getId(), sceance.getIdEmploi(),
				formatterDate.format(sceance.getDate()),
				formatterTime.format(sceance.getTime()) };
		String condition = "id_sceance = '" + sceance.getId() + "'";
		String t = queryString.Query_Update(table, fields, value, condition);
		// System.out.println(t);
		if (process.Execute_DB(conn, t))
			System.out.println("Ok!");
	}

	public void addEmploi(EmploiDuTemps emploi) {
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String table = "Emploi";
		String[] fields = { "id_emploi", "id_classe", };
		String[] value = { emploi.getId(), emploi.getClasse() };
		String t = queryString.Query_Insert(table, fields, value);
		// System.out.println(t);
		if (process.Execute_DB(conn, t))
			System.out.println("Ok!");

	}

	public void deleteEmploi(EmploiDuTemps emploi) throws ParseException {
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		List<Sceance> listSceance = new ArrayList<Sceance>();
		listSceance = findSceanceByEmploi(emploi.getId());
		for (Sceance sceance : listSceance) {
			deleteSceance(sceance);
		}
		String tableE = "Emploi";
		String conditionE = "id_emploi= '" + emploi.getId() + "'";
		String tE = queryString.Query_Delete(tableE, conditionE);
		// System.out.println(tE);
		if (process.Execute_DB(conn, tE))
			System.out.println("Ok!");
	}

	public void addModule(Module module) {
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String table = "Module";
		String[] fields = { "id_module", "id_prof", "name_module", "duree" };
		String[] value = { module.getId(), module.getIdProfessor(),
				module.getName(), Integer.toString(module.getDuree()) };
		String t = queryString.Query_Insert(table, fields, value);
		// System.out.println(t);
		if (process.Execute_DB(conn, t))
			System.out.println("Ok!");

	}

	public void deleteModule(Module module) throws ParseException {
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		List<Sceance> listSceance = new ArrayList<Sceance>();
		listSceance = findSceanceByModule(module.getId());
		for (Sceance sceance : listSceance) {
			deleteSceance(sceance);
		}
		String tableE = "Module";
		String conditionE = "id_module= '" + module.getId() + "'";
		String tE = queryString.Query_Delete(tableE, conditionE);
		// System.out.println(tE);
		if (process.Execute_DB(conn, tE))
			System.out.println("Ok!");
	}

	public void addProfessor(Professor professor) {
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		String table = "Professor";
		String[] fields = { "id_prof", "first_name", "last_name", "email" };
		String[] value = { professor.getId(), professor.getFirstName(),
				professor.getLastName(), professor.getEmail() };
		String t = queryString.Query_Insert(table, fields, value);
		// System.out.println(t);
		if (process.Execute_DB(conn, t))
			System.out.println("Ok!");

	}

	public void deleteProfessor(Professor professor) throws ParseException {
		ProcessDB process = new ProcessDB();
		Query queryString = new Query();
		List<Module> listModule = new ArrayList<Module>();
		listModule = findModuleByProfessor(professor.getId());
		for (Module module : listModule) {
			deleteModule(module);
		}
		String tableE = "Professor";
		String conditionE = "id_professor= '" + professor.getId() + "'";
		String tE = queryString.Query_Delete(tableE, conditionE);
		// System.out.println(tE);
		if (process.Execute_DB(conn, tE))
			System.out.println("Ok!");
	}
		
}
