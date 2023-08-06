package com.supportportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supportportal.domain.Groupe;

@Repository
public interface GroupeRepository  extends JpaRepository< Groupe, Long>  {
	Groupe findGroupeByNomGroupe(String nomGroupe) ;
	Groupe findGroupeByIdGroupe(long idGroupe) ;
}
