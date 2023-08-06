package com.supportportal.service.impl;

import static com.supportportal.constant.GroupeImplConstant.GROUPENAME_ALREADY_EXISTS;
import static com.supportportal.constant.GroupeImplConstant.GROUPE_NOT_FOUND_BY_NAME;
import static com.supportportal.constant.UserImplConstant.USER_NOT_FOUND;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.supportportal.domain.Groupe;
import com.supportportal.domain.User;
import com.supportportal.exception.domain.GroupeNameExistException;
import com.supportportal.exception.domain.GroupeNameNotFoundException;
import com.supportportal.exception.domain.UserNotFoundException;
import com.supportportal.repository.GroupeRepository ;
import com.supportportal.repository.UserRepository;
import com.supportportal.service.GroupeService;

@Service
public class GroupServiceImpl implements GroupeService{
	
	private GroupeRepository groupeRepository ;
	private UserRepository userRepository ;
	
	@Autowired
	public GroupServiceImpl(GroupeRepository groupeRepository , UserRepository userRepository ) {
		super();
		this.groupeRepository = groupeRepository;
		this.userRepository = userRepository ;
		
	}
	
	@Override
    public List<Groupe> getGroups() {
        return groupeRepository.findAll();
    }

    @Override
    public Groupe findGroupeByNomGroupe(String nomGroupe) {
        return groupeRepository.findGroupeByNomGroupe(nomGroupe) ;
    }
    
    
    
    @Override
	public Groupe addNewGroupe(String nomGroupe , boolean isActive ) throws GroupeNameExistException, GroupeNameNotFoundException {
    	validateNewGroupeName( null  , nomGroupe ) ;
    	Groupe groupe = new Groupe() ;
    	groupe.setNomGroupe(nomGroupe);
		groupe.setDateCreation(new Date());
		groupe.setIsActive(isActive);
		groupeRepository.save(groupe);
		return groupe ;
	}
    
	@Override
   	public Groupe updateGroupe(String currentGroupeName ,String newNomGroupe,  boolean isActive ) throws GroupeNameExistException, GroupeNameNotFoundException {
		Groupe currentGroupe = validateNewGroupeName( currentGroupeName  , newNomGroupe ) ;
    	currentGroupe.setNomGroupe(newNomGroupe);
    	currentGroupe.setIsActive(isActive);
    	groupeRepository.save(currentGroupe);
		return currentGroupe ;    	
    } 
	
	@Override 
	public void deleteGroupe(String nomGroupe ) throws IOException, GroupeNameNotFoundException {
		Groupe groupe = groupeRepository.findGroupeByNomGroupe(nomGroupe) ;
		List<User> users = getGroupMembers(nomGroupe) ;
		for (User user : users){
			user.setGroupe(null);
			groupe.getMembers().remove(users) ;
		}
		groupeRepository.deleteById(groupe.getIdGroupe());
	}
	
	
	
	
	@Override
	public void assignUserToGroupe (String nomGroupe , String username) throws GroupeNameNotFoundException , UserNotFoundException {
		Groupe groupe = groupeRepository.findGroupeByNomGroupe(nomGroupe) ;
		User user = userRepository.findUserByUsername(username) ;
		if (user==null) {
			throw new UserNotFoundException(USER_NOT_FOUND + username ) ;
		}
		if (groupe==null) {
			throw new GroupeNameNotFoundException(GROUPE_NOT_FOUND_BY_NAME + nomGroupe); 
		} else {
			user.setGroupe(groupe);
			groupe.getMembers().add(user);
			userRepository.save(user);
		}
	}
	
	@Override
	public List<User> getGroupMembers(String nomGroupe) throws GroupeNameNotFoundException{
		Groupe groupe = groupeRepository.findGroupeByNomGroupe(nomGroupe) ;
		if (groupe==null){
			throw new GroupeNameNotFoundException(GROUPE_NOT_FOUND_BY_NAME + nomGroupe); 
			}
		else
			{
			return groupe.getMembers(); 
			}
	}
	
	@Override
	public void removeUserFromGroup(String username) {
		User user = userRepository.findUserByUsername(username) ;
		Groupe groupe = user.getGroupe() ;
		groupe.getMembers().remove(user) ;
		user.setGroupe(null);
		userRepository.save(user) ;
	}
	
	
	private Groupe validateNewGroupeName(String currentGroupeName , String newGroupeName ) throws GroupeNameExistException , GroupeNameNotFoundException {
		Groupe groupeByName = findGroupeByNomGroupe(newGroupeName) ;
		if (StringUtils.isNotBlank(currentGroupeName)) {
			Groupe currentGroupe = findGroupeByNomGroupe(currentGroupeName);
			Groupe groupeByNewName = findGroupeByNomGroupe(newGroupeName) ;
			if(currentGroupe== null ) {
				throw new GroupeNameNotFoundException(GROUPE_NOT_FOUND_BY_NAME + currentGroupeName);
			}
			if (groupeByNewName != null && !currentGroupe.getIdGroupe().equals(groupeByNewName.getIdGroupe())) {
				throw new GroupeNameExistException(GROUPENAME_ALREADY_EXISTS) ;
			}
			return currentGroupe ;
		}else {
			if (groupeByName != null ) {
				throw new GroupeNameExistException(GROUPENAME_ALREADY_EXISTS) ;
			}
			return null ;
		}
		
	}

	
}
