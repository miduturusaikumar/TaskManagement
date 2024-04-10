package com.taskmanagement.assignment.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.taskmanagement.assignment.entity.TaskEntity;
import com.taskmanagement.assignment.entity.TaskEntity.taskStatus;

public interface TaskServices {
	
	public ResponseEntity<String> saveTask(TaskEntity taskEntity);
	public TaskEntity getById(long id);
	public List<TaskEntity> getAll();
	public void deleteTaskById(long id);
	public ResponseEntity<String> updateTask(long id, taskStatus newStatus);
	Page<TaskEntity> getAllTask(int offset, int pageSize);


}
