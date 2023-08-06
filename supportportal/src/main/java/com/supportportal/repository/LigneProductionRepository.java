package com.supportportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.supportportal.domain.LigneProduction;


@Repository
public interface LigneProductionRepository extends JpaRepository<LigneProduction, Long>  {
	
	LigneProduction findLigneProductionByCodeLp(String codeLp) ;
	LigneProduction findLigneProductionByIdLigneProd(long idLigneProd) ;

}
