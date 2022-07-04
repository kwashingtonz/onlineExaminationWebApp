package com.onlineExamApp.app.model;


import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.IdClass;


import com.onlineExamApp.app.config.ExamTeachersStatusId;

@Entity
@IdClass(ExamTeachersStatusId.class)
public class ExamTeachersStatus {

	@Id
	private Integer userId;
	
	@Id
	private Integer examId;
	
	private String publishStatus;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	public String getPublishStatus() {
		return publishStatus;
	}

	public void setPublishStatus(String publishStatus) {
		this.publishStatus = publishStatus;
	}
	
	
	
}
