package com.casestudy.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.casestudy.model.BookingModel;
import com.casestudy.model.FlightModel;
import com.casestudy.model.UserModel;
import com.casestudy.repository.UserRepository;


@RestController
@CrossOrigin(origins="*")
@RequestMapping("/user")
public class UserController {

		@Autowired
		private UserRepository userrepo;
		
		@Autowired
		private RestTemplate restTemplate;
		 
		Logger logger = LoggerFactory.getLogger(UserController.class);
		
		
		
//User-CRUD
		
		//Rest API to get User details by Id
		@GetMapping("/viewuserprofile/{id}")
		public Optional<UserModel> getuser(@PathVariable("id") String id){
			
		 
	        
			return userrepo.findById(id);
		}

		
		//Rest API to update User details by Id
		@RequestMapping(value="/updateprofile/{id}", method=RequestMethod.PUT)
		public String update(@PathVariable("id") String id, @RequestBody UserModel usermodel) {
			userrepo.save(usermodel);
			
			 
	        
			return "Updated";
		}
	
		
		//Rest API to delete User profile by Id
		@DeleteMapping("/deleteprofile/{id}")
		public String delete(@PathVariable String id) {
			userrepo.deleteById(id);
			 
	        
			return "User with id "+id+" have been deleted";
		}
		
		
		//Rest API to get all User Details
		@GetMapping("/viewallusers")
		public List<UserModel>getuser(){
			
		 
	        
			return userrepo.findAll();
		}
		
//---------------------------------------User-flight---------------------------------------------
		
		//Rest API to get all flight Details
		@GetMapping("/viewallflights")
		public List<FlightModel> getAllflights(){
			 
	        
			return Arrays.asList(restTemplate.getForObject("http://flightDetails/flight/viewallflights",FlightModel[].class));
		}
		

		//Rest API to get flight Details by Id
		@GetMapping("/viewflight/{flightNo}")
		public FlightModel getflights(@PathVariable("flightNo") String flightNo){
			
			 
	        
			return restTemplate.getForObject("http://flightDetails/flight/viewflightbyno/"+flightNo,FlightModel.class);	
		}
		
		
		//Rest API to get flight details from source to destination
		@GetMapping("/findbw/{flightFrom}/{flightTo}")
		public FlightModel findByloc(@PathVariable("flightFrom") String flightFrom, @PathVariable("flightTo") String flightTo){
			 
	        
			return this.restTemplate.getForObject("http://flightDetails/flight/findbw/"+flightFrom+"/"+flightTo,FlightModel.class);
		}
		
		
		//Rest API to get flight fare by Id
		@GetMapping("/findfareno/{flightNo}")
		public int findfare(@PathVariable("flightNo") String flightNo){
		 
	        
			return restTemplate.getForObject("http://flightDetails/flight/findfarebyno/"+flightNo,Integer.class);
		}
		
		
		//Rest API to get flight fare by Name
		@GetMapping("/findfarename/{flightName}")
		public int findfarebyname(@PathVariable("flightName") String flightName){
			
			 
	        
			return restTemplate.getForObject("http://flightDetails/flight/findfarebyname/"+flightName,Integer.class);
		}
		
		
		//Rest API to get flight time by Id
		@GetMapping("/findtimeno/{flightNo}")
		public String findtime(@PathVariable("flightNo") String flightNo){
			
			 
			return restTemplate.getForObject("http://flightDetails/flight/findtimebyno/"+flightNo,String.class);
		}
		
		
 		

//-----------------------------------User-Ticket-------------------------------------------------
		
		//Rset API to add Booking details 
		@PostMapping("/bookticket")
		public String bookticket(@RequestBody BookingModel book){
			
			//logger implementation
	        logger.info("[bookticket] info message added");
	        logger.debug("[bookticket] debug message added");
	        
			FlightModel flightModel = restTemplate.getForObject("http://flightDetails/flight/viewflightbyno/"+book.getflightNo(),FlightModel.class);
			int fare = flightModel.getFare();
			
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            String username = userDetails.getUsername();
			int seats = restTemplate.postForObject("http://BookingDetails/booking/bookticket/"+username+"/"+fare, book, Integer.class);
//			return seats+"";
			
			if(seats>0)
			{
			restTemplate.postForObject("http://flightDetails/flight/decreaseseat/"+book.getflightNo()+"/"+seats,book, BookingModel.class);
			return "flight have been booked Successfully with seats: "+seats;
			}
			else
			{
				return "Limit Reached";
		    }
			
		}
		
		
		//Rest API to delete Booking by pnrId
		@DeleteMapping("/cancelticket/{pnrId}")
		public String cancelticket(@PathVariable String pnrId){
			
			BookingModel bookingModel = restTemplate.getForObject("http://BookingDetails/booking/getorderpnr/"+pnrId,BookingModel.class);
			FlightModel flightModel = restTemplate.getForObject("http://flightDetails/flight/viewflightbyno/"+bookingModel.getflightNo(),FlightModel.class);
			this.restTemplate.delete("http://BookingDetails/booking/cancelticket/"+pnrId, BookingModel.class);
			restTemplate.postForObject("http://flightDetails/flight/increaseseat/"+flightModel.getflightNo()+"/"+bookingModel.getTotalseats(),bookingModel, BookingModel.class);
			
			//logger implementation
	        logger.info("[cancelticket/pnrId] info message added");
	        logger.debug("[cancelticket/pnrId] debug message added");
	        
			return "flight Ticket with "+pnrId+" cancelled Succesfully";
		}
		
		
		//UNNECESSARY
		//Rest API to get all Bookings
		@GetMapping("/getallorders")
		public List<BookingModel> getAllOrder(){
			
			//logger implementation
	        logger.info("[getallorders] info message added");
	        logger.debug("[getallorders] debug message added");
	        
			return Arrays.asList(restTemplate.getForObject("http://BookingDetails/booking/getallorders",BookingModel[].class));
		}
		
		
		//Rest API to get Bookings by User Id
		@GetMapping("/getorder/{userId}")
		public List<BookingModel> getorder(@PathVariable("userId") String userId){
			
			//logger implementation
	        logger.info("[getorder/userId] info message added");
	        logger.debug("[getorder/userId] debug message added");
	        
			return Arrays.asList(restTemplate.getForObject("http://BookingDetails/booking/getorder/"+userId,BookingModel[].class));
		}
		
		
		//Rest API to update Booking details for User By User Id
		@PutMapping("/updateorder/{userId}")
		public String updateorder(@PathVariable String userId, @RequestBody BookingModel book) {
			restTemplate.put("http://BookingDetails/booking/updateorder/{userId}",userId,book);
			
			//logger implementation
	        logger.info("[updateorder/userId] info message added");
	        logger.debug("[updateorder/userid] debug message added");
	        
			return "Order with id "+userId+" havebeen updated Successfully";
		}
		
		
	}
