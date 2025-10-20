/*Kirissa Byington
 * SNHU CS 320
 * 11/17/24
 * Milestone 3
 */

package service;

import java.util.List;

import model.Contact;

import java.util.ArrayList;

//class for managing the contacts
public class ContactService {
	
	int currentIDNumber = 0; //initialized at 0
	
	//create arraylist for the objects
	public static List<Contact> contactList = new ArrayList<Contact>();
	
	//add contact with a unique ID
	public void addContact(String firstName, String lastName, String phone, String address) {
		String stringID = Integer.toString(currentIDNumber);
		Contact newContact = new Contact(stringID, firstName, lastName, phone, address);
		contactList.add(newContact.getContactID(),newContact);
		
		//Increment ID number
		++currentIDNumber;
		
	}
	
	//delete contact
	public void deleteContact(String ID) {
		int intID = Integer.valueOf(ID);
		
		for(int i = 0; i < ContactService.contactList.size(); i++) {
			if(ContactService.contactList.get(i).getContactID() == intID) {
				contactList.remove(i);
				
			}
		}
	}
	
	//Update first name
	public void updateFirstName(String contactID, String firstName) {
		for(Contact iter : contactList) {
			if(iter.getContactID() == Integer.valueOf(contactID)) {
				iter.setFirstName(firstName);
			}
		}
	}
	
	//Update last name
	public void updateLastName(String contactID, String lastName) {
		for(Contact iter : contactList) {
			if(iter.getContactID() == Integer.valueOf(contactID)) {
				iter.setLastName(lastName);
			}
		}
	}
	
	//Update phone number
	public void updatePhone(String contactId, String phone) {
		for(Contact iter : contactList) {
			if(iter.getContactID() == Integer.valueOf(contactID)) {
				iter.setPhoneNumber(phone);
			}
		}
	}
	
	//Update address
	public void updateAddress(String contactID, String address) {
		for(Contact iter : contactList) {
			if(iter.getContactID() == Integer.valueOf(contactID)) {
				iter.setAddress(address);
			}
		}
	}

}
	
	