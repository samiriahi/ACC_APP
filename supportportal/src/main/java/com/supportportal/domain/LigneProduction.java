package com.supportportal.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "LigneProduction")
public class LigneProduction implements Serializable {

	private static final long serialVersionUID = 1L;
	

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "id_LigneProd" , nullable = false, updatable = false)
    private Long idLigneProd;
	
	@Column(name="code_LigneProd", length = 50, nullable = false)
    private String codeLp ;
	
	@Column(name="robot_Traitement", length = 50, nullable = false)
    private String robotTraitement ;
	
	@Column(name="Observation", length = 50 , nullable = false )
    private String observation ;
	
    @Column(name="dateMaj", nullable = false)
    private Date dateMaj ;
    
    @Column(name="Status", nullable = false)
    private Boolean status ;
   
    
    
    
    @JsonManagedReference
    @OneToMany
    private List<Poste> postes ;
    
    
    
 	@JsonIgnoreProperties("lignesProds") //to solve Infinite Recursion problem
    @ManyToOne
    @JoinColumn(name = "UF")
    private UniteFabrication  uniteFab ;

      
    
	public LigneProduction() {
		super();
	}


	public LigneProduction(Long idLigneProd, String codeLp, String robotTraitement, String observation, Date dateMaj,
			Boolean status, UniteFabrication uniteFab) {
		this.idLigneProd = idLigneProd;
		this.codeLp = codeLp;
		this.robotTraitement = robotTraitement;
		this.observation = observation;
		this.dateMaj = dateMaj;
		this.status = status;
		this.uniteFab = uniteFab;
	}

	 public int getSommeCadences() {
	        int sommeCadences = 0;
	        for (Poste poste : postes) {
	            // Convertir la valeur de la cadence de String à int
	            int cadence = Integer.parseInt(poste.getCadence());
	            sommeCadences += cadence;
	        }
	        return sommeCadences;
	    }


   public List<Poste> getPostes() {
		return postes;
	}

   public void setPostes(List<Poste> postes) {
		this.postes = postes;
	}

	public Long getIdLigneProd() {
		return idLigneProd;
	}


	public void setIdLigneProd(Long idLigneProd) {
		this.idLigneProd = idLigneProd;
	}




	public String getCodeLp() {
		return codeLp;
	}


	public void setCodeLp(String codeLp) {
		this.codeLp = codeLp;
	}



	public String getRobotTraitement() {
		return robotTraitement;
	}


	public void setRobotTraitement(String robotTraitement) {
		this.robotTraitement = robotTraitement;
	}


	public String getObservation() {
		return observation;
	}


	public void setObservation(String observation) {
		this.observation = observation;
	}


	public Date getDateMaj() {
		return dateMaj;
	}


	public void setDateMaj(Date dateMaj) {
		this.dateMaj = dateMaj;
	}


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}




	public UniteFabrication getUniteFab() {
		return uniteFab;
	}



	public void setUniteFab(UniteFabrication uniteFab) {
		this.uniteFab = uniteFab;
	}


	
    
    
    
    
    

}
