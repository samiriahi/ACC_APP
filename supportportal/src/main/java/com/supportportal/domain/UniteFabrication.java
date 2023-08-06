package com.supportportal.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "UniteFabrication")
public class UniteFabrication   implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "id_UF" , nullable = false, updatable = false)
    private Long idUf;
	
	@Column(name="code", length = 50, nullable = false)
    private String codeUf ;
	
    @Column(name="mapa", nullable = false)
    private String mapa ;
    
    @Column(name="status", nullable = false)
    private Boolean status ;

    @JsonManagedReference
    @OneToMany
    private List<LigneProduction> lignesProds ;
    
    
    

	public UniteFabrication() {
		super();
	}

    
	public UniteFabrication(Long idUf, String codeUf, String mapa, Boolean status , List<LigneProduction> lignesProds) {
		super();
		this.idUf = idUf;
		this.codeUf = codeUf;
		this.mapa = mapa;
		this.status = status;
		this.lignesProds = lignesProds ;
	}

	
	public Long getIdUf() {
		return idUf;
	}

	public void setIdUf(Long idUf) {
		this.idUf = idUf;
	}

	
	public String getCodeUf() {
		return codeUf;
	}


	public void setCodeUf(String codeUf) {
		this.codeUf = codeUf;
	}


	public String getMapa() {
		return mapa;
	}

	public void setMapa(String mapa) {
		this.mapa = mapa;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}


	public List<LigneProduction> getLignesProds() {
		return lignesProds;
	}


	public void setLignesProds(List<LigneProduction> lignesProds) {
		this.lignesProds = lignesProds;
	}
	
	
    
   
}
