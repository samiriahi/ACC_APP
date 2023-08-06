package com.supportportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.supportportal.domain.Poste;

@Repository
public interface PosteRepository  extends JpaRepository<Poste, Long>{

	Poste findPosteByIdPoste(Long idPoste) ;
	Poste findPosteByNomPoste(String nomPoste) ;
	
}
