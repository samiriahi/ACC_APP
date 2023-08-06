package com.supportportal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Magasin  implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column( name = "id_magasin" , nullable = false, updatable = false) 
	private Long idMagasin ;
	
	@Column(name="code_magasin", length = 50, nullable = false)
	private String codeMg ;
	
	@Column(name="dateCreation", nullable = false)
    private Date dateCreation ;
			
	public Magasin() {
		super();
	}

}
