/*Kirissa Byington
 * SNHU CS 320
 * 11/17/24
 * Milestone 3
 */

package service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


//test class for ContactService
class ContactServiceTest {
	
	//clear list after each test
	@AfterEach
	void tearDown() throws Exception {
		ContactService.contactList.clear();
	}
	
	//calls AddContact method, validates fisrt name, last name, phone, and address, validate list isn't empy
	
	@DisplayName("Test addContact")
	@Test
	void testeAddContact() {
		String firstName = "Kirissa";
		String lastName = "Byington";
		String phone = "1234567890";
		String address = "123 South St";
		
		ContactService test = new ContactService();
		
		assertTrue(ContactService.contactList.isEmpty());
		
		test.addContact(firstName, lastName, phone, address);
		
		assertFalse(ContactService.contactList.isEmpty());
		assertEquals(0, ContactService.contactList.get(0).getContactID());
		assertEquals(firstName, ContactService.contactList.get(0).getFirstName());
		assertEquals(lastName, ContactService.contactList.get(0).getLastName());
		assertEquals(phone, ContactService.contactList.get(0).getPhone());
		assertEquals(address, ContactService.contactList.get(0).getAddress());
		
	}
	
	//add contacts using addContact and then delete the contact at index 1 and validate delete contact worked
	
	@DisplayName("Test deleteContact")
	@Test
	void testDeleteContact() {
		String firstName = "Kirissa";
		String lastName = "Byington";
		String phone = "1234567890";
		String address = "123 South St";
		boolean testBool = false;
		
		ContactService test = new ContactService();
		
		assertTrue(ContactService.contactList.isEmpty());
		
		//add 3 new contact for testing
		test.addContact(firstName, lastName, phone, address);
		test.addContact(firstName, lastName, phone, address);
		test.addContact(firstName, lastName, phone, address);
		
		assertEquals(3,ContactService.contactList.size());
		
		test.deleteContact("1"); //delete contact at index 1 in list
		
		assertEquals(2,ContactService.contactList.size());
		
		//validate 1 was deleted successfully by looking for ID 1
		for(int i = 0; i < ContactService.contactList.size(); i++) {
			if(ContactService.contactList.get(i).getContactID() == 1) {
				testBool = true;
			}
		}
		assertFalse(testBool);
		
	}
	
	//create contact and validate phone number
	@DisplayName("Test updating phone number")
	@Test
	void testUpdatePhone() {
		String firstName = "Kirissa";
		String lastName = "Byington";
		String phone = "1234567890";
		String address = "123 South St";
		
		ContactService test = new ContactService();
		test.addContact(firstName, lastName, phone, address);
		
		assertEquals(phone, ContactService.contactList.get(0).getPhone());
		
		test.updatePhone("0", "0987654321");
		
		assertEquals("0987654321", ContactService.contactList.get(0).getPhone());
	}
	
	//create contact and validate first name
	@DisplayName("Test updating first name")
	@Test
	void testUpdateFirst() {
		String firstName = "Kirissa";
		String lastName = "Byington";
		String phone = "1234567890";
		String address = "123 South St";
		
		ContactService test = new ContactService();
		test.addContact(firstName, lastName, phone, address);
		
		assertEquals(firstName, ContactService.contactList.get(0).getFirstName());
		
		test.updateFirstName("0", "Bob");
		
		assertEquals("Bob", ContactService.contactList.get(0).getFirstName());
	}
	
	//add contact and validate last name
	@DisplayName("Test updating last name")
	@Test
	void testUpdateLast() {
		String firstName = "Kirissa";
		String lastName = "Byington";
		String phone = "1234567890";
		String address = "123 South St";
		
		ContactService test = new ContactService();
		test.addContact(firstName, lastName, phone, address);
		
		assertEquals(lastName, ContactService.contactList.get(0).getLastName());
		
		test.updateLastName("0", "Evans");
		
		assertEquals("Evans", ContactService.contactList.get(0).getLastName());
	}
	
	//create contact and validate address
	@DisplayName("Test updating address")
	@Test
	void testUpdateAddress() {
		String firstName = "Kirissa";
		String lastName = "Byington";
		String phone = "1234567890";
		String address = "123 South St";
		
		ContactService test = new ContactService();
		test.addContact(firstName, lastName, phone, address);
		
		assertEquals(address, ContactService.contactList.get(0).getAddress());
		
		test.updateAddress("0", "321 North St");
		
		assertEquals("321 North St", ContactService.contactList.get(0).getAddress());
	}
}