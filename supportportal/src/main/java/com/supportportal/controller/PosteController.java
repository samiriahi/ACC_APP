package com.supportportal.controller;

import static com.supportportal.constant.PosteConstant.POSTE_REMOVED;
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
import com.supportportal.domain.Poste;
import com.supportportal.exception.ExceptionHandling;
import com.supportportal.exception.domain.LigneProdNotFoundException;
import com.supportportal.exception.domain.PosteExisteException;
import com.supportportal.exception.domain.PosteNotFoundException;
import com.supportportal.service.PosteService;

@RestController
@RequestMapping(path = { "/", "/poste"})
public class PosteController  extends ExceptionHandling {

	private PosteService posteService ;
	
	@Autowired
	public PosteController( PosteService posteService) {
		this.posteService = posteService ;
	}

	
	@GetMapping("/count")
    public ResponseEntity<Long> getTotalPostes() {
        long count = posteService.getTotalPostes();
        return ResponseEntity.ok(count);
    }
	
	@PostMapping("/add")
	public ResponseEntity<Poste> addNewPoste(@RequestParam("nomPoste") String nomPoste,	 @RequestParam("nomReseau") String nomReseau ,
											 @RequestParam("cadence") String cadence ) throws PosteExisteException, PosteNotFoundException   {
		Poste newPoste = posteService.addPoste(nomPoste, nomReseau , cadence) ;
		return new  ResponseEntity<>(newPoste,OK);
	}
	
	
	  @PostMapping("/update")
		public ResponseEntity<Poste> updatePostee( @RequestParam("nomPoste") String nomPoste ,
												   @RequestParam("nomReseau") String nomReseau,
												   @RequestParam("cadence") String cadence  ) throws PosteNotFoundException {
		  Poste updatedPoste = posteService.updatePoste(nomPoste , nomReseau , cadence) ;
			return new  ResponseEntity<>(updatedPoste, OK);
		}
		
	  @GetMapping("/find/{nomPoste}")
		public  ResponseEntity<Poste> getPoste (@PathVariable("nomPoste") String nomPoste) throws PosteNotFoundException {
		  Poste poste = posteService.findPosteByNomPoste(nomPoste);
			return new  ResponseEntity<>(poste, OK);
		}
		
	  @GetMapping("/list")
		public  ResponseEntity<List<Poste>> getAllPostes () {
			List <Poste> postes = posteService.getPostes();
			return new  ResponseEntity<>(postes, OK);
		}
	  
	  @DeleteMapping("/delete/{nomPoste}")
	  	public ResponseEntity<HttpResponse> deleteUser (@PathVariable("nomPoste") String nomPoste) throws IOException{
		  posteService.deletePoste(nomPoste);
			return response(OK ,"Poste has been  deleted Successfully") ; 
		}
	  
	  
	  @PostMapping("/assignPosteToLigneProd")
	  public ResponseEntity<HttpResponse> assignUserToGroup(@RequestParam("nomPoste") String nomPoste, @RequestParam("codeLp") String codeLp ) throws LigneProdNotFoundException, PosteNotFoundException {
		  posteService.assignPosteToLigneProd(codeLp, nomPoste) ;
		  return response (OK , " this " + nomPoste +" has been added to this line Production " + codeLp  );
	  }
	  
	  @GetMapping("/PostesByLigneProd/{codeLp}")
	  public  ResponseEntity<List<Poste>> getPostesByLigneProd (@PathVariable("codeLp") String codeLp) throws  LigneProdNotFoundException {
		List <Poste> postes = posteService.getPostesByLigneProd(codeLp);
		return new  ResponseEntity<>(postes, OK);
		}
	  
	  
	  @DeleteMapping("/removePosteFromLigneProd/{nomPoste}")
		public ResponseEntity<HttpResponse> removePosteFromLigneProd (@PathVariable("nomPoste") String nomPoste) throws IOException{
		  posteService.removePosteFromLigneProd(nomPoste);
	     return response(OK , POSTE_REMOVED) ;
		  }
	
	  
	  private  ResponseEntity<HttpResponse> response (HttpStatus httpStatus , String message ){
			HttpResponse body = new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase()	,message.toUpperCase() )  ;
			return new  ResponseEntity<>( body , httpStatus ) ;
		}
	  
}
