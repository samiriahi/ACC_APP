package com.supportportal.service;

import java.io.IOException;
import java.util.List;

import com.supportportal.domain.Groupe;
import com.supportportal.domain.User;
import com.supportportal.exception.domain.GroupeNameExistException;
import com.supportportal.exception.domain.GroupeNameNotFoundException;
import com.supportportal.exception.domain.UserNotFoundException;

public interface GroupeService {

	public Groupe findGroupeByNomGroupe(String nomGroupe) throws GroupeNameNotFoundException;

	public List<Groupe> getGroups();
	
	public Groupe addNewGroupe(String nomGroupe, boolean isActive ) throws GroupeNameExistException, GroupeNameNotFoundException ;

	public Groupe updateGroupe( String currentGroupeName, String newNomGroupe, boolean isActive)  throws GroupeNameExistException, GroupeNameNotFoundException ;
	
	public void deleteGroupe(String nomGroupe ) throws IOException, GroupeNameNotFoundException ;

	public void assignUserToGroupe(String nomGroupe, String username) throws GroupeNameNotFoundException , UserNotFoundException;

	public List<User> getGroupMembers(String nomGroupe) throws GroupeNameNotFoundException;
	
	public void removeUserFromGroup(String username) ;
 
}
