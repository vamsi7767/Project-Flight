package com.casestudy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.casestudy.model.AdminModel;
import com.casestudy.model.AuthenticationRequest;
import com.casestudy.model.AuthenticationResponse;
import com.casestudy.repository.AdminRepository;
import com.casestudy.service.JwtUtil;
import com.casestudy.service.UserInfoService;


@RestController
@CrossOrigin(origins="*")
@RequestMapping("/admin")
public class AuthController {
	
	@Autowired
	AdminRepository adminrepo;
	
	@Autowired
	AuthenticationManager authenticationmanager;
	
	@Autowired
	UserInfoService userinfoservice;
	
	@Autowired
	JwtUtil jwtutil;
	
	 @Autowired
	 private PasswordEncoder passwordEncoder;
	
	
 
	
	
    //Rest API to add/Register as Admin details
	@PostMapping("/register")
	private ResponseEntity<AuthenticationResponse> registerClientToken(@RequestBody AuthenticationRequest authrequest){

		AdminModel usermodel =new AdminModel();

		usermodel.setUsername(authrequest.getUsername());
		String encodedPassword = passwordEncoder.encode(authrequest.getPassword());
		
		usermodel.setPassword(encodedPassword);
		
		try {
			adminrepo.save(usermodel);
		}
		catch(Exception e){
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
					("Registration Failed") , HttpStatus.OK);
		}
		
 
		
		return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
				(authrequest.getUsername()+" registered Successfully "), HttpStatus.OK);
	}
	
	//Rest API to authenticate Admin details
	@PostMapping("/authenticate")
	private ResponseEntity<?> authenticateClientToken(@RequestBody AuthenticationRequest authrequest) throws Exception{

		try {
			authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(authrequest.getUsername(), authrequest.getPassword()));
		}
		catch(Exception e) {
			return ResponseEntity.ok(new AuthenticationResponse("no"));
		}
		
		UserDetails userdetails= userinfoservice.loadUserByUsername(authrequest.getUsername());
		
		String jwt = jwtutil.generateToken(userdetails);
		
 
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
	
	
	
	

	

}
