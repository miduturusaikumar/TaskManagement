package com.taskmanagement.assignment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hibernate.query.Page;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import com.taskmanagement.assignment.customeException.ControllerException;
import com.taskmanagement.assignment.customeException.ServiceException;
import com.taskmanagement.assignment.entity.TaskEntity;
import com.taskmanagement.assignment.repository.TaskRepo;

public class TaskServiceImplTest {

	@Mock
	private TaskRepo taskRepo;

	@InjectMocks
	private TaskServiceImpl taskService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testSave_Success()
	{
		TaskEntity taskEntity=new TaskEntity();
		taskEntity.setTaskId(1);
		taskEntity.setTaskName("Bug");
		taskEntity.setTaskAssign("ravi");
		String dateString = "2024-03-29";
		Date date = Date.valueOf(dateString);
		taskEntity.setDueDate(date);
		taskEntity.setStatus(TaskEntity.taskStatus.COMPLETED);
		taskEntity.setTaskDescription("seviour");
		Mockito.when(taskRepo.save(taskEntity)).thenReturn(taskEntity);
	    ResponseEntity<String> obj=	taskService.saveTask(taskEntity);
	    assertEquals("Details saved Successfully", obj.getBody());	
	}
	
	@Test
	public void testUpdateTask_Success() {
		long id = 1;
		TaskEntity.taskStatus newStatus = TaskEntity.taskStatus.PENDING;
		TaskEntity existingTask = new TaskEntity();
		existingTask.setTaskId(id);
	    Mockito.when(taskRepo.findById(id)).thenReturn(Optional.of(existingTask));
	    
		taskService.updateTask(id, newStatus);
		assertEquals(newStatus, existingTask.getStatus());
	}
	
	 @Test
	    public void testUpdateTask_TaskNotFound() {
	        long id = 1;
	        TaskEntity.taskStatus newStatus = TaskEntity.taskStatus.PENDING;
	        when(taskRepo.findById(id)).thenReturn(Optional.empty());
	        assertThrows(ServiceException.class, () -> taskService.updateTask(id, newStatus));
	    }

	@Test
	public void testGetById_Success() {
		long id = 1;
		TaskEntity taskEntity = new TaskEntity();
		when(taskRepo.findById(id)).thenReturn(Optional.of(taskEntity));
		assertEquals(taskEntity, taskService.getById(id));
	}
	
	@Test
    public void testGetById_TaskNotFound() {
        long id = 1;
        
        when(taskRepo.findById(id)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> taskService.getById(id));
    }

	@Test
	public void testGetAll_Success() {
		List<TaskEntity> taskList = new ArrayList<>();
		taskList.add(new TaskEntity());
		when(taskRepo.findAll()).thenReturn(taskList);
		assertEquals(taskList, taskService.getAll());
	}
	
	@Test
    void testGetAll_TaskNotFoundException() {
        
       Mockito.when(taskRepo.findAll()).thenReturn(Collections.emptyList());
  
        assertThrows(ServiceException.class, () -> taskService.getAll());
    }


	@Test
	public void testDeleteTaskById_Success() {
		long id = 1;
		TaskEntity taskEntity = new TaskEntity();
		when(taskRepo.findById(id)).thenReturn(Optional.of(taskEntity));
		doNothing().when(taskRepo).deleteById(id);
		taskService.deleteTaskById(id);
	}

	@Test
    public void testDeleteTaskById_TaskNotFound() {
        long id = 1;
        when(taskRepo.findById(id)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> taskService.deleteTaskById(id));
    }
	 
}
