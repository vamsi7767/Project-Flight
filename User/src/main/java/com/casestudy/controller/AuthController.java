package com.casestudy.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.casestudy.model.AuthenticationRequest;
import com.casestudy.model.AuthenticationResponse;
import com.casestudy.model.UserModel;
import com.casestudy.repository.UserRepository;
import com.casestudy.service.JwtUtil;
import com.casestudy.service.UserInfoService;


@RestController
@CrossOrigin(origins="*")
@RequestMapping("/user")
public class AuthController {
	
	@Autowired
	UserRepository userrepo;
	
	@Autowired
	AuthenticationManager authenticationmanager;
	
	@Autowired
	UserInfoService userinfoservice;
	
	@Autowired
	JwtUtil jwtutil;
	
	@Autowired
	RestTemplate restTemplate;
	
	 @Autowired
	    private PasswordEncoder passwordEncoder;
	
	
	Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	
	
	//Rest API to Register as User 
	@PostMapping("/register")
	private ResponseEntity<AuthenticationResponse>registerClientToken(@RequestBody AuthenticationRequest authrequest){
		
		//logger implementation
        logger.info("[register] info message added");
        logger.debug("[register] debug message added");
        
		UserModel usermodel =new UserModel();
		usermodel.setUsername(authrequest.getUsername());
		  String encodedPassword = passwordEncoder.encode(authrequest.getPassword());//
		//usermodel.setPassword(authrequest.getPassword());
		  usermodel.setPassword(encodedPassword);
		  
		List<UserModel> usermodellist = Arrays.asList(restTemplate.getForObject("http://UserDetails/user/viewallusers",UserModel[].class));
		System.out.println(usermodellist);
		System.out.println(usermodel.getUsername());
		int count = 0;
		
		for(int i=0;i<usermodellist.size();i++)
		{
			System.out.println(usermodellist.get(i).getUsername());
			if(usermodel.getUsername().equals(usermodellist.get(i).getUsername())) {
				count++;
				break;
			}
		}
		
		 if (count==0) {
			    userrepo.save(usermodel);
		        return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
				   (authrequest.getUsername()+" registered Successfully ","1"), HttpStatus.OK);
	     }
	     else {
				System.out.println("Invalid Credentials");
				return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse
					("Registration Failed","0") , HttpStatus.OK);
		 }
    }
     
	
	//Rest API to Authenticate as User
	@PostMapping("/authenticate")
	private ResponseEntity<?> authenticateClientToken(@RequestBody AuthenticationRequest authrequest) throws Exception{

		try {
			authenticationmanager.authenticate(new UsernamePasswordAuthenticationToken(authrequest.getUsername(), authrequest.getPassword()));
		}
		
		catch(Exception e) {
			String success = "false";
			return ResponseEntity.ok(new AuthenticationResponse("no",success));
		}
		
		UserDetails userdetails= userinfoservice.loadUserByUsername(authrequest.getUsername());
		
		String jwt = jwtutil.generateToken(userdetails);
		String success = "true";
		
		//logger implementation
        logger.info("[authenticate] info message added");
        logger.debug("[authenticate] debug message added");
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt ,success));
	}
	
	

}
