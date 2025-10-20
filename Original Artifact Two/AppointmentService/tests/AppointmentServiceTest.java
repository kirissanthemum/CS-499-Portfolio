/*************************************************
  Name: Kirissa Byington
  Course: CS 320
  Date: 12/3/24
  Desc: AppointmentService Test
**************************************************/

package service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;


//initiate test class
class AppointmentServiceTest {
	@AfterEach
	void tearDown() throws Exception {
		AppointmentService.appts.clear(); //tearDown perform cleanup after test case ends
	}
	
	//test addNewAppt
	@DisplayName("Test add an Appointment")
	@Test
	void testAddNewAppt() {
		String ID = "0";
		String Desc = "This is a valid description";
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.MONTH, 04);
		cal.set(Calendar.DATE, 27);
		cal.set(Calendar.YEAR, 2025);
		
		Date validDate = cal.getTime();
		
		AppointmentService tempAppt = new AppointmentService();
		
		assertEquals(0, AppointmentService.appts.size());
		
		tempAppt.addNewAppt(validDate, Desc);
		
		assertTrue(AppointmentService.appts.containsKey(ID));
		assertEquals(validDate, AppointmentService.appts.get(ID).getApptDate());
		assertEquals(Desc, AppointmentService.appts.get(ID).getApptDesc());
	}
	
	
	//test deleteNewAppt
	@DisplayName("Test delete an Appointment")
	@Test
	void testDeleteNewAppt() {
		String ID = "0";
		String Desc = "This is a valid description";
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.MONTH, 04);
		cal.set(Calendar.DATE, 27);
		cal.set(Calendar.YEAR, 2025);
		
		Date validDate = cal.getTime();
		
		AppointmentService tempAppt = new AppointmentService();
		
		assertEquals(0, AppointmentService.appts.size());
		
		tempAppt.addNewAppt(validDate, Desc); //#0
		tempAppt.addNewAppt(validDate, Desc); //#1
		tempAppt.addNewAppt(validDate, Desc); //#2
		
		assertEquals(3, AppointmentService.appts.size());
		
		tempAppt.deleteNewAppt("1");
		
		//confirm delete
		assertEquals(2, AppointmentService.appts.size());
		assertFalse(AppointmentService.appts.containsKey("1"));
		
		tempAppt.deleteNewAppt("1");
		assertEquals(2, AppointmentService.appts.size());
		
	}
	
	//test add appt w/ null desc
	@DisplayName("Test add an appointment with null description")
	@Test
	void testAddNewApptNull() {
		String ID = "0";
		String Desc = null; //null desc
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.MONTH, 04);
		cal.set(Calendar.DATE, 27);
		cal.set(Calendar.YEAR, 2025);
		
		Date validDate = cal.getTime();
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			AppointmentService tempAppt = new AppointmentService();
			tempAppt.addNewAppt(validDate, Desc);
		});
		
		assertEquals("Invalid Description", exception.getMessage());
		
	}
	
	//test add appt w/ empty desc
	@DisplayName("Test add an appointment with an empty description")
	@Test
	void testAddNewApptEmpty() {
		String ID = "0";
		String Desc = ""; //empty desc
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.MONTH, 04);
		cal.set(Calendar.DATE, 27);
		cal.set(Calendar.YEAR, 2025);
		
		Date validDate = cal.getTime();
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			AppointmentService tempAppt = new AppointmentService();
			tempAppt.addNewAppt(validDate, Desc);
		});
		
		assertEquals("Invalid Description", exception.getMessage());
		
	}

}
