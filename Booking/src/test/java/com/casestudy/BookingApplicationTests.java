package com.casestudy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;

import com.casestudy.controller.BookingController;
import com.casestudy.model.BookingModel;
import com.casestudy.repository.BookingRepository;

@SpringBootTest
class BookingApplicationTests {
	
	
	 @Mock
	    private BookingRepository bookingRepo;
	    
	    @InjectMocks
	    private BookingController bookingController;

	    
	    @Test
		void contextLoads() {
		}
	    @Test
	    public void testBookTicket_ValidBooking_ReturnsTotalSeats() {
	        String userId = "user123";
	        int fare = 100;
	        BookingModel book = new BookingModel();
	        book.setUserId(userId);
	        book.setTotalseats(2);

	        List<BookingModel> ticketslist = new ArrayList<>();
	        when(bookingRepo.findByUserId(userId)).thenReturn(ticketslist);

	        int result = bookingController.bookticket(userId, fare, book);

	        assertEquals(book.getTotalseats(), result);
	        verify(bookingRepo, times(1)).save(book);
	    }

	    @Test
	    public void testGetAllOrders_ReturnsListOfBookings() {
	        List<BookingModel> expectedBookings = new ArrayList<>();
	        expectedBookings.add(new BookingModel(/* booking details */));
	        expectedBookings.add(new BookingModel(/* booking details */));

	        when(bookingRepo.findAll()).thenReturn(expectedBookings);

	        List<BookingModel> result = bookingController.getAllOrders();

	        assertEquals(expectedBookings, result);
	    }
	    
	    @Test
	    public void testGetOrder_InvalidUserId_ReturnsEmptyList() {
	        String userId = "456";

	        when(bookingRepo.findByUserId(userId)).thenReturn(new ArrayList<>());

	        List<BookingModel> result = bookingController.getorder(userId);

	        assertEquals(0, result.size());
	    }

	 

	  

}
