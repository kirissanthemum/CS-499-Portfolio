/*Kirissa Byington
 * SNHU CS 320
 * 11/17/24
 * Milestone 3
 */

package model;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


//test class for ContactTest
class ContactTest{
	
	//test constructor for validity
	@DisplayName("Test Valid Constructor")
	@Test
	public void validConstructor() {
		String contactID = "1";
		String firstName = "Kirissa";
		String lastName = "Byington";
		String phone = "12345678890";
		String address = "123 South St";
		
		Contact testContact = new Contact(contactID, firstName, lastName, phone, address);
		
		assertEquals(1, testContact.getContactID());
		assertEquals(firstName, testContact.getFirstName());
		assertEquals(lastName, testContact.getLastName());
		assertEquals(phone, testContact.getPhone());
		assertEquals(address, testContact.getAddress());
	}
	
	//test constructor for invalid phone number
	@DisplayName("Test Invalid Constructor")
	@Test
	public void invalidConstructor() {
		String contactID = "2";
		String firstName = "Kirissa";
		String lastName = "Byington";
		String phone = "123";
		String address = "123 South St";
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Contact(contactID, firstName, lastName, phone, address);
		});
	}
	
	//test setFirstName method with valid input
	@DisplayName("Test Valid setFirstName")
	@Test
	public void validSetFirstName() {
		String contactID = "3";
		String firstName = "Kirissa";
		String lastName = "Byington";
		String phone = "12345678890";
		String address = "123 South St";
		
		Contact testContact = new Contact(contactID, firstName, lastName, phone, address);
		testContact.setFirstName("Sue");
		assertEquals("Sue", testContact.getFirstName());
	}
	
	//test setFirstName with invalid input(null) and throw exception
	@DisplayName("Test Invalid setFirstName")
	@Test
	public void invalidSetFirstName() {
		String contactID = "3";
		String firstName = "Kirissa";
		String lastName = "Byington";
		String phone = "12345678890";
		String address = "123 South St";
		
		Contact testContact = new Contact(contactID, firstName, lastName, phone, address);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			testContact.setFirstName(null);
		});
	}
	
	//test setFirstName with too long a length and throw exception
	@DisplayName("Test Invalid setFirstName length")
	@Test
	public void testLengthFirstName() {
		String contactID = "3";
		String firstName = "Kirissa";
		String lastName = "Byington";
		String phone = "12345678890";
		String address = "123 South St";
		
		Contact testContact = new Contact(contactID, firstName, lastName, phone, address);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			testContact.setFirstName("Kirissakirissakirissa");
		});
	}
	
	//test setLastName with invalid input(null) and throw exception
	@DisplayName("Test Invalid setLastName")
	@Test
	public void invalidSetLastName() {
		String contactID = "3";
		String firstName = "Kirissa";
		String lastName = "Byington";
		String phone = "12345678890";
		String address = "123 South St";
		
		Contact testContact = new Contact(contactID, firstName, lastName, phone, address);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			testContact.setLastName(null);
		});
	}
	
	//test setLastName with too long a length and throw exception
	@DisplayName("Test Invalid setLastName length")
	@Test
	public void testLengthLastName() {
		String contactID = "3";
		String firstName = "Kirissa";
		String lastName = "Byington";
		String phone = "12345678890";
		String address = "123 South St";
		
		Contact testContact = new Contact(contactID, firstName, lastName, phone, address);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			testContact.setLastName("Byingtonbyingtonbyington");
		});
	}
	
	//test setLastName with valid input
	@DisplayName("Test Valid setLastName")
	@Test
	public void validSetLastName() {
		String contactID = "3";
		String firstName = "Kirissa";
		String lastName = "Byington";
		String phone = "12345678890";
		String address = "123 South St";
		
		Contact testContact = new Contact(contactID, firstName, lastName, phone, address);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			testContact.setLastName("Ellen");
		});
	}
	
	//test setPhone with null input and throw exception
	@DisplayName("Test Invalid setPhoneNumber")
	@Test
	public void invalidSetPhone() {
		String contactID = "3";
		String firstName = "Kirissa";
		String lastName = "Byington";
		String phone = "12345678890";
		String address = "123 South St";
		
		Contact testContact = new Contact(contactID, firstName, lastName, phone, address);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			testContact.setPhoneNumber(null);
		});
	}
	
	//test setPhone with too short and too long a length and through exception
	@DisplayName("Test Invalid setPhoneNumber length")
	@Test
	public void testLengthSetPhone() {
		String contactID = "3";
		String firstName = "Kirissa";
		String lastName = "Byington";
		String phone = "12345678890";
		String address = "123 South St";
		
		Contact testContact = new Contact(contactID, firstName, lastName, phone, address);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			testContact.setPhoneNumber("123");
		});
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			testContact.setPhoneNumber("123123123123123123123123");
		});
	}
	
	//test setPhone with valid length
	@DisplayName("Test Valid setPhoneNumber length")
	@Test
	public void validSetPhone() {
		String contactID = "3";
		String firstName = "Kirissa";
		String lastName = "Byington";
		String phone = "12345678890";
		String address = "123 South St";
		
		Contact testContact = new Contact(contactID, firstName, lastName, phone, address);
		
		testContact.setPhoneNumber("0987654321");
		assertEquals("0987654321", testContact.getPhone());
	}
	
	//test setAddress with invalid input(null) and throw exception
	@DisplayName("Test Invalid setAddress")
	@Test
	public void invalidSetAddress() {
		String contactID = "3";
		String firstName = "Kirissa";
		String lastName = "Byington";
		String phone = "12345678890";
		String address = "123 South St";
		
		Contact testContact = new Contact(contactID, firstName, lastName, phone, address);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			testContact.setAddress(null);
		});
	}
	
	//test setAddress with too long a length and throw exception
	@DisplayName("Test Invalid setAddress length")
	@Test
	public void longSetAddress() {
		String contactID = "3";
		String firstName = "Kirissa";
		String lastName = "Byington";
		String phone = "12345678890";
		String address = "123 South St";
		
		Contact testContact = new Contact(contactID, firstName, lastName, phone, address);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			testContact.setAddress("123 Southsouthsouthsouthsouthsouth St");
		});
	}
	
	//test setAddress with valid input
	@DisplayName("Test Valid setAddress")
	@Test
	public void validSetAddress() {
		String contactID = "3";
		String firstName = "Kirissa";
		String lastName = "Byington";
		String phone = "12345678890";
		String address = "123 South St";
		
		Contact testContact = new Contact(contactID, firstName, lastName, phone, address);
		
		testContact.setAddress("321 North St");
		assertEquals("321 North St", testContact.getAddress());
	}
	
}
