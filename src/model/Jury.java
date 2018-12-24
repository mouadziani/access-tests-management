package model;

import java.util.ArrayList;

public class Jury {
	private Integer id;
	private String nom;
	private ArrayList<Jury> juries;

	public Jury() {
		super();
	}

	public Jury(String nom) {
		super();
		this.nom = nom;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}	
	
	public ArrayList<Jury> getJuries() {
		return juries;
	}

	public void setJuries(ArrayList<Jury> juries) {
		this.juries = juries;
	}
}
