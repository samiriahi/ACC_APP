package com.supportportal.controller;

import static com.supportportal.constant.LigneProdConstant.LIGNE_DELETED_SUCCESSFULY;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import com.supportportal.exception.ExceptionHandling;
import com.supportportal.exception.domain.CodeLigneProdExistException;
import com.supportportal.exception.domain.LigneProdNotFoundException;
import com.supportportal.exception.domain.UniteFabNotFoundException;
import com.supportportal.service.LigneProdService;

@RestController
@RequestMapping(path = { "/", "/ligneProd"})
public class LigneProdController  extends ExceptionHandling  {

	
	private LigneProdService ligneProdService ;
	
	@Autowired
	public LigneProdController( LigneProdService ligneProdService ) {
		this.ligneProdService=ligneProdService ;
	}
	
	
	@GetMapping("/sommes-cadences")
    public List<Map<String, Object>> getSommesCadences() {
        return ligneProdService.getSommesCadences();
    }
	
	@PostMapping("/add")
	public ResponseEntity<LigneProduction> addNewLigneProd(@RequestParam("codeLp") String codeLp,
														   @RequestParam("robotTraitement") String robotTraitement,
														   @RequestParam("observation") String observation,
														   @RequestParam("status") String status  ,
														   @RequestParam("codeUf") String codeUf) throws CodeLigneProdExistException, LigneProdNotFoundException, UniteFabNotFoundException  {
		LigneProduction newLigneProd = ligneProdService.addLigneProd(codeLp, robotTraitement, observation,  Boolean.parseBoolean(status), codeUf) ;
		return new  ResponseEntity<>(newLigneProd,OK) ;
	}
	
	@PostMapping("/update")
	public ResponseEntity<LigneProduction> updateLigneProd(@RequestParam("codeLp") String codeLp,
														   @RequestParam("robotTraitement") String robotTraitement,
														   @RequestParam("status") String status ) throws CodeLigneProdExistException, LigneProdNotFoundException, UniteFabNotFoundException  {
		LigneProduction ligneProd = ligneProdService.updateLigneProd( codeLp, robotTraitement, Boolean.parseBoolean(status) ) ;
		return new  ResponseEntity<>(ligneProd,OK) ;
	}
	
	@GetMapping("/list")
		public  ResponseEntity<List<LigneProduction>> getAllLignesProds () {
			List <LigneProduction> lignesProd = ligneProdService.getAllLigneProduction();
			return new  ResponseEntity<>(lignesProd, OK);
		}
	 
	 
	@DeleteMapping("/delete/{codeLp}")
	//@PreAuthorize("hasAnyAuthority('user:delete')")
		public ResponseEntity<HttpResponse> deleteLigneProd (@PathVariable("codeLp") String codeLp) throws IOException{
		ligneProdService.deleteLigneProd(codeLp);
			return response( OK , LIGNE_DELETED_SUCCESSFULY) ; 
		}
	
	
	
	
	private  ResponseEntity<HttpResponse> response (HttpStatus httpStatus , String message ){
		HttpResponse body = new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase()	,message.toUpperCase() )  ;
		return new  ResponseEntity<>( body , httpStatus ) ;
	}

}
