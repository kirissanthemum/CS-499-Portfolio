/*************************************************
  Name: Kirissa Byington
  Course: CS 320
  Date: 12/1/24
  Desc: Task Test Class
**************************************************/

package model;

import static org.junit.Assert.assertEquals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class TaskTest {
	//good constructor
	@DisplayName("Good Constructor")
	@Test
	void goodConstructor() {
		String ID = "1";
		String Name = "Kirissa Lea";
		String Desc = "This is a valid description";
		
		Task tempTask = new Task(ID, Name, Desc);
		assertEquals(1, tempTask.getTaskID());
		assertEquals(Name, tempTask.getName());
		assertEquals(Desc, tempTask.getDesc());
	}
	
	//Invalid constructor w/ too long desc
	@DisplayName("Invalid Constructor")
	@Test
	void invalidConstructor() {
		String ID = "1";
		String Name = "Kirissa Lea";
		String Desc = "This description is way toooooooooooooooooooooooooooooooooooooooooooooooooooooo long and is not a valid description";
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Task(ID, Name, Desc);
			
		});
	}
	
	//invalid constructor w/ null ID
	@DisplayName("Invalid constructor with null ID")
	@Test
	void invalidNullConstructor() {
		String ID = null;
		String Name = "Kirissa Lea";
		String Desc = "Description";
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Task(ID, Name, Desc);
		});
	}
	
	//Invalid Constructor w/ long ID
	@DisplayName("Invalid constructor with too long ID")
	@Test
	void invalidIDConstructor() {
		String ID = "123456789101112131415";
		String Name = "Kirissa Lea";
		String Desc = "Description";
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Task(ID, Name, Desc);
		});
	}
	
	//Test setName w/ valid input
	@DisplayName("Test valid setName")
	@Test
	public void testValidName() {
		String ID = "1";
		String Name = "Kirissa Lea";
		String Desc = "Description";
		
		Task tempTask = new Task(ID, Name, Desc);
		tempTask.setName("Test Name");
		assertEquals("Test Name", tempTask.getName());
	}	
	
	//Test setName w/ null input
	@DisplayName("Test setName with null")
	@Test
	public void testNullName() {
		String ID = "1";
		String Name = "Kirissa Lea";
		String Desc = "Description";
		
		Task tempTask = new Task(ID, Name, Desc);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			tempTask.setName(null);
		});
	}
	
	//test setName w/ too long input
	@DisplayName("Test setName with too long name")
	@Test
	public void testLongName() {
		String ID = "1";
		String Name = "Kirissa Lea";
		String Desc = "Description";
		
		Task tempTask = new Task(ID, Name, Desc);
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			tempTask.setName("Kirissa tooooooooooooooooooooooooooo looooooooooooonnnnnnnnnnnnggggg Lea");
		});
	}
	
	//test setName w/ empty string
	@DisplayName("Test setName with empty input")
	@Test
	public void testEmptyName() {
		String ID = "1";
		String Name = "Kirissa Lea";
		String Desc = "Description";
		
		Task tempTask = new Task(ID, Name, Desc);
				
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			tempTask.setName("");
		});
	}
	
	//test setDesc w/ null
	@DisplayName("Test setDesc with null")
	@Test
	public void testNullDesc() {
		String ID = "1";
		String Name = "Kirissa Lea";
		String Desc = null;
				
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Task(ID, Name, Desc);
		});
	}
	
	//test setDesc w/ empty input
	@DisplayName("Test setDesc with empty description")
	@Test
	public void testEmptyDesc() {
		String ID = "1";
		String Name = "Kirissa Lea";
		String Desc = "";
		
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new Task(ID, Name, Desc);
		});
	}
}
