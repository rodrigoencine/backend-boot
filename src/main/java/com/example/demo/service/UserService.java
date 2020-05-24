package com.example.demo.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.security.UserSS;

public class UserService {
	
	public static UserSS authenticated() {
		//retorna usuario logado
		try {
			return  (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
		}
		catch(Exception e) {
			return null;
		}
		
	}

}
