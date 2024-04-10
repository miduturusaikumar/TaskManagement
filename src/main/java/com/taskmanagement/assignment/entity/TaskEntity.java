package com.taskmanagement.assignment.entity;

import java.sql.Date;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TaskEntity {

	@NotNull
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long taskId;
	
	@NotNull
	@NotBlank
	private String taskName;
	
	@NotNull
	private String taskDescription;
	
	@NotNull
	private String taskAssign;
	
	@NotNull
	@DateTimeFormat
	private Date dueDate;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private taskStatus status; 
	
	public enum taskStatus {
		PENDING, COMPLETED, In_progress;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getTaskAssign() {
		return taskAssign;
	}

	public void setTaskAssign(String taskAssign) {
		this.taskAssign = taskAssign;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public taskStatus getStatus() {
		return status;
	}

	public void setStatus(taskStatus status) {
		this.status = status;
	}
	
	
}
