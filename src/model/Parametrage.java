package model;

public class Parametrage {
	private Integer id;
	private double coef_ndossier_nfinal;
	private double coef_necrit_nfinal;
	private double coef_norale_nfinal;
	private double porc_ecrit_non_lmd;
	private double porc_ecrit_lmd;
	private double porc_ecrit_ista;
	private double porc_ecrit_cp;
	private double porc_orale_non_lmd;
	private double porc_orale_lmd;
	private double porc_orale_ista;
	private double porc_orale_cp;
		
	public Parametrage(double coef_ndossier_nfinal, double coef_necrit_nfinal, double coef_norale_nfinal,
			double porc_ecrit_non_lmd, double porc_ecrit_lmd, double porc_ecrit_ista, double porc_ecrit_cp,
			double porc_orale_non_lmd, double porc_orale_lmd, double porc_orale_ista, double porc_orale_cp) {
		super();
		this.coef_ndossier_nfinal = coef_ndossier_nfinal;
		this.coef_necrit_nfinal = coef_necrit_nfinal;
		this.coef_norale_nfinal = coef_norale_nfinal;
		this.porc_ecrit_non_lmd = porc_ecrit_non_lmd;
		this.porc_ecrit_lmd = porc_ecrit_lmd;
		this.porc_ecrit_ista = porc_ecrit_ista;
		this.porc_ecrit_cp = porc_ecrit_cp;
		this.porc_orale_non_lmd = porc_orale_non_lmd;
		this.porc_orale_lmd = porc_orale_lmd;
		this.porc_orale_ista = porc_orale_ista;
		this.porc_orale_cp = porc_orale_cp;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public double getCoef_ndossier_nfinal() {
		return coef_ndossier_nfinal;
	}
	public void setCoef_ndossier_nfinal(double coef_ndossier_nfinal) {
		this.coef_ndossier_nfinal = coef_ndossier_nfinal;
	}
	public double getCoef_necrit_nfinal() {
		return coef_necrit_nfinal;
	}
	public void setCoef_necrit_nfinal(double coef_necrit_nfinal) {
		this.coef_necrit_nfinal = coef_necrit_nfinal;
	}
	public double getCoef_norale_nfinal() {
		return coef_norale_nfinal;
	}
	public void setCoef_norale_nfinal(double coef_norale_nfinal) {
		this.coef_norale_nfinal = coef_norale_nfinal;
	}
	public double getPorc_ecrit_non_lmd() {
		return porc_ecrit_non_lmd;
	}
	public void setPorc_ecrit_non_lmd(double porc_ecrit_non_lmd) {
		this.porc_ecrit_non_lmd = porc_ecrit_non_lmd;
	}
	public double getPorc_ecrit_lmd() {
		return porc_ecrit_lmd;
	}
	public void setPorc_ecrit_lmd(double porc_ecrit_lmd) {
		this.porc_ecrit_lmd = porc_ecrit_lmd;
	}
	public double getPorc_ecrit_ista() {
		return porc_ecrit_ista;
	}
	public void setPorc_ecrit_ista(double porc_ecrit_ista) {
		this.porc_ecrit_ista = porc_ecrit_ista;
	}
	public double getPorc_ecrit_cp() {
		return porc_ecrit_cp;
	}
	public void setPorc_ecrit_cp(double porc_ecrit_cp) {
		this.porc_ecrit_cp = porc_ecrit_cp;
	}
	public double getPorc_orale_non_lmd() {
		return porc_orale_non_lmd;
	}
	public void setPorc_orale_non_lmd(double porc_orale_non_lmd) {
		this.porc_orale_non_lmd = porc_orale_non_lmd;
	}
	public double getPorc_orale_lmd() {
		return porc_orale_lmd;
	}
	public void setPorc_orale_lmd(double porc_orale_lmd) {
		this.porc_orale_lmd = porc_orale_lmd;
	}
	public double getPorc_orale_ista() {
		return porc_orale_ista;
	}
	public void setPorc_orale_ista(double porc_orale_ista) {
		this.porc_orale_ista = porc_orale_ista;
	}
	public double getPorc_orale_cp() {
		return porc_orale_cp;
	}
	public void setPorc_orale_cp(double porc_orale_cp) {
		this.porc_orale_cp = porc_orale_cp;
	}
}
