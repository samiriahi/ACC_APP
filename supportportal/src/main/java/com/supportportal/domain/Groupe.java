package com.supportportal.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class Groupe implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "groupe_id" , nullable = false, updatable = false)
    private Long idGroupe;
	
	@Column(name="NomGroupe", length = 50, nullable = false)
    private String nomGroupe ;
	
    @Column(name="DateCreation", nullable = false)
    private Date dateCreation ;
    
    @Column(name="active", nullable = false)
    private Boolean isActive ;
    
    
    
    @JsonManagedReference
    @OneToMany
    private List<User> members;
    
    
	public Groupe() {}


	


	public Groupe(Long idGroupe, String nomGroupe, Date dateCreation, Boolean isActive, List<User> members) {
		super();
		this.idGroupe = idGroupe;
		this.nomGroupe = nomGroupe;
		this.dateCreation = dateCreation;
		this.isActive = isActive;
		this.members = members;
	}





	public Long getIdGroupe() {
		return idGroupe;
	}


	public void setIdGroupe(Long idGroupe) {
		this.idGroupe = idGroupe;
	}


	public String getNomGroupe() {
		return nomGroupe;
	}


	public void setNomGroupe(String nomGroupe) {
		this.nomGroupe = nomGroupe;
	}


	public Date getDateCreation() {
		return dateCreation;
	}


	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}


	public Boolean getIsActive() {
		return isActive;
	}


	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}


	public List<User> getMembers() {
		return members;
	}


	public void setMembers(List<User> members) {
		this.members = members;
	}


	

	
    
    
    
    
}
