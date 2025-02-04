package com.casestudy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import com.casestudy.controller.AdminController;
import com.casestudy.controller.AuthController;
import com.casestudy.model.AdminModel;
import com.casestudy.repository.AdminRepository;

@SpringBootTest
class AdminApplicationTests {
	
	 @Mock
	    private AdminRepository adminRepo;

	    @InjectMocks
	    private AdminController adminController;
	    	
	    @InjectMocks
	    private AuthController authController;
	    
	    @Test
	    void testAddAdmin_ValidAdmin_ReturnsSuccessMessage() {
	        AdminModel admin = new AdminModel();
	        admin.setId("admin123");
	        admin.setUsername("admin");
	        admin.setPassword("123");
	        // Set other properties of the admin model

	        String expectedMessage = "Admin with Id: " + admin.getId() + " have been Registered Successfully";

	        String result = adminController.addadmin(admin);

	        assertEquals(expectedMessage, result);
	        verify(adminRepo, times(1)).save(admin);
	    }
	
	    @Test
	    void testGetAdmin_ValidId_ReturnsAdminModel() {
	        String id = "admin123";
	        AdminModel adminModel = new AdminModel();
	        adminModel.setId(id);
	        adminModel.setUsername("admin");
	        adminModel.setPassword("123");
	        when(adminRepo.findById(id)).thenReturn(Optional.of(adminModel));

	        Optional<AdminModel> expectedResponse = Optional.of(adminModel);

	        Optional<AdminModel> result = adminController.getadmin(id);

	        assertEquals(expectedResponse, result);
	        Mockito.verify(adminRepo, Mockito.times(1)).findById(id);
	    }
	    
	    @Test
	    void testGetAdmin_InvalidId_ReturnsEmptyOptional() {
	        String id = "admin456";

	        when(adminRepo.findById(id)).thenReturn(Optional.empty());

	        Optional<AdminModel> expectedResponse = Optional.empty();

	        Optional<AdminModel> result = adminController.getadmin(id);

	        assertEquals(expectedResponse, result);
	        Mockito.verify(adminRepo, Mockito.times(1)).findById(id);
	    }
	    

	    @Test
	    void testUpdateAdmin_ValidId_ReturnsSuccessMessage() {
	        String id = "admin123";
	        AdminModel adminModel = new AdminModel();
	        adminModel.setId(id);
	        adminModel.setUsername("admin");

	        when(adminRepo.save(adminModel)).thenReturn(adminModel);

	        String expectedResponse = "Admin with id " + id + " have been updated successfully";

	        String result = adminController.updateadmin(id, adminModel);

	        assertEquals(expectedResponse, result);
	        Mockito.verify(adminRepo, Mockito.times(1)).save(adminModel);
	    }



}
