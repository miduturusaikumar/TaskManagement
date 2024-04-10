package com.taskmanagement.assignment.controller;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.taskmanagement.assignment.customeException.ControllerException;
import com.taskmanagement.assignment.customeException.ServiceException;
import com.taskmanagement.assignment.entity.TaskEntity;
import com.taskmanagement.assignment.service.TaskServiceImpl;

public class TaskControllerTest {
	
	@Mock
	private TaskServiceImpl taskService;

	@InjectMocks
	private TaskController taskController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

    @Test
    public void test_postSuccess()
    {
    	TaskEntity taskEntity=new TaskEntity();
    	taskEntity.setTaskId(1);
    	taskEntity.setTaskName("Bug fixes");
    	taskEntity.setTaskDescription("bug fixes for core");
    	taskEntity.setTaskAssign("Rajesh");
    	taskEntity.setStatus(TaskEntity.taskStatus.PENDING);
    	String dateString = "2024-03-29";
    	Date date = Date.valueOf(dateString);
    	taskEntity.setDueDate(date);
        Mockito.when(taskService.saveTask(taskEntity)).thenReturn(ResponseEntity.ok("Successfully Added"));
    	ResponseEntity<String> response = taskController.saveTaskDetails(taskEntity);
    	assertEquals(HttpStatus.OK, response.getStatusCode());
    }
  
    @Test
    public void test_postFail() {
        // Create a TaskEntity object with dummy data
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTaskId(1);
        taskEntity.setTaskName("Bug fixes");
        taskEntity.setTaskDescription("bug fixes for core");
        taskEntity.setTaskAssign("Rajesh");
        taskEntity.setStatus(TaskEntity.taskStatus.PENDING);
        String dateString = "2024-03-29";
        Date date = Date.valueOf(dateString);
        taskEntity.setDueDate(date); 
        Mockito.doThrow(ServiceException.class).when(taskService).saveTask(taskEntity); 
        ResponseEntity<String> response = taskController.saveTaskDetails(taskEntity); 
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void test_getAllSuccess() {
        List<TaskEntity> tasks = new ArrayList<>();
        when(taskService.getAll()).thenReturn(tasks);
        ResponseEntity<?> response = taskController.getAllTask();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    public void test_getAllFailure() {
        doThrow(ServiceException.class).when(taskService).getAll();
        ResponseEntity<?> response = taskController.getAllTask();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
       
    }
    
    @Test
    public void test_getTaskByIdSuccess() {
        long id = 1;
        TaskEntity task = new TaskEntity();
        Mockito.when(taskService.getById(id)).thenReturn(task);
        ResponseEntity<?> response = taskController.getTaskById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void test_getTaskByIdFailure() {
        long id = 1;
        doThrow(ServiceException.class).when(taskService).getById(id);
        ResponseEntity<?> response = taskController.getTaskById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    public void test_updateTaskDetailsSuccess() {
        long taskId = 1;
        TaskEntity.taskStatus status = TaskEntity.taskStatus.PENDING;
        Mockito.when(taskService.updateTask(taskId, status)).thenReturn(ResponseEntity.ok("Updated SuccessFully"));
        ResponseEntity<String> response = taskController.updateTaskDetails(taskId, status);
        assertEquals("Task details updated successfully", response.getBody());
    }

    @Test
    public void test_updateTaskDetailsFailure() {
        long taskId = 1;
        TaskEntity.taskStatus status = TaskEntity.taskStatus.PENDING;
        doThrow(ServiceException.class).when(taskService).updateTask(taskId, status);
        ResponseEntity<String> response = taskController.updateTaskDetails(taskId, status);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    public void test_deleteByIdSuccess() {
        long id = 1;
        doNothing().when(taskService).deleteTaskById(id);
        ResponseEntity<String> response = taskController.deleteById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deleted Successfully", response.getBody());
    }

    @Test
    public void test_deleteByIdFailure() {
        long id = 1;
        doThrow(ServiceException.class).when(taskService).deleteTaskById(id);
        ResponseEntity<String> response = taskController.deleteById(id);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

}
