package com.casestudy;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.casestudy.controller.UserController;
import com.casestudy.model.FlightModel;
import com.casestudy.model.UserModel;
import com.casestudy.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.inject.matcher.Matchers;

@SpringBootTest
class UserApplicationTests {
	
	@Mock
    private UserRepository userRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }
	@Test
	void contextLoads() {
	}
//	 @Test
//	    public void testGetUserById() throws Exception {
//	        String userId = "1";
//	        java.util.Optional<UserModel> user = Optional.of(new UserModel());
//	        Mockito.when(userRepository.findById(userId)).thenReturn(user);
//
//	        mockMvc.perform(MockMvcRequestBuilders.get("/user/viewuserprofile/{id}", userId))
//	                .andExpect(MockMvcResultMatchers.status().isOk())
//	                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userId));
//	    }

	    @Test
	    public void testUpdateUserProfile() throws Exception {
	        String userId = "1";
	        UserModel user = new UserModel();
	        user.setId(userId);

	        mockMvc.perform(MockMvcRequestBuilders.put("/user/updateprofile/{id}", userId)
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(new ObjectMapper().writeValueAsString(user)))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.content().string("Updated"));
	    }

	    @Test
	    public void testDeleteUserProfile() throws Exception {
	        String userId = "1";

	        mockMvc.perform(MockMvcRequestBuilders.delete("/user/deleteprofile/{id}", userId))
	                .andExpect(MockMvcResultMatchers.status().isOk())
	                .andExpect(MockMvcResultMatchers.content().string("User with id " + userId + " have been deleted"));
	    }
//
//	    @Test
//	    public void testGetAllUsers() throws Exception {
//	        List<UserModel> users = Arrays.asList(new UserModel(), new UserModel());
//	        Mockito.when(userRepository.findAll()).thenReturn(users);
//
//	        mockMvc.perform(MockMvcRequestBuilders.get("/user/viewallusers"))
//	                .andExpect(MockMvcResultMatchers.status().isOk())
//	                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
//	    }
//
//	    @Test
//	    public void testGetAllFlights() throws Exception {
//	        List<FlightModel> flights = Arrays.asList(new FlightModel(), new FlightModel());
//	        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(FlightModel[].class))).thenReturn(flights);
//
//	        mockMvc.perform(MockMvcRequestBuilders.get("/user/viewallflights"))
//	                .andExpect(MockMvcResultMatchers.status().isOk())
//	                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)));
//	    }
}
