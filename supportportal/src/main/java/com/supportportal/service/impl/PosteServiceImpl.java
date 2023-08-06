package com.supportportal.service.impl;

import static com.supportportal.constant.LigneProdConstant.LIGNE_NOT_FOUND;
import static com.supportportal.constant.PosteConstant.POSTE_EXISTE;
import static com.supportportal.constant.PosteConstant.POSTE_NOT_FOUND;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.supportportal.domain.LigneProduction;
import com.supportportal.domain.Poste;
import com.supportportal.exception.domain.LigneProdNotFoundException;
import com.supportportal.exception.domain.PosteExisteException;
import com.supportportal.exception.domain.PosteNotFoundException;
import com.supportportal.repository.LigneProductionRepository;
import com.supportportal.repository.PosteRepository;
import com.supportportal.service.PosteService;

@Service
public class PosteServiceImpl implements PosteService {

	private PosteRepository posteRepository ;
	private LigneProductionRepository ligneProdRepository ;
	
	
	public PosteServiceImpl(PosteRepository posteRepository , LigneProductionRepository ligneProdRepository  ) {
		super();
		this.posteRepository = posteRepository ;
		this.ligneProdRepository = ligneProdRepository ;
	}
	
	
	@Override
    public List<Poste> getPostes() {
        return posteRepository.findAll();
    }

	
	 @Override
	 public long getTotalPostes() {
	     return posteRepository.count();
	  }
	
    @Override
    public Poste findPosteByIdPoste(Long idPoste) {
        return posteRepository.findPosteByIdPoste(idPoste);
    }

	@Override
    public Poste findPosteByNomPoste(String nomPoste) {
        return posteRepository.findPosteByNomPoste(nomPoste);
    }

	@Override
	public Poste addPoste ( String nomPoste , String nomReseau , String cadence ) throws PosteExisteException, PosteNotFoundException {
		validateNewPoste( null , nomPoste) ;
 		Poste poste = new Poste() ;
		poste.setNomPoste(nomPoste);
		poste.setNomReseau(nomReseau);
		poste.setCadence(cadence);
		poste.setDateCreation(new Date());
		poste.setDateMaj(null);
		posteRepository.save(poste);
		return poste ;
	}
	
	
	@Override
	public Poste updatePoste( String nomPoste, String nomReseau , String cadence) throws PosteNotFoundException{
		Poste poste = posteRepository.findPosteByNomPoste(nomPoste) ;
		if (poste == null) {
			throw new PosteNotFoundException(POSTE_NOT_FOUND + nomPoste);
		} else {
			poste.setNomReseau(nomReseau);
			poste.setCadence(cadence);
			poste.setDateMaj(new Date());
			posteRepository.save(poste);
			return poste ;
		}		
	}
	
	
	@Override
	public void deletePoste(String nomPoste ) throws IOException {
		Poste poste = posteRepository.findPosteByNomPoste(nomPoste) ;
		LigneProduction ligneProd = poste.getLigneProd() ;
		if (ligneProd== null) {
			posteRepository.deleteById(poste.getIdPoste());
		}else {
			ligneProd.getPostes().remove(poste) ;
			posteRepository.deleteById(poste.getIdPoste());
		}
	}
	
	
	
	@Override
	public void assignPosteToLigneProd (String codeLp , String nomPoste) throws LigneProdNotFoundException , PosteNotFoundException {
		LigneProduction ligneProd= ligneProdRepository.findLigneProductionByCodeLp(codeLp) ;
		Poste  poste = posteRepository.findPosteByNomPoste(nomPoste) ;
		if (poste==null) {
			throw new PosteNotFoundException(POSTE_NOT_FOUND + nomPoste ) ;
		}
		if (ligneProd==null) {
			throw new LigneProdNotFoundException(LIGNE_NOT_FOUND + codeLp); 
		} else {
			poste.setLigneProd(ligneProd);
			ligneProd.getPostes().add(poste);
			posteRepository.save(poste) ;
		}
	}
	
	
	@Override
	public List<Poste> getPostesByLigneProd(String codeLp) throws LigneProdNotFoundException{
		LigneProduction ligneProd = ligneProdRepository.findLigneProductionByCodeLp(codeLp) ;
		if (ligneProd ==null){
			throw new LigneProdNotFoundException(LIGNE_NOT_FOUND + codeLp); 
			}
		else
			{
			return ligneProd.getPostes(); 
			}
	}
	
	
	@Override
	public void removePosteFromLigneProd(String nomPoste) {
		Poste poste = posteRepository.findPosteByNomPoste(nomPoste) ;
		LigneProduction ligneProd = poste.getLigneProd() ;
		ligneProd.getPostes().remove(poste) ;
		poste.setLigneProd(null);
		posteRepository.save(poste) ;
	}
	
	
	private Poste validateNewPoste(String currentNomPoste , String newNomPoste ) throws PosteExisteException , PosteNotFoundException {
		Poste posteByNom = findPosteByNomPoste(newNomPoste) ;
		if (StringUtils.isNotBlank(currentNomPoste)) {
			Poste currentPoste= findPosteByNomPoste(currentNomPoste);
			Poste posteByNewNom = findPosteByNomPoste(newNomPoste) ;
			if(currentPoste== null ) {
				throw new PosteNotFoundException(POSTE_NOT_FOUND + currentNomPoste);
			}
			if (posteByNom != null && !currentPoste.getIdPoste().equals(posteByNewNom.getIdPoste())) {
				throw new PosteExisteException(POSTE_EXISTE) ;
			}
			return currentPoste ;
		}else {
			if (posteByNom != null ) {
				throw new PosteExisteException(POSTE_EXISTE) ;
			}
			return null ;
		}
	}

	
}
