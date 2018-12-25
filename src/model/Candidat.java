package model;

public class Candidat implements Comparable<Candidat> {
	
	private Integer id;
	private String num;
	private String nom;
	private String prenom;
	private String etablissement;
	private String ville;
	private String diplome;
	private String type_diplome;
	private String specialite;
	private double note_dossier;
	private double note_test_ecrit;
	private double passe_ecrit;
	private double passe_orale;
	private Jury jury;
	
	public Candidat() {
		super();
	}

	public Candidat(String num, String nom, String prenom, String etablissement, String ville,
			String diplome, String type_diplome, String specialite, double note_dossier) {
		super();
		this.num = num;
		this.nom = nom;
		this.prenom = prenom;
		this.etablissement = etablissement;
		this.ville = ville;
		this.diplome = diplome;
		this.type_diplome = type_diplome;
		this.specialite = specialite;
		this.note_dossier = note_dossier;
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

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEtablissement() {
		return etablissement;
	}

	public void setEtablissement(String etablissement) {
		this.etablissement = etablissement;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getDiplome() {
		return diplome;
	}

	public void setDiplome(String diplome) {
		this.diplome = diplome;
	}

	public String getType_diplome() {
		return type_diplome;
	}

	public void setType_diplome(String type_diplome) {
		this.type_diplome = type_diplome;
	}

	public String getSpecialite() {
		return specialite;
	}

	public void setSpecialite(String specialite) {
		this.specialite = specialite;
	}

	public double getNote_dossier() {
		return note_dossier;
	}

	public void setNote_dossier(double note_dossier) {
		this.note_dossier = note_dossier;
	}

	public double getNote_test_ecrit() {
		return note_test_ecrit;
	}

	public void setNote_test_ecrit(double note_test_ecrit) {
		this.note_test_ecrit = note_test_ecrit;
	}

	public double getPasse_ecrit() {
		return passe_ecrit;
	}

	public void setPasse_ecrit(double passe_ecrit) {
		this.passe_ecrit = passe_ecrit;
	}

	public double getPasse_orale() {
		return passe_orale;
	}

	public void setPasse_orale(double passe_orale) {
		this.passe_orale = passe_orale;
	}

	public Jury getJury() {
		return jury;
	}

	public void setJury(Jury jury) {
		this.jury = jury;
	}

	@Override
	public String toString() {
		return "Candidat [id=" + id + ", num=" + num + ", nom=" + nom + ", prenom=" + prenom + ", etablissement="
				+ etablissement + ", ville=" + ville + ", diplome=" + diplome + ", type_diplome=" + type_diplome
				+ ", specialite=" + specialite + ", note_dossier=" + note_dossier + "]";
	}

	@Override
	public int compareTo(Candidat o) {
		return -Double.compare(this.getNote_dossier(), o.getNote_dossier());
	}
	
}
