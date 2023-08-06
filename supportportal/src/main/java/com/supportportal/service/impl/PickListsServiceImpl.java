package com.supportportal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supportportal.domain.Pickliste;
import com.supportportal.repository.PickListeRepository;
import com.supportportal.service.PickListsService;

@Service
public class PickListsServiceImpl implements PickListsService {

	
	@Autowired
    private PickListeRepository pickListsRepository ;
	
	
	public PickListsServiceImpl(PickListeRepository pickListsRepository) {
        this.pickListsRepository = pickListsRepository;
    }
	
	@Override
	public List<Pickliste> getPickLists(String uf, String mag) {
		return pickListsRepository.findPickListsByUfAndMagasin(uf, mag);
	}
	
	@Override
	public long getNombrePicklistesDemander() {
	    return pickListsRepository.countByStatus_Code("DEMANDER");
	}
	
	@Override
	 public long getNombrePicklistesServi() {
	    return pickListsRepository.countByStatus_Code("SERVI");
	}
}


	

