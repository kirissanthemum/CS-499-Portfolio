/*Kirissa Byington
 * SNHU CS 320
 * 11/17/24
 * Milestone 3
 */

package model;

public class Contact {
	//variables for contact details as specified in milestone requirements
	private String contactID;
	private String firstName;
	private String lastName;
	private String phone;
	private String address;
	
	//validate contactID
	private final boolean validateID(String contactID) {
		if(contactID == null || contactID.length() > 10) {
			return false; //contactID shall not be null and be longer than 10 character
		}
		return true; //validated
	}
	
	//validate firstName
	private final boolean validateFirstName(String firstName) {
		if(firstName == null || firstName.length() > 10) {
			return false; //fisrtName shall not be null or be longer than 10 characters
		}
		return true; //validated
	}
	
	private final boolean validateLastName(String lastName) {
		if(lastName == null || lastName.length() > 10) {
			return false; //lastName shall not be null or longer than 10 characters
		}
		return true; //validated
	}
	
	private final boolean validatePhoneNumber(String phone) {
		if(phone == null || phone.length() != 10) {
			return false; //phone shall not be null or less than 10 characters
		}
		return true; //validated
	}
	
	private final boolean validateAddress(String address) {
		if(address == null || address.length() > 30) {
			return false; //address shall not be null greater than 30 characters
		}
		return true; //validated
	}
	
	//constructor for contact details
	public Contact(String contactID, String firstName, String lastName, String phone, String address) {
		
		//throw exceptions first
		if(!this.validateID(contactID)) {
			throw new IllegalArgumentException("Invalid Contact ID.");
		}
		
		if(!this.validateFirstName(firstName)) {
			throw new IllegalArgumentException("Invalid First Name.");
		}
		
		if(!this.validateLastName(lastName)) {
			throw new IllegalArgumentException("Invalid Last Name.");
		}
		
		if(!this.validatePhoneNumber(phone)) {
			throw new IllegalArgumentException("Invalid Phone Number.");
		}
		
		if(!this.validateAddress(address)) {
			throw new IllegalArgumentException("Invalid Address.");
		}
				
		this.contactID = contactID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.address = address;
	}
	//getter for contact details
	
	//get contact ID
	public String getContactID() {
		return contactID;
	}
	
	//get first name
	public String getFirstName() {
		return firstName;
	}
	
	//set first name
	public void setFirstName(String firstName) {
		if(!this.validateFirstName(firstName)) {
			throw new IllegalArgumentException("Invalid First Name.");
		}
		this.firstName = firstName;
	}
	
	//get last name
	public String getLastName() {
		return lastName;
	}
	
	//set last name
	public void setLastName(String lastName) {
		if(!this.validateLastName(lastName)) {
			throw new IllegalArgumentException("Invalid Last Name.");
		}
		this.lastName = lastName;
	}
		
	//get phone number
	public String getPhone() {
		return phone;
	}
	
	//set phone number
	public void setPhoneNumber(String phone) {
		if(!this.validatePhoneNumber(phone)) {
			throw new IllegalArgumentException("Invalid Phone Number.");
		}
		this.phone = phone;
	}
	
	//get address
	public String getAddress() {
		return address;
	}
	
	//set address
	public void setAddress(String address) {
		if(!this.validateAddress(address)) {
			throw new IllegalArgumentException("Invalid Address.");
		}
		this.address = address;
	}
	
}
