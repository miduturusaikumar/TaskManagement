package com.taskmanagement.assignment.controller;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taskmanagement.assignment.customeException.ControllerException;
import com.taskmanagement.assignment.customeException.ServiceException;
import com.taskmanagement.assignment.entity.TaskEntity;
import com.taskmanagement.assignment.service.TaskServiceImpl;


@RestController
@RequestMapping("/task")
public class TaskController {
        
	@Autowired
	private TaskServiceImpl taskServiceImpl;
	
	
	@PostMapping("/add")
	public ResponseEntity<String> saveTaskDetails(@RequestBody TaskEntity taskEntity) {

		if (taskEntity.getTaskName() == null && taskEntity.getTaskDescription() == null 
				&& taskEntity.getDueDate() == null && taskEntity.getStatus() == null 
				&& taskEntity.getTaskAssign() == null) {

			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Body Should not be Empty.....");


		}
		try {

			taskServiceImpl.saveTask(taskEntity);
			return ResponseEntity.ok("Successfully Added");

		}catch (ServiceException e) {

			ControllerException controllerException=new ControllerException(e.getMessage());	        	
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(controllerException.getMessage());

		}
	}

	@PutMapping("/update/{taskId}")
	public ResponseEntity<String> updateTaskDetails(@PathVariable long taskId, @RequestParam TaskEntity.taskStatus status) {
		try {
			taskServiceImpl.updateTask(taskId, status);
			return ResponseEntity.ok("Task details updated successfully");
		} catch (ServiceException e) {
			ControllerException controllerException=new ControllerException(e.getMessage());	        	
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(controllerException.getMessage());
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllTask() {
		try {
			List<TaskEntity> tasks = taskServiceImpl.getAll();
			return ResponseEntity.ok(tasks);
		} catch (ServiceException e) {
			ControllerException controllerException=new ControllerException(e.getMessage());	        	
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(controllerException.getMessage());
		}
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<?> getTaskById(@PathVariable long id) {
		try {
			TaskEntity task = taskServiceImpl.getById(id);
			return ResponseEntity.ok(task);
		} catch (ServiceException e) {
			ControllerException controllerException=new ControllerException(e.getMessage());	        	
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(controllerException.getMessage());

		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteById(@PathVariable long id) {
		try {
			taskServiceImpl.deleteTaskById(id);
			return ResponseEntity.ok("Deleted Successfully");
		} catch (ServiceException e) {
			ControllerException controllerException=new ControllerException(e.getMessage());	        	
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(controllerException.getMessage());
		}
	}

	@GetMapping("/pagination/{offset}/{pageSize}")
	public ResponseEntity<?> getAllStudents(@PathVariable int offset, @PathVariable int pageSize) {
		try {
			Page<TaskEntity> students = taskServiceImpl.getAllTask(offset, pageSize);
			return ResponseEntity.ok(students);
		} catch (RuntimeException e) {
			ControllerException controllerException=new ControllerException(e.getMessage());	        	
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(controllerException.getMessage());
		}
	}
}
