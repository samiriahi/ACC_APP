package com.supportportal.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.supportportal.domain.Pickliste;
import com.supportportal.service.PickListsService;

@RestController
@RequestMapping("/pickliste")
public class PicklisteController {

	
	 private  PickListsService pickListService;
	 
	 
	 public PicklisteController(PickListsService pickListService) {
	        this.pickListService = pickListService;
	    }
	 
	 
	 @GetMapping("/{uf}/{mag}")
	    public List<Pickliste> findByCode(@PathVariable("uf") String uf, @PathVariable("mag") String mag) {
	        return pickListService.getPickLists(uf, mag);
	    }
	 
	 @GetMapping("/demandes/count")
	    public long getNombrePicklistesDemander() {
	        return pickListService.getNombrePicklistesDemander();
	    }

	 @GetMapping("/servis/count")
	    public long getNombrePicklistesServi() {
	        return pickListService.getNombrePicklistesServi();
	   }
	 
}
