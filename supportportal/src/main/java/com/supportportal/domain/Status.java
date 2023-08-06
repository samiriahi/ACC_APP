package com.supportportal.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Status implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "id_Status" , nullable = false, updatable = false)
	private Long  idStatus ;
	
	@Column(name="code", length = 50, nullable = false)
	private String code ;

	
	//bi-directional many-to-one association to PickList
	@OneToMany(mappedBy="status")
	@JsonIgnore
	private List<Pickliste> pickLists;

	
	

	public Status() {
		super();
	}

	



	public Status(Long idStatus, String code, List<Pickliste> pickLists) {
		super();
		this.idStatus = idStatus;
		this.code = code;
		this.pickLists = pickLists;
	}





	public Long getIdStatus() {
		return idStatus;
	}




	public void setIdStatus(Long idStatus) {
		this.idStatus = idStatus;
	}




	public String getCode() {
		return code;
	}




	public void setCode(String code) {
		this.code = code;
	}




	public List<Pickliste> getPickLists() {
		return pickLists;
	}




	public void setPickLists(List<Pickliste> pickLists) {
		this.pickLists = pickLists;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	
	

}
