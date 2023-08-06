package com.supportportal.service.impl;

import static com.supportportal.constant.UniteFabImplConstant.UNITE_FAB_ALREADY_EXISTS;
import static com.supportportal.constant.UniteFabImplConstant.UNITE_FAB_NOT_FOUND_BY_CODE;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supportportal.domain.LigneProduction;
import com.supportportal.domain.UniteFabrication;
import com.supportportal.exception.domain.CodeUniteFabExistException;
import com.supportportal.exception.domain.UniteFabNotFoundException;
import com.supportportal.repository.LigneProductionRepository;
import com.supportportal.repository.UniteFabricationRepository;
import com.supportportal.service.UniteFabricationService;

@Service
public class UniteFabServiceImpl implements UniteFabricationService {
	
	private UniteFabricationRepository uniteFabRepository ;
	private LigneProductionRepository ligneProductionRepo ;
	
	@Autowired
	public UniteFabServiceImpl(UniteFabricationRepository uniteFabRepository , LigneProductionRepository ligneProductionRepo ) {
		super();
		this.uniteFabRepository = uniteFabRepository ;
		this.ligneProductionRepo=ligneProductionRepo ;
	
	}
	
	@Override
	public List<UniteFabrication> getAllUniteFab() {
		return uniteFabRepository.findAll();
	}
	
	@Override
	public UniteFabrication findUniteFabByCodeUf(String codeUf) {
	    return uniteFabRepository.findUniteFabricationByCodeUf(codeUf);
	}
	  
	@Override
	public UniteFabrication addNewUniteFab (String codeUf, String mapa ,Boolean status ) throws CodeUniteFabExistException, UniteFabNotFoundException {
		validateNewUniteFab(null, codeUf) ;
		UniteFabrication uniteFab = new UniteFabrication() ;
		uniteFab.setCodeUf(codeUf);
		uniteFab.setMapa(mapa);
		uniteFab.setStatus(status);
    	uniteFabRepository.save(uniteFab);
		return uniteFab;
	}
	
	@Override
	public UniteFabrication updateUniteFab (String currentCodeUF, String newCodeUF, String mapa ,Boolean status ) throws CodeUniteFabExistException, UniteFabNotFoundException {
		UniteFabrication currentUF =  validateNewUniteFab(currentCodeUF, newCodeUF) ;
		currentUF.setCodeUf(newCodeUF);
		currentUF.setMapa(mapa);
		currentUF.setStatus(status);
		uniteFabRepository.save(currentUF) ;
		return currentUF;
	
	}
	
	
	
	@Override
	public void deleteUniteFab(String codeUf ) throws IOException, UniteFabNotFoundException {
		UniteFabrication uniteFab = uniteFabRepository.findUniteFabricationByCodeUf(codeUf) ;
		List<LigneProduction> lignesProds = getAllLignesProdsByUF(codeUf) ;
		for (LigneProduction ligneProd : lignesProds){
			ligneProd.setUniteFab(null);
			uniteFab.getLignesProds().remove(lignesProds) ;
		}
		uniteFabRepository.deleteById(uniteFab.getIdUf());
	}
	
	
	@Override
	public List<LigneProduction> getAllLignesProdsByUF(String codeUf) throws UniteFabNotFoundException{
		UniteFabrication uniteFab = uniteFabRepository.findUniteFabricationByCodeUf(codeUf) ;
		if (uniteFab==null){
			throw new UniteFabNotFoundException(UNITE_FAB_NOT_FOUND_BY_CODE + codeUf); 
			}
		else
			{
			return uniteFab.getLignesProds(); 
			}
	}
	
	@Override
	public void removeLigneProdFromUniteFab(String codeLp) {
		LigneProduction ligneProd = ligneProductionRepo.findLigneProductionByCodeLp(codeLp) ;
		UniteFabrication uniteFab = ligneProd.getUniteFab() ;
		uniteFab.getLignesProds().remove(ligneProd) ;
		ligneProd.setUniteFab(null);
		ligneProductionRepo.save(ligneProd) ;
	}
	
	
	
	private UniteFabrication validateNewUniteFab(String currentCodeUF , String newCodeUF ) throws CodeUniteFabExistException , UniteFabNotFoundException {
		UniteFabrication uniteFabByCode = findUniteFabByCodeUf(newCodeUF) ;
		if (StringUtils.isNotBlank(currentCodeUF)) {
			UniteFabrication currentUniteFab = findUniteFabByCodeUf(currentCodeUF);
			UniteFabrication uniteFabByNewCode = findUniteFabByCodeUf(newCodeUF) ;
			if(currentUniteFab== null ) {
				throw new UniteFabNotFoundException(UNITE_FAB_NOT_FOUND_BY_CODE + currentCodeUF);
			}
			if (uniteFabByNewCode != null && !currentUniteFab.getIdUf().equals(uniteFabByNewCode.getIdUf())) {
				throw new CodeUniteFabExistException(UNITE_FAB_ALREADY_EXISTS) ;
			}
			return currentUniteFab ;
		}else {
			if (uniteFabByCode != null ) {
				throw new CodeUniteFabExistException(UNITE_FAB_ALREADY_EXISTS) ;
			}
			return null ;
		}
	}
	

	
}
