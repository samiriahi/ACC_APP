package com.supportportal.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CauseServi {


	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "idCauseServi" , nullable = false, updatable = false)
	private Long idCauseServi ;
	

	@Column(name="cause", length = 50, nullable = false)
	private String cause ;
	

	@Column(name="description", length = 50, nullable = false)
	private String description ;
	
	@Column(name="dateCreation", length = 50, nullable = false)
	private Date dateCreation ;
	
	
	
}
