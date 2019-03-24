package com.night4us.entity.backoffice;

public class DemandeBar {
	public int id, capacite, dispos, tel;
	public String nom, mail, adresse;
	
	public DemandeBar(int id, String nom, int capacite, int tel, String mail, int dispos, String adresse){
		this.id = id;
		this.nom = nom;
		this.capacite = capacite;
		this.tel = tel;
		this.mail = mail;
		this.dispos = dispos;
		this.adresse = adresse;
	}
}
