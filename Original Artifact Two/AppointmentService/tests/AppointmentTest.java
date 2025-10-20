/*************************************************
  Name: Kirissa Byington
  Course: CS 320
  Date: 12/2/24
  Desc: Appointment Test
**************************************************/

package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import java.util.Date;
import java.util.Calendar; //converts Date object and YEAR, MONTH, DAY, HOUR
import org.junit.jupiter.api.Test;


//initiate test class
class AppointmentTest {
	
	//test that the constructors are valid
	@DisplayName("Test good constructor.")
	@Test
	void testAppointment() {
		String ID = "1";
		Calendar cal = Calendar.getInstance();
		String Desc = "This is a valid description";
		
		cal.set(Calendar.MONTH,  04);
		cal.set(Calendar.DATE, 27);
		cal.set(Calendar.YEAR, 2025);
		
		Date validDate = cal.getTime();
		
		Appointment tempAppt = new Appointment(ID, validDate, Desc);
		
		assertEquals(1, tempAppt.getNewID());
		assertEquals(validDate, tempAppt.getApptDate());
		assertEquals(Desc, tempAppt.getApptDesc());
		
	}
	
	//test invalid constructors (null newID)
	@DisplayName("Test null ID")
	@Test
	void testNewIDNull() {
		String ID = null;
		Date date = new Date();
		String Desc = "This is a valid description";
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Appointment(ID, date, Desc);
		});
		
		assertEquals("Invalid ID", exception.getMessage());
	}
	
	//test invalid constructors (ID too long)
	@DisplayName("Test too long ID")
	@Test
	void testIDTooLong() {
		String ID = "123456789101112131415";
		Date date = new Date();
		String Desc = "This is a valid description";
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Appointment(ID, date, Desc);
		});
		
		assertEquals("Invalid ID", exception.getMessage());
	}
	
	//test invalid constructors (past date for appointment)
	@DisplayName("Test Date in past")
	@Test
	void testPastDate() {
		String ID = "1";
		Calendar cal = Calendar.getInstance();
		String Desc = "This is a valid description";
		
		cal.set(Calendar.MONTH, 04);
		cal.set(Calendar.DATE, 27);
		cal.set(Calendar.YEAR, 1994); //my birthday
		
		Date invalidDate = cal.getTime();
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Appointment(ID, invalidDate, Desc);
		});
		
		assertEquals("Invalid Date", exception.getMessage());
	}
	
	//test bad constructors (null date)
	@DisplayName("Test null Date")
	@Test
	void testNullDate() {
		String ID = "1";
		Date invalidDate = null;
		String Desc = "This is a valid description";
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.MONTH, 04);
		cal.set(Calendar.DATE, 27);
		cal.set(Calendar.YEAR, 2025);
		
		Date validDate = cal.getTime();
		
		Appointment tempAppt = new Appointment(ID, validDate, Desc);
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Appointment(ID, validDate, Desc);
		});
		
		assertEquals("Invalid Date", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () -> {
			tempAppt.setApptDate(null);
		});
		
		assertEquals("Invalid Date", exception.getMessage());
	}
	
	//test bad constructors (description too long)
	@DisplayName("Test too long Description")
	@Test
	void testDescTooLong() {
		String ID = "1";
		Calendar cal = Calendar.getInstance();
		String Desc = "This is an invalid description because it is way too longgggggggggggggggggggggggggggggggggggggggggggggggggggggggggg";
		
		cal.set(Calendar.MONTH, 04);
		cal.set(Calendar.DATE, 27);
		cal.set(Calendar.YEAR, 2025); 
		
		Date validDate = cal.getTime();
		
		Appointment tempAppt = new Appointment(ID, validDate, "TEST");
				
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Appointment(ID, validDate, Desc);
		});
		
		assertEquals("Invalid Description", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () -> {
			tempAppt.setApptDesc(Desc);
		});
		
		assertEquals("Invalid Description", exception.getMessage());
	}
	
	//test bad constructors (empty desc)
	@DisplayName("Test empty Description")
	@Test
	void testEmptyDesc() {
		String ID = "1";
		Calendar cal = Calendar.getInstance();
		String Desc = "";
		
		cal.set(Calendar.MONTH, 04);
		cal.set(Calendar.DATE, 27);
		cal.set(Calendar.YEAR, 2025); 
		
		Date validDate = cal.getTime();
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Appointment(ID, validDate, Desc);
		});
		
		assertEquals("Invalid Description", exception.getMessage());
	}
	
	//test bad constructors (null description)
	@DisplayName("Test null Descripton")
	@Test
	void tesetNullDesc() {
		String ID = "1";
		Calendar cal = Calendar.getInstance();
		String Desc = null;
		
		cal.set(Calendar.MONTH, 04);
		cal.set(Calendar.DATE, 27);
		cal.set(Calendar.YEAR, 2025); 
		
		Date validDate = cal.getTime();
		
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			new Appointment(ID, validDate, Desc);
		});
		
		assertEquals("Invalid Description", exception.getMessage());
	}


}
