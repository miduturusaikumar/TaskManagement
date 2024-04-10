package com.taskmanagement.assignment.service;


import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.taskmanagement.assignment.customeException.ServiceException;
import com.taskmanagement.assignment.entity.TaskEntity;

import com.taskmanagement.assignment.repository.TaskRepo;

@Service
public class TaskServiceImpl implements TaskServices {

	@Autowired
	private TaskRepo taskRepo;
	
	@Override
	public ResponseEntity<String> saveTask(TaskEntity taskEntity) {

		if ( taskRepo.existsById(taskEntity.getTaskId())) {

			throw new ServiceException("Task with ID " + taskEntity.getTaskId() + " already exists");

		}	
		else if(taskEntity.getStatus()==null)		     
		{

			throw new ServiceException("Your status field is null or empty .....");

		}else if(taskEntity.getDueDate()==null) {

			throw new ServiceException("Your Date field is null or empty.....");

		}else if(taskEntity.getTaskAssign()==null) {

			throw new ServiceException("Your TaskAssign field is null or empty.....");

		}else if(taskEntity.getTaskDescription()==null) {

			throw new ServiceException("Your description field is null or empty.....");

		}else if(taskEntity.getTaskName()==null) {

			throw new ServiceException("Your TaskName field is null or empty.....");

		}	
		else if(taskEntity.getTaskAssign().length()==0 ){

			throw new ServiceException("Task assign is not entered");

		}else if(taskEntity.getTaskName().length()==0) {

			throw new ServiceException("task name is not mentioned");

		}
		 if (!isValidStatus(taskEntity.getStatus())) {
	            throw new ServiceException("Invalid status. Please provide a valid status value.");
	        }

		validateDate(taskEntity.getDueDate());

		try {				   

			taskRepo.save(taskEntity);
			return ResponseEntity.ok("Details saved Successfully");
			
		}
		catch(IllegalArgumentException e)
		{
			throw new ServiceException("Not filled any values..");
		}catch(Exception e)
		{
			throw new ServiceException("Something went wrong");
		}	
	}

	
	@Override
	public ResponseEntity<String> updateTask(long id, TaskEntity.taskStatus newStatus) {
		Optional<TaskEntity> optionalTask = taskRepo.findById(id);

		if (optionalTask.isEmpty()) {
			throw new ServiceException(id+" this id is not present to update");

		}  
		try {
			TaskEntity task = optionalTask.get();
			task.setStatus(newStatus);
			taskRepo.save(task);
			return ResponseEntity.ok("Updated SuccessFully");
		} catch(Exception e)
		{
			throw new ServiceException("Something went wrong");
		}	
	}

	
	
	@Override
	public TaskEntity getById(long id) {
		Optional<TaskEntity> optionalTask = taskRepo.findById(id);
	    if (optionalTask.isPresent()) {
	        return optionalTask.get();
	    } else {
	        throw new ServiceException("Id: " + id + " is not present to get Data");
	    }
	}

	@Override
	public List<TaskEntity> getAll() {	
		if(taskRepo.findAll().isEmpty())
		{
			throw new ServiceException("No records found in DataBase to fetch.. ");
		}
		try {

			return taskRepo.findAll();		
		}catch(Exception e)
		{
			throw new ServiceException("Something Went wrong while listing all Records.."+e.getMessage());
		}
	}

	@Override
	public void deleteTaskById(long id) {
		try {
			Optional<TaskEntity> optionalTask = taskRepo.findById(id);
			if(optionalTask.isPresent())
			{
				taskRepo.deleteById(id);
				         			
			}else {
				throw new ServiceException("No records found with the id: "+id);
			}
		}catch(NoSuchElementException e)
		{
			throw new ServiceException("Give id may be Null....");
		}	
	}
	
	@Override
    public Page<TaskEntity> getAllTask(int offset, int pageSize) {
        Page<TaskEntity> task = taskRepo.findAll(PageRequest.of(offset, pageSize));
        if (task.isEmpty()) {
            throw new RuntimeException("No tasks found in the database");
        }
        return task;
    }
 
	private void validateDate(Date dueDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date parsedDate = sdf.parse(dueDate.toString());
            java.util.Date currentDate = new java.util.Date(); // Current date
            if (parsedDate.before(currentDate)) {
                throw new ServiceException("Cannot enter a previous date.");
            }
        } catch (ParseException e) {
            throw new ServiceException("Error parsing or validating date. Please enter a valid date.");
        }
    }
	
	private boolean isValidStatus(TaskEntity.taskStatus status) {
	    return status != null;
	}
}
