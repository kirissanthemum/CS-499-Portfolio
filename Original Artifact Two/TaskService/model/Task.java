/*************************************************
  Name: Kirissa Byington
  Course: CS 320
  Date: 12/1/24
  Desc: Task Class
**************************************************/

package model;

public class Task {
	private String taskID;
	private String taskName;
	private String taskDesc;
				
	//validate taskID w/ parameters taskID and return boolean
	private final boolean validateID(String taskID) {
		if(taskID == null || taskID.length() > 10) {
			return false;
		}
		return true;
	}
	
	//validate taskName w/ parameters taskName and return boolean
	private final boolean validateName(String taskName) {
		if(taskName == null || taskName.length() > 20 || taskName.equals("")) {
			return false;
		}
		return true;
	}
	
	//validate taskDesc w/ parameters taskDesc and return boolean
	private final boolean validateDesc(String taskDesc) {
		if(taskDesc == null || taskDesc.length() > 50 || taskDesc.equals("")) {
			return false;
		}
		return true;
	}
	//validate and throw exceptions for each parameter
	public Task(String taskID, String taskName, String taskDesc) {
		if(!this.validateID(taskID)) {
			throw new IllegalArgumentException("Invalid ID");
		}
		if(!this.validateName(taskName)) {
			throw new IllegalArgumentException("Invalid Name");
		}
		if(!this.validateDesc(taskDesc)) {
			throw new IllegalArgumentException("Invalid Description");
		}
		setID(taskID);
		setName(taskName);
		setDesc(taskDesc);
	}
	//getters and setters
	public int getTaskID() {
		return Integer.valueOf(taskID);
	}
	
	public void setID(String taskID) {
		this.taskID = taskID;
	}
	
	public String getName() {
		return taskName;
	}
	
	public void setName(String taskName) {
		if(!this.validateName(taskName)) {
			throw new IllegalArgumentException("Invalid Name");
		}
		this.taskName = taskName;
	}
	
	public String getDesc() {
		return taskDesc;
	}
	
	public void setDesc(String taskDesc) {
		if(!this.validateDesc(taskDesc)) {
			throw new IllegalArgumentException("Invalid Description");
		}
		this.taskDesc = taskDesc;
	}
}
