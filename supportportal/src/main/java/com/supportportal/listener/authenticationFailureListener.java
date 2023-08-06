package com.supportportal.listener;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.supportportal.service.LoginAttemptService;


@Component
public class authenticationFailureListener {
	
	private LoginAttemptService loginAttemptService ;
	
	@Autowired
	public authenticationFailureListener(LoginAttemptService loginAttemptService) {
		this.loginAttemptService = loginAttemptService;
	}
	
	@EventListener
	public void onauthentificationFailure(AuthenticationFailureBadCredentialsEvent event ) throws ExecutionException {
		Object principal = event.getAuthentication().getPrincipal();
		if (principal instanceof String) {
			String username = (String ) event.getAuthentication().getPrincipal();
			loginAttemptService.addUserToLoginAttemptCache(username);
		}
	}
	
	
}
