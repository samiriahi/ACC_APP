package com.supportportal.controller;

import static com.supportportal.constant.GroupeImplConstant.GROUPE_DELETED_SUCCESSFULY;
import static com.supportportal.constant.GroupeImplConstant.USER_REMOVED_SUCCESSFULY;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supportportal.domain.Groupe;
import com.supportportal.domain.HttpResponse;
import com.supportportal.domain.User;
import com.supportportal.exception.ExceptionHandling;
import com.supportportal.exception.domain.GroupeNameExistException;
import com.supportportal.exception.domain.GroupeNameNotFoundException;
import com.supportportal.exception.domain.UserNotFoundException;
import com.supportportal.service.GroupeService;

@RestController
@RequestMapping(path = { "/", "/groupe"})
public class GroupeController extends ExceptionHandling {

	private GroupeService groupeService ;
	
	  @Autowired
	    public GroupeController( GroupeService groupeService ) {
	        this.groupeService = groupeService;
	    } 
	  
	  
	  @PostMapping("/add")
		public ResponseEntity<Groupe> addNewGroupe(@RequestParam("nomGroupe") String nomGroupe, @RequestParam("isActive") String isActive  ) throws GroupeNameExistException, GroupeNameNotFoundException  {
		  Groupe newGroupe = groupeService.addNewGroupe(nomGroupe, Boolean.parseBoolean(isActive)) ;
			return new  ResponseEntity<>(newGroupe,OK);
		}
	  
	  @PostMapping("/update")
	  @PreAuthorize("hasAnyAuthority('user:update')")
		public ResponseEntity<Groupe> updateGroupe(@RequestParam("currentGroupeName") String currentGroupeName ,
												   @RequestParam("nomGroupe") String nomGroupe,
												   @RequestParam("isActive") String isActive  ) throws GroupeNameExistException, GroupeNameNotFoundException {
		  Groupe updateGroupe = groupeService.updateGroupe(currentGroupeName , nomGroupe , Boolean.parseBoolean(isActive)) ;
			return new  ResponseEntity<>(updateGroupe, OK);
		}
		
	  @GetMapping("/find/{nomGroupe}")
		public  ResponseEntity<Groupe> getGroupe (@PathVariable("nomGroupe") String nomGroupe) throws GroupeNameNotFoundException {
		  Groupe groupe = groupeService.findGroupeByNomGroupe(nomGroupe);
			return new  ResponseEntity<>(groupe, OK);
		}
		
	  @GetMapping("/list")
		public  ResponseEntity<List<Groupe>> getAllGroupes () {
			List <Groupe> groupes = groupeService.getGroups();
			return new  ResponseEntity<>(groupes, OK);
		}
	  
	  
	  @DeleteMapping("/delete/{nomGroupe}")
	  @PreAuthorize("hasAnyAuthority('user:delete')")
		public ResponseEntity<HttpResponse> deleteGroupe (@PathVariable("nomGroupe") String nomGroupe) throws IOException, GroupeNameNotFoundException{
		  groupeService.deleteGroupe(nomGroupe);
		return response(OK ,GROUPE_DELETED_SUCCESSFULY) ;
	  }
	  
	  @PostMapping("/assignUserToGroup")
	  public ResponseEntity<HttpResponse> assignUserToGroup(@RequestParam("username") String username ,
			  												@RequestParam("nomGroupe") String nomGroupe ) throws GroupeNameNotFoundException, UserNotFoundException{
		  groupeService.assignUserToGroupe(nomGroupe, username) ;
		  return response (OK , username +" has been added to "+ nomGroupe+ " Group" );
	  }
	  
	  @GetMapping("/members/{nomGroupe}")
	  public  ResponseEntity<List<User>> getGroupMembers (@PathVariable("nomGroupe") String nomGroupe) throws GroupeNameNotFoundException {
		List <User> members = groupeService.getGroupMembers(nomGroupe);
		return new  ResponseEntity<>(members, OK);
		}
	  
	  
	  @DeleteMapping("/removeUserFromGroup/{username}")
	  @PreAuthorize("hasAnyAuthority('user:delete')")
		public ResponseEntity<HttpResponse> removeUserFromGroup (@PathVariable("username") String username) throws IOException{
		 groupeService.removeUserFromGroup(username);
	     return response(OK ,USER_REMOVED_SUCCESSFULY) ;
		  }
	  
	  
	  private  ResponseEntity<HttpResponse> response (HttpStatus httpStatus , String message ){
			HttpResponse body = new HttpResponse(httpStatus.value(), httpStatus, httpStatus.getReasonPhrase().toUpperCase()	,message.toUpperCase() )  ;
			return new  ResponseEntity<>( body , httpStatus ) ;
		}
	  
	  
}