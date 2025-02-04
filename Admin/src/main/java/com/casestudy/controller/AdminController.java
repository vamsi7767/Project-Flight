package com.casestudy.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.casestudy.exception.AdminServiceException;
import com.casestudy.model.AdminModel;
import com.casestudy.model.FlightModel;
import com.casestudy.model.UserModel;
import com.casestudy.repository.AdminRepository;
import com.casestudy.repository.FlightRepository;
import com.casestudy.repository.UserRepository;
@CrossOrigin(origins="*")
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	    @Autowired
	    private RestTemplate restTemplate;

		@Autowired
		private AdminRepository adminrepo;
		
		@Autowired
		private FlightRepository flightrepo;
		
		@Autowired
		private UserRepository userrepo;
		
		 
		

		
		@PostMapping("/registeradmin")
 
		public String addadmin(@RequestBody AdminModel admin) {

			adminrepo.save(admin);

	        

			return "Admin with Id: "+admin.getId()+" have been Registered Successfully";
		}
		
		
		
		@GetMapping("/viewadminprofile/{id}")

		public Optional<AdminModel> getadmin(@PathVariable("id") String id){

 
        
			return adminrepo.findById(id);
			
		
		}
		
	
		@PutMapping("/updateprofile/{id}")

		public String updateadmin(@PathVariable("id") String id, @RequestBody AdminModel adminmodel) {
			
			adminrepo.save(adminmodel);

 
	        
			return "Admin with id "+id+" have been updated successfully";
		}
		
		
		
		@DeleteMapping("/deleteadmin/{id}")

		public String deleteadmin(@PathVariable String id) {
			adminrepo.deleteById(id);

 
			return "Admin with id "+id+" have been deleted";
		}
		
		
         
		@GetMapping("/viewallusers")
		public List<UserModel>getallusers() {
			
 
			return Arrays.asList(restTemplate.getForObject("http://UserDetails/user/viewallusers",UserModel[].class));
			
			 
		}
		
		
		
		//viewuser/id
		@GetMapping("/viewuser/{id}")
		public List<UserModel> getuser(@PathVariable("id") String id){
			
 
	 
			return Arrays.asList(restTemplate.getForObject("http://UserDetails/user/viewuser/"+id,UserModel[].class));	
		}
		
		
		
		//updateuserbyid
		@PutMapping("/updateuser/{id}")
		public String updateuser(@RequestBody UserModel usermodel, @PathVariable("id") String id) {
			this.restTemplate.put("http://UserDetails/user/updateprofile/{id}",id,usermodel);
  
			return "User with id : "+id+" have been updated";

		}
		
	
		
		@DeleteMapping("/deleteuser/{id}")
		public String deleteuser(@PathVariable String id) {
			this.restTemplate.delete("http://UserDetails/user/deleteprofile/{id}",id);
          
	        
			return "User with id :"+id+" have been deleted";
		}
		
		
		
//Admin-flight
		
	   
		@PostMapping("/addflight")
		public String addflight(@RequestBody FlightModel flightmodel) {
			this.restTemplate.postForObject("http://FlightDetails/flight/addflight", flightmodel, FlightModel.class);
			
         
	        
			return "flight with No: "+flightmodel.getflightNo()+" have been added Successfully";
		}
		
		
	    
		@GetMapping("/viewallflights")
		public List<FlightModel> getAllflights(){
			
            
	        
			return Arrays.asList(restTemplate.getForObject("http://flightDetails/flight/viewallflights",FlightModel[].class));
		}

		
		
		@GetMapping("/viewallflights/{flightNo}")
		public FlightModel getflights(@PathVariable("flightNo") String flightNo){
			
           
			return restTemplate.getForObject("http://flightDetails/flight/viewflightbyno/"+flightNo,FlightModel.class);	
		}
		
		
		
		@GetMapping("/viewflightbyname/{flightName}")
		public FlightModel getflightsbyname(@PathVariable("flightName") String flightName){

		 
			
			return restTemplate.getForObject("http://flightDetails/flight/viewflightbyname/"+flightName,FlightModel.class);	
		}
		
		
		
		@PutMapping("/updateflight/{flightNo}")
		public String updateflight(@RequestBody FlightModel flightmodel, @PathVariable String flightNo) {
			this.restTemplate.put("http://flightDetails/flight/updateflight/{flightNo}",flightmodel,flightNo,FlightModel.class);
			
           
			
			return "flight with no. : "+flightNo+" have been updated";
		}
		
	
		//Rest API to delete User details by Id
		@DeleteMapping("/deleteflight/{flightNo}")
		public String deleteflight(@PathVariable String flightNo) {
			this.restTemplate.delete("http://flightDetails/flight/deleteflight/"+flightNo,FlightModel.class);
			
            
			return "flight with no. :"+flightNo+" have been deleted";
		}
		

		
}
