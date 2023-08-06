package com.supportportal.controller;

import static com.supportportal.constant.UniteFabImplConstant.UNITE_FAB_DELETED;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supportportal.domain.HttpResponse;
import com.supportportal.domain.LigneProduction;
import com.supportportal.domain.UniteFabrication;
import com.supportportal.exception.ExceptionHandling;
import com.supportportal.exception.domain.CodeUniteFabExistException;
import com.supportportal.exception.domain.UniteFabNotFoundException;
import com.supportportal.service.UniteFabricationService;

@RestController
@RequestMapping(path = { "/", "/uniteFab"})
public class UniteFabController  extends ExceptionHandling  {

	private UniteFabricationService uniteFabService ;

	  @Autowired
	  public UniteFabController( UniteFabricationService uniteFabService) {
		this.uniteFabService = uniteFabService;
	  }
		
	  @PostMapping("/add")
	  public ResponseEntity<UniteFabrication> addNewUF(	@RequestParam("codeUf") String codeUf,
													 	@RequestParam("mapa") String mapa, 
													 	@RequestParam("status") String status ) throws CodeUniteFabExistException, UniteFabNotFoundException {
		  UniteFabrication newUF = uniteFabService.addNewUniteFab(codeUf,  mapa , Boolean.parseBoolean(status)) ;
		  return new  ResponseEntity<>(newUF,OK);
	  }
  
	  @PostMapping("/update")
	  //@PreAuthorize("hasAnyAuthority('user:update')")
	  public ResponseEntity<UniteFabrication> updateUniteFab(@RequestParam("currentCodeUf") String currentCodeUf ,
									                		 @RequestParam("codeUf") String codeUf,
									                		 @RequestParam("mapa") String mapa ,
									                		 @RequestParam("status") String status  ) throws CodeUniteFabExistException, UniteFabNotFoundException {
		  UniteFabrication updateUniteFab = uniteFabService.updateUniteFab(currentCodeUf , codeUf , mapa, Boolean.parseBoolean(status)) ;
		  return new  ResponseEntity<>(updateUniteFab, OK);
	  }
  
  	
	  @GetMapping("/find/{codeUf}")
	  public  ResponseEntity<UniteFabrication> getUniteFab (@PathVariable("codeUf") String codeUf) throws UniteFabNotFoundException {
		  UniteFabrication uniteFab = uniteFabService.findUniteFabByCodeUf(codeUf);
		  return new  ResponseEntity<>(uniteFab, OK);
	  }
	
	  @GetMapping("/list")
	  public  ResponseEntity<List<UniteFabrication>> getAllUniteFab () {
		  List <UniteFabrication> uniteFabs = uniteFabService.getAllUniteFab();
		  return new  ResponseEntity<>(uniteFabs, OK);
	  }
	  
	  
	  
	  @DeleteMapping("/delete/{codeUf}")
	  //@PreAuthorize("hasAnyAuthority('user:delete')")
		public ResponseEntity<HttpResponse> deleteUniteFab(@PathVariable("codeUf") String codeUf) throws IOException, UniteFabNotFoundException{
		  uniteFabService.deleteUniteFab(codeUf);
		return response(OK , UNITE_FAB_DELETED ) ;
	  }
	  
	  @GetMapping("/lignesProds/{codeUf}")
	  public  ResponseEntity<List<LigneProduction>> lignesProdByUF (@PathVariable("codeUf") String codeUf) throws UniteFabNotFoundException {
		List <LigneProduction> lignesProdByUF = uniteFabService.getAllLignesProdsByUF(codeUf);
		return new  ResponseEntity<>(lignesProdByUF, OK);
		}
	  
	  
	  private  ResponseEntity<HttpResponse> response (HttpStatus httpStatus , String message ){
			HttpResponse body = new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase()	,message.toUpperCase() )  ;
			return new  ResponseEntity<>( body , httpStatus ) ;
		}
	   
	  
}
