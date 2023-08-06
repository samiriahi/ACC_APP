package com.supportportal.service;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import static java.util.concurrent.TimeUnit.MINUTES;

import java.util.concurrent.ExecutionException;

@Service
public class LoginAttemptService {
	
	private static final  int MAXIMUM_NUMBER_OF_ATTEMPTS = 5 ;
	private static final  int ATTEMPTS_INCREMENT = 1 ;
	private LoadingCache<String, Integer> loginAttemptCache ;
	
	

	public LoginAttemptService () {
		super();
		loginAttemptCache =  CacheBuilder.newBuilder().expireAfterWrite(15, MINUTES) // cache expire after 15 minute  
				.maximumSize(100).build(new CacheLoader<String, Integer>() {         // maximum size 100 user 
					public Integer load(String key) {
						return 0 ;
					}
				});
	}
	
	
	public void removeUserFromLoginAttemptCache(String username ) {
		loginAttemptCache.invalidate(username);
	}
	
	public void addUserToLoginAttemptCache(String username)  {
		int attempts = 0 ;
		try {
			attempts = ATTEMPTS_INCREMENT + loginAttemptCache.get(username);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		loginAttemptCache.put(username, attempts);
	}
	
	public Boolean hasExceededMaxAttemps (String username) {
		try {
			return loginAttemptCache.get(username) >= MAXIMUM_NUMBER_OF_ATTEMPTS ;
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return false ;
	}
	
}
