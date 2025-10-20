/*************************************************
  Name: Kirissa Byington
  Course: CS 320
  Date: 12/1/24
  Desc: AppointmentService Class
**************************************************/

package service;

import java.util.HashMap;

import model.Appointment;

import java.util.Date;

//initiate class
public class AppointmentService {
	
	int currentID = 0; //declare variable
	//hashmap to store data
	public static HashMap<String, Appointment> appts = new HashMap<String, Appointment>();
	
	//add appointment
	public void addNewAppt(Date apptDate, String apptDesc) {
		String stringID = Integer.toString(currentID);
		Appointment tempAppt = new Appointment (stringID, apptDate, apptDesc);
		appts.put(stringID, tempAppt);
		
		++currentID; //iterate IDs
	}
	
	//delete appointment
	public void deleteNewAppt(String newID) {
		if(appts.containsKey(newID)) {
			appts.remove(newID);
		}
	}

}
