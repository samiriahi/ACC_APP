package com.supportportal.service.impl;

import static com.supportportal.constant.LigneProdConstant.LIGNE_ALREADY_EXISTS;
import static com.supportportal.constant.LigneProdConstant.LIGNE_NOT_FOUND;
import static com.supportportal.constant.UniteFabImplConstant.UNITE_FAB_NOT_FOUND_BY_CODE;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supportportal.domain.LigneProduction;
import com.supportportal.domain.UniteFabrication;
import com.supportportal.exception.domain.CodeLigneProdExistException;
import com.supportportal.exception.domain.LigneProdNotFoundException;
import com.supportportal.exception.domain.UniteFabNotFoundException;
import com.supportportal.repository.LigneProductionRepository;
import com.supportportal.repository.UniteFabricationRepository;
import com.supportportal.service.LigneProdService;

@Service
public class LigneProdServiceImpl implements LigneProdService{

	private LigneProductionRepository ligneProductionRepo ;
	private UniteFabricationRepository uniteFabRepository ;
	
	@Autowired
	public LigneProdServiceImpl( LigneProductionRepository ligneProductionRepo , UniteFabricationRepository uniteFabRepository) {
		this.ligneProductionRepo=ligneProductionRepo;
		this.uniteFabRepository=uniteFabRepository ;
	}
		
	
	@Override
	 public List<Map<String, Object>> getSommesCadences() {
	        List<LigneProduction> lignesProduction = ligneProductionRepo.findAll();
	        List<Map<String, Object>> sommesCadences = new ArrayList<>();

	        for (LigneProduction ligneProduction : lignesProduction) {
	            Map<String, Object> sommeCadences = new HashMap<>();
	            sommeCadences.put("ligneProduction", ligneProduction.getCodeLp());
	            sommeCadences.put("sommeCadences", ligneProduction.getSommeCadences());
	            sommesCadences.add(sommeCadences);
	        }

	        return sommesCadences;
	    }
	
	
	@Override
	public List<LigneProduction> getAllLigneProduction (){
		return ligneProductionRepo.findAll() ;
	}

	@Override
	public LigneProduction findLigneProductionByCodeLp(String codeLp) {
	    return ligneProductionRepo.findLigneProductionByCodeLp(codeLp) ;
	}
	
	
	@Override
	public LigneProduction addLigneProd(String codeLp, String robotTraitement, String observation , boolean status, String codeUf ) 
			throws CodeLigneProdExistException, LigneProdNotFoundException, UniteFabNotFoundException {
		
		UniteFabrication uniteFab = uniteFabRepository.findUniteFabricationByCodeUf(codeUf) ;
		validateNewCodeLp( null  , codeLp ) ;
		LigneProduction ligneProd = new LigneProduction() ;
		ligneProd.setCodeLp(codeLp);
		ligneProd.setRobotTraitement(robotTraitement);
		ligneProd.setObservation(observation);
		ligneProd.setDateMaj(new Date());
		ligneProd.setStatus(status);
		if( uniteFab == null ) {
			throw new UniteFabNotFoundException(UNITE_FAB_NOT_FOUND_BY_CODE + codeUf);
		}else {
			ligneProd.setUniteFab(uniteFab); ;
			uniteFab.getLignesProds().add(ligneProd) ;
			ligneProductionRepo.save(ligneProd);
			return ligneProd;
		}
	}
	
	
	@Override
	public LigneProduction updateLigneProd( String codeLp, String robotTraitement , boolean status) throws CodeLigneProdExistException, LigneProdNotFoundException, UniteFabNotFoundException {
		LigneProduction ligneProd = ligneProductionRepo.findLigneProductionByCodeLp(codeLp);
		ligneProd.setRobotTraitement(robotTraitement);
		ligneProd.setDateMaj(new Date());
		ligneProd.setStatus(status);
		ligneProductionRepo.save(ligneProd);
		return ligneProd ;
		
	}	
	
	@Override
	public void deleteLigneProd(String codeLp )  {
		LigneProduction ligneProd = ligneProductionRepo.findLigneProductionByCodeLp(codeLp);
		UniteFabrication uniteFab = ligneProd.getUniteFab() ;
		if (uniteFab== null) {
			ligneProductionRepo.deleteById(ligneProd.getIdLigneProd());
		}else {
			uniteFab.getLignesProds().remove(ligneProd) ;
			ligneProd.setUniteFab(null); 
			ligneProductionRepo.deleteById(ligneProd.getIdLigneProd());
		}
	}

	
	@Override
	public void removeUfFromLigneProd(String codeLp) {
		LigneProduction ligneProd =ligneProductionRepo.findLigneProductionByCodeLp(codeLp) ;
		UniteFabrication uniteFab =ligneProd.getUniteFab() ;
		uniteFab.getLignesProds().remove(ligneProd) ;
		ligneProd.setUniteFab(null) ;
		ligneProductionRepo.save(ligneProd) ;
	}
	
	
	private LigneProduction validateNewCodeLp(String currentCodeLp , String newCodeLp ) throws CodeLigneProdExistException , LigneProdNotFoundException {
		LigneProduction ligneProdByCode = findLigneProductionByCodeLp(newCodeLp) ;
		if (StringUtils.isNotBlank(currentCodeLp)) {
			LigneProduction currentLigneProd= findLigneProductionByCodeLp(currentCodeLp);
			LigneProduction ligneProdByNewCode = findLigneProductionByCodeLp(newCodeLp) ;
			if(currentLigneProd== null ) {
				throw new LigneProdNotFoundException(LIGNE_NOT_FOUND + currentCodeLp);
			}
			if (ligneProdByCode != null && !currentLigneProd.getIdLigneProd().equals(ligneProdByNewCode.getIdLigneProd())) {
				throw new CodeLigneProdExistException(LIGNE_ALREADY_EXISTS) ;
			}
			return currentLigneProd ;
		}else {
			if (ligneProdByCode != null ) {
				throw new CodeLigneProdExistException(LIGNE_ALREADY_EXISTS) ;
			}
			return null ;
		}
		
	}
	
	
	
}
