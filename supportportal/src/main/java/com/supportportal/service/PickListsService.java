package com.supportportal.service;

import java.util.List;

import com.supportportal.domain.Pickliste;

public interface PickListsService {

	List<Pickliste> getPickLists(String uf, String mag);

	long getNombrePicklistesServi();

	long getNombrePicklistesDemander();

}
