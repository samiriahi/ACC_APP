package com.supportportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.supportportal.domain.UniteFabrication;

@Repository
public interface UniteFabricationRepository  extends JpaRepository<UniteFabrication, Long>{

	UniteFabrication findUniteFabricationByCodeUf(String codeUf) ;
	UniteFabrication findUniteFabricationByIdUf(long idUf) ;
	
}
