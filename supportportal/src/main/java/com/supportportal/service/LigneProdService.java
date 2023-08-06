package com.supportportal.service;

import java.util.List;
import java.util.Map;

import com.supportportal.domain.LigneProduction;
import com.supportportal.exception.domain.CodeLigneProdExistException;
import com.supportportal.exception.domain.LigneProdNotFoundException;
import com.supportportal.exception.domain.UniteFabNotFoundException;

public interface LigneProdService {

	LigneProduction addLigneProd(String codeLp, String robotTraitement, String observation, boolean status, String codeUf) throws CodeLigneProdExistException, LigneProdNotFoundException, UniteFabNotFoundException;

	LigneProduction findLigneProductionByCodeLp(String codeLp) ;

	List<LigneProduction> getAllLigneProduction();


	void removeUfFromLigneProd(String codeLp);


	LigneProduction updateLigneProd( String codeLp, String robotTraitement , boolean status) throws CodeLigneProdExistException, LigneProdNotFoundException, UniteFabNotFoundException ;

	void deleteLigneProd(String codeLp) ;

	List<Map<String, Object>> getSommesCadences();

}