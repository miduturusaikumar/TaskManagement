package com.taskmanagement.assignment.repository;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanagement.assignment.entity.TaskEntity;
import com.taskmanagement.assignment.entity.TaskEntity.taskStatus;

public interface TaskRepo extends JpaRepository<TaskEntity, Long> {
	
	public String getBytaskAssign(String Name);

	public Page<TaskEntity> findByDueDateAndStatus(Date dueDate, taskStatus status, Pageable pageable); 

}
