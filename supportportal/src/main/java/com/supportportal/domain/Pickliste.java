package com.supportportal.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Pickliste  implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "id_pickliste" , nullable = false, updatable = false)
	private Long  idPickliste ;
	
	@Column(name="approb_supp_par", length = 50, nullable = true)
	private String approbSuppPar;

	@Column(name="CodeProduit", length = 50, nullable = true)
	private String codeProduit;

	@Column(name="date_approb_suppression", nullable = true)
	private Timestamp dateApprobSuppression;

	@Column(name="date_demande_suppression", nullable = true)
	private Timestamp dateDemandeSuppression;

	@Column(name="DateCreation", nullable = true)
	private Timestamp dateCreation;

	@Column(name="DateLivraison", nullable = true)
	private Timestamp dateLivraison;

	@Column(name="DateMAj", nullable = true)
	private Timestamp dateMAj;

	@Column(name="DateServi", nullable = true)
	private Timestamp dateServi;

	@Column(name="demande_annulation", length = 10, nullable = true)
	private String demandeAnnulation;

	@Column(name="demande_supp_par", length = 50, nullable = true)
	private String demandeSuppPar;

	@Column(name="Hostame", length = 50, nullable = true)
	private String hostame;

	@Column(name="Magasin", length = 200, nullable = true)
	private String magasin;

	@Column(name="NbUSRecept", nullable = true)
	private int nbUSRecept;

	@Column(name="NbUSServi", nullable = true)
	private int nbUSServi;

	@Column(name="Num_PickList", length = 50, nullable = true)
	private String numPickList;

	@Column(name="Observation", length = 250, nullable = true)
	private String observation;

	@Column(name="PrintedServi", nullable = true)
	private boolean printedServi;

	@Column(name="TypePickList", length = 50, nullable = true)
	private String typePickList;
	
	
		
	//bi-directional many-to-one association to LigneProduction
	@ManyToOne
	@JsonIgnoreProperties("postes")
	@JoinColumn(name = "id_ligne_production")
	private LigneProduction ligneProduction;


	//bi-directional many-to-one association to Status
	@ManyToOne
	@JoinColumn(name="id_Status")
	private Status status;


	public Pickliste(Long idPickliste, String approbSuppPar, String codeProduit, Timestamp dateApprobSuppression,
			Timestamp dateDemandeSuppression, Timestamp dateCreation, Timestamp dateLivraison, Timestamp dateMAj,
			Timestamp dateServi, String demandeAnnulation, String demandeSuppPar, String hostame, String magasin,
			int nbUSRecept, int nbUSServi, String numPickList, String observation, boolean printedServi,
			String typePickList, LigneProduction ligneProduction, Status status) {
		super();
		this.idPickliste = idPickliste;
		this.approbSuppPar = approbSuppPar;
		this.codeProduit = codeProduit;
		this.dateApprobSuppression = dateApprobSuppression;
		this.dateDemandeSuppression = dateDemandeSuppression;
		this.dateCreation = dateCreation;
		this.dateLivraison = dateLivraison;
		this.dateMAj = dateMAj;
		this.dateServi = dateServi;
		this.demandeAnnulation = demandeAnnulation;
		this.demandeSuppPar = demandeSuppPar;
		this.hostame = hostame;
		this.magasin = magasin;
		this.nbUSRecept = nbUSRecept;
		this.nbUSServi = nbUSServi;
		this.numPickList = numPickList;
		this.observation = observation;
		this.printedServi = printedServi;
		this.typePickList = typePickList;
		this.ligneProduction = ligneProduction;
		this.status = status;
	}


	public Long getIdPickliste() {
		return idPickliste;
	}


	public void setIdPickliste(Long idPickliste) {
		this.idPickliste = idPickliste;
	}


	public String getApprobSuppPar() {
		return approbSuppPar;
	}


	public void setApprobSuppPar(String approbSuppPar) {
		this.approbSuppPar = approbSuppPar;
	}


	public String getCodeProduit() {
		return codeProduit;
	}


	public void setCodeProduit(String codeProduit) {
		this.codeProduit = codeProduit;
	}


	public Timestamp getDateApprobSuppression() {
		return dateApprobSuppression;
	}


	public void setDateApprobSuppression(Timestamp dateApprobSuppression) {
		this.dateApprobSuppression = dateApprobSuppression;
	}


	public Timestamp getDateDemandeSuppression() {
		return dateDemandeSuppression;
	}


	public void setDateDemandeSuppression(Timestamp dateDemandeSuppression) {
		this.dateDemandeSuppression = dateDemandeSuppression;
	}


	public Timestamp getDateCreation() {
		return dateCreation;
	}


	public void setDateCreation(Timestamp dateCreation) {
		this.dateCreation = dateCreation;
	}


	public Timestamp getDateLivraison() {
		return dateLivraison;
	}


	public void setDateLivraison(Timestamp dateLivraison) {
		this.dateLivraison = dateLivraison;
	}


	public Timestamp getDateMAj() {
		return dateMAj;
	}


	public void setDateMAj(Timestamp dateMAj) {
		this.dateMAj = dateMAj;
	}


	public Timestamp getDateServi() {
		return dateServi;
	}


	public void setDateServi(Timestamp dateServi) {
		this.dateServi = dateServi;
	}


	public String getDemandeAnnulation() {
		return demandeAnnulation;
	}


	public void setDemandeAnnulation(String demandeAnnulation) {
		this.demandeAnnulation = demandeAnnulation;
	}


	public String getDemandeSuppPar() {
		return demandeSuppPar;
	}


	public void setDemandeSuppPar(String demandeSuppPar) {
		this.demandeSuppPar = demandeSuppPar;
	}


	public String getHostame() {
		return hostame;
	}


	public void setHostame(String hostame) {
		this.hostame = hostame;
	}


	public String getMagasin() {
		return magasin;
	}


	public void setMagasin(String magasin) {
		this.magasin = magasin;
	}


	public int getNbUSRecept() {
		return nbUSRecept;
	}


	public void setNbUSRecept(int nbUSRecept) {
		this.nbUSRecept = nbUSRecept;
	}


	public int getNbUSServi() {
		return nbUSServi;
	}


	public void setNbUSServi(int nbUSServi) {
		this.nbUSServi = nbUSServi;
	}


	public String getNumPickList() {
		return numPickList;
	}


	public void setNumPickList(String numPickList) {
		this.numPickList = numPickList;
	}


	public String getObservation() {
		return observation;
	}


	public void setObservation(String observation) {
		this.observation = observation;
	}


	public boolean isPrintedServi() {
		return printedServi;
	}


	public void setPrintedServi(boolean printedServi) {
		this.printedServi = printedServi;
	}


	public String getTypePickList() {
		return typePickList;
	}


	public void setTypePickList(String typePickList) {
		this.typePickList = typePickList;
	}


	public LigneProduction getLigneProduction() {
		return ligneProduction;
	}


	public void setLigneProduction(LigneProduction ligneProduction) {
		this.ligneProduction = ligneProduction;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Pickliste() {
		super();
	}
		


	

	
	
	

	
}
