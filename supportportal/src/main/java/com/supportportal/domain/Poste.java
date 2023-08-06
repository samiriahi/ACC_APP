package com.supportportal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@Entity
public class Poste implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "poste_id" , nullable = false, updatable = false)
	private Long idPoste ;
	@Column(name="nomPoste", length = 50, nullable = false)
	private String nomPoste ;
	@Column(name="nomReseau", length = 50, nullable = false)
	private String nomReseau ;
	@Column(name="cadence", length = 50, nullable = false)
	private String cadence ;
	@Column(name="dateCreation", nullable = false)
    private Date dateCreation ;
	@Column(name="dateMaj")
	private Date dateMaj ;
	
	
	@JsonIgnoreProperties("postes") //to solve Infinite Recursion problem
	@ManyToOne
	@JoinColumn(name = "ligneProd")
    private LigneProduction ligneProd ;
	
	

	public Poste() {
		super();
	}


	public Poste(Long idPoste, String nomPoste, String nomReseau, String cadence, Date dateCreation, Date dateMaj,
			LigneProduction ligneProd) {
		super();
		this.idPoste = idPoste;
		this.nomPoste = nomPoste;
		this.nomReseau = nomReseau;
		this.cadence = cadence;
		this.dateCreation = dateCreation;
		this.dateMaj = dateMaj;
		this.ligneProd = ligneProd;
	}


	public Long getIdPoste() {
		return idPoste;
	}


	public void setIdPoste(Long idPoste) {
		this.idPoste = idPoste;
	}


	public String getNomPoste() {
		return nomPoste;
	}


	public void setNomPoste(String nomPoste) {
		this.nomPoste = nomPoste;
	}


	public String getNomReseau() {
		return nomReseau;
	}


	public void setNomReseau(String nomReseau) {
		this.nomReseau = nomReseau;
	}


	public String getCadence() {
		return cadence;
	}


	public void setCadence(String cadence) {
		this.cadence = cadence;
	}


	public Date getDateCreation() {
		return dateCreation;
	}


	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	public Date getDateMaj() {
		return dateMaj;
	}


	public void setDateMaj(Date dateMaj) {
		this.dateMaj = dateMaj;
	}


	public LigneProduction getLigneProd() {
		return ligneProd;
	}


	public void setLigneProd(LigneProduction ligneProd) {
		this.ligneProd = ligneProd;
	}
	
	
	
	
	

}
