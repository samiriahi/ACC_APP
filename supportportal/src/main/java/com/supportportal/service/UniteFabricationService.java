package com.supportportal.service;

import java.io.IOException;
import java.util.List;

import com.supportportal.domain.LigneProduction;
import com.supportportal.domain.UniteFabrication;
import com.supportportal.exception.domain.CodeUniteFabExistException;
import com.supportportal.exception.domain.UniteFabNotFoundException;

public interface UniteFabricationService {

	UniteFabrication findUniteFabByCodeUf(String codeUf);

	List<UniteFabrication> getAllUniteFab();

	UniteFabrication addNewUniteFab(String codeUf, String mapa , Boolean status)throws CodeUniteFabExistException, UniteFabNotFoundException;

	UniteFabrication updateUniteFab ( String currentCodeUF, String newCodeUF, String mapa , Boolean status)throws CodeUniteFabExistException, UniteFabNotFoundException;

	void deleteUniteFab(String codeUf) throws IOException, UniteFabNotFoundException;

	List<LigneProduction> getAllLignesProdsByUF(String codeUf) throws UniteFabNotFoundException;

	void removeLigneProdFromUniteFab(String codeLp);

}
