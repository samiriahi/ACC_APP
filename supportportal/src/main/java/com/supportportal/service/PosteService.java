package com.supportportal.service;

import java.io.IOException;
import java.util.List;

import com.supportportal.domain.Poste;
import com.supportportal.exception.domain.LigneProdNotFoundException;
import com.supportportal.exception.domain.PosteExisteException;
import com.supportportal.exception.domain.PosteNotFoundException;

public interface PosteService {

	Poste findPosteByIdPoste(Long idPoste);

	List<Poste> getPostes();

	Poste findPosteByNomPoste(String nomPoste);

	Poste addPoste(String nomPoste, String nomReseau, String cadence)throws PosteExisteException, PosteNotFoundException;

	Poste updatePoste(String nomPoste, String nomReseau, String cadence) throws PosteNotFoundException ;

	void deletePoste(String nomPoste) throws IOException ;

	void assignPosteToLigneProd(String codeLp, String nomPoste) throws LigneProdNotFoundException , PosteNotFoundException ;

	List<Poste> getPostesByLigneProd(String codeLp) throws LigneProdNotFoundException;

	void removePosteFromLigneProd(String nomPoste);

	long getTotalPostes();

}
