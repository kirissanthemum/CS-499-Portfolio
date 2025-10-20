/*************************************************
  Name: Kirissa Byington
  Course: CS 320
  Date: 12/1/24
  Desc: Task Service Class
**************************************************/

package service;

import java.util.HashMap;

import model.Task;

public class TaskService {
	int currentIDNumber = 0; //declare and initialize at 0
	
	public static HashMap<String, Task> tasks = new HashMap<String, Task>();
	
	public void addNewTask(String _name, String _description) {
		String stringID = Integer.toString(currentIDNumber);
		Task tempTask = new Task (stringID, _name, _description);
		tasks.put(stringID,  tempTask);
		
		++currentIDNumber; //iterate currentIDNumber
	}
	
	public void deleteTask(String _ID) {
		if(tasks.containsKey(_ID)) {
			tasks.remove(_ID);
		}
	}
	
	public void updateTasks(String _ID, String _newName, String _newDescription) {
		if(tasks.containsKey(_ID)) {
			tasks.get(_ID).setName(_newName);;
			tasks.get(_ID).setDesc(_newDescription);
		}
	}
}
