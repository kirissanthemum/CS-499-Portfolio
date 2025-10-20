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
public class AppointmentTest {
	
	//test that the constructors are valid
	@DisplayName("Test good constructor.")
	@Test
	void testAppointment() {
	    String ID = "1";
	    String Desc = "This is a valid description";

	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DAY_OF_MONTH, 1); // tomorrow
	    Date validDate = cal.getTime();

	    Appointment tempAppt = new Appointment(ID, validDate, Desc);

	    assertEquals("1", tempAppt.getNewID());
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
	    String Desc = "This is a valid description";

	    Appointment tempAppt = new Appointment(ID, new Date(System.currentTimeMillis() + 86400000), Desc); // tomorrow

	    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
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
	@DisplayName("Test null Description")
	@Test
	void testNullDesc() {
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
	
	//ENHANCEMENTS BELOW
	//test setApptDate and setApptDesc as planned in enhancement plan. Both with valid and invalid values.
	@DisplayName("Test setApptDate with valid and invalid values")
	@Test
	void testSetApptDate() {
	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DAY_OF_MONTH, 1); // tomorrow
	    Date validDate = cal.getTime();

	    Appointment appt = new Appointment("1", validDate, "Initial description");

	    // Valid update
	    cal.add(Calendar.DAY_OF_MONTH, 2); // two days from now
	    Date newValidDate = cal.getTime();
	    appt.setApptDate(newValidDate);
	    assertEquals(newValidDate, appt.getApptDate());

	    // Invalid update - NULL
	    IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> {
	        appt.setApptDate(null);
	    });
	    assertEquals("Invalid Date", ex1.getMessage());

	    // Invalid update with past date
	    cal.set(Calendar.YEAR, 2000);
	    Date pastDate = cal.getTime();
	    IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> {
	        appt.setApptDate(pastDate);
	    });
	    assertEquals("Invalid Date", ex2.getMessage());
	}
	
	@DisplayName("Test setApptDesc with valid and invalid values")
	@Test
	void testSetApptDesc() {
	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DAY_OF_MONTH, 1);
	    Date validDate = cal.getTime();

	    Appointment appt = new Appointment("1", validDate, "Initial description");

	    // Valid update
	    String newDesc = "Updated description";
	    appt.setApptDesc(newDesc);
	    assertEquals(newDesc, appt.getApptDesc());

	    // Invalid update - NULL
	    IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> {
	        appt.setApptDesc(null);
	    });
	    assertEquals("Invalid Description", ex1.getMessage());

	    // Invalid update with empty string
	    IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> {
	        appt.setApptDesc("");
	    });
	    assertEquals("Invalid Description", ex2.getMessage());

	    // Invalid update with too long description
	    String longDesc = "This description is wayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy tooooooooooooooooooo longgggggggggg.";
	    IllegalArgumentException ex3 = assertThrows(IllegalArgumentException.class, () -> {
	        appt.setApptDesc(longDesc);
	    });
	    assertEquals("Invalid Description", ex3.getMessage());
	}
	
	//description boundary test
	@DisplayName("Test character boundary for description")
	@Test
	void testDescriptionBoundary() {
	    String ID = "1";
	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DAY_OF_MONTH, 1);
	    Date validDate = cal.getTime();

	    // Description at 50 characters should pass
	    String desc50 = "This description is wayyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy tooooooooooooooo longgggggggggggg";
	    Appointment appt = new Appointment(ID, validDate, desc50);
	    assertEquals(desc50, appt.getApptDesc());

	    // more than 50 characters should fail
	    String desc51 = desc50 + "X";
	    IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
	        appt.setApptDesc(desc51);
	    });
	    assertEquals("Invalid Description", ex.getMessage());
	}
	
	//test null appointment handling
	@DisplayName("Test constructor with nulls")
	@Test
	void testConstructorNulls() {
	    Calendar cal = Calendar.getInstance();
	    cal.add(Calendar.DAY_OF_MONTH, 1);
	    Date validDate = cal.getTime();
	    String validDesc = "Valid description";

	    // Null ID Test
	    assertThrows(IllegalArgumentException.class, () -> {
	        new Appointment(null, validDate, validDesc);
	    });

	    // Null Date Test
	    assertThrows(IllegalArgumentException.class, () -> {
	        new Appointment("1", null, validDesc);
	    });

	    // Null Description Test
	    assertThrows(IllegalArgumentException.class, () -> {
	        new Appointment("1", validDate, null);
	    });
	}




}
