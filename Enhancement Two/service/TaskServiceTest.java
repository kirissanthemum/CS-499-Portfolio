/*************************************************
  Name: Kirissa Byington
  Course: CS 320
  Date: 12/1/24
  Desc: Task Service Test Class
**************************************************/

package service;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


//create TaskServiceTest class
class TaskServiceTest {
	@AfterEach
	void tearDown() throws Exception { //tearDown perform cleanup after test case ends
		TaskService.tasks.clear();
	}
	
	@DisplayName("Add Task")
	@Test
	void testAddTask() { //declare and initialize variables
		String ID = "0";
		String Name = "Kirissa Lea";
		String Desc = "Description";
		
		TaskService tempTask = new TaskService();
		
		tempTask.addNewTask(Name, Desc);
		
		assertTrue(TaskService.tasks.containsKey(ID));
		assertEquals(Name, TaskService.tasks.get(ID).getName());
		assertEquals(Desc, TaskService.tasks.get(ID).getDesc());
	}
	
	//Test delete contact after adding 3. delete at object ID 1 and check nothing is there anymore
	@DisplayName("Test deleteContact")
	@Test
	void testDeleteContact() {
		String Name = "Kirissa Lea";
		String Desc = "Description";
		
		TaskService tempTask = new TaskService();
		
		assertEquals(0, TaskService.tasks.size());
		
		tempTask.addNewTask(Name, Desc); // #0
		tempTask.addNewTask(Name, Desc); // #1
		tempTask.addNewTask(Name, Desc); // #2
		
		assertEquals(3,TaskService.tasks.size());
		
		tempTask.deleteTask("1");
		
		assertEquals(2,TaskService.tasks.size());
		assertFalse(TaskService.tasks.containsKey("1"));
		
	}
	
	//Create task then update w/ bad ID
	@DisplayName("Test addTask with a bad ID")
	@Test
	void testUpdateTaskBad() {
		String ID = "0";
		String Name = "Kirissa Lea";
		String Desc = "Description";
		
		TaskService tempTask = new TaskService();
		
		tempTask.addNewTask(Name, Desc); //#0
		
		tempTask.updateTasks("1", Name, "Desciption New");
		assertNotEquals("Description New", TaskService.tasks.get(ID).getDesc());
		assertEquals(Name, TaskService.tasks.get(ID).getName());
		
	}
	
	//create task and then update w/ good ID
	@DisplayName("Test addTask with a good ID")
	@Test
	void testUpdateTaskGood() {
		String ID = "0";
		String Name = "Kirissa Lea";
		String Desc = "Description";
		
		TaskService tempTask = new TaskService();
		
		tempTask.addNewTask(Name, Desc); //#0
		
		tempTask.updateTasks("0", Name, "Desciption New");
		assertNotEquals("Description New", TaskService.tasks.get(ID).getDesc());
		assertEquals(Name, TaskService.tasks.get(ID).getName());
		
	}
	
}