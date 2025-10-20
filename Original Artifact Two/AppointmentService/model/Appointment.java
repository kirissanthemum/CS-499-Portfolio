/*************************************************
  Name: Kirissa Byington
  Course: CS 320
  Date: 12/1/24
  Desc: Appointment Class
**************************************************/

package model;

import java.util.Date;

//initiate class
public class Appointment {
	
	private String newID;
	private Date apptDate;
	private String apptDesc;
	
	//validate ID w/ parameters newID and return boolean
	private final boolean validateID(String newID) {
		if(newID == null || newID.length() > 10) { //newID null or greater than 10 characters return false
			return false;
		}
		return true; //if not null and less than 10 characters return true
	}
	
	//validate appointment date w/ parameters apptDate and return boolean
	private final boolean validateDate(Date apptDate) {
		if(apptDate == null || apptDate.before(new Date())) {//if date is null or in past return false
			return false;
		}
		return true; //else return true
	}
	
	//validate appointment description w/ parameter apptDesc and return boolean
	private final boolean validateDesc(String apptDesc) {
		if(apptDesc == null || apptDesc.length() > 50 || apptDesc.equals("") ) {
			return false; //if desc is null, more than 50 characters, is empty return false
		}
		return true; //else return true
	}
	//throw illegal argument exceptions
	public Appointment(String newID, Date apptDate, String apptDesc) {
		if(!this.validateID(newID)) {
			throw new IllegalArgumentException("Invalid ID");
		}
		
		if(!this.validateDate(apptDate)) {
			throw new IllegalArgumentException("Invalid Date");
		}
		
		if(!this.validateDesc(apptDesc)) {
			throw new IllegalArgumentException("Invalid Description");
		}
		
		//set id, date, and desc
		setNewID(newID);
		setApptDate(apptDate);
		setApptDesc(apptDesc);
	}
	
	//constructors getters/setters
	public int getNewID() {
		return Integer.valueOf(newID);
	}
	
	public void setNewID(String newID) {
		this.newID = newID;
	}
	
	public Date getApptDate() {
		return apptDate;
	}
	
	public void setApptDate(Date apptDate) {
		if(!this.validateDate(apptDate)) {
			throw new IllegalArgumentException("Invalide Date");
		}
		this.apptDate = apptDate;
	}
	
	public String getApptDesc() {
		return apptDesc;
	}
	
	public void setApptDesc(String apptDesc) {
		if(!this.validateDesc(apptDesc)) {
			throw new IllegalArgumentException("Invalid Description");
		}
		this.apptDesc = apptDesc;
	}

}
