package com.onlineExamApp.app.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.onlineExamApp.app.config.ExamStudentsStatusId;

@Entity
@IdClass(ExamStudentsStatusId.class)
public class ExamStudentsStatus {

	@Id
	private Integer userId;

	@Id
	private Integer examId;
	
	private String pendingStatus;
	
	private Integer lastQueId;
	
	private Double finalResult;

	
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

	public String getPendingStatus() {
		return pendingStatus;
	}

	public void setPendingStatus(String pendingStatus) {
		this.pendingStatus = pendingStatus;
	}

	public Integer getLastQueId() {
		return lastQueId;
	}

	public void setLastQueId(Integer lastQueId) {
		this.lastQueId = lastQueId;
	}

	public Double getFinalResult() {
		return finalResult;
	}

	public void setFinalResult(Double finalResult) {
		this.finalResult = finalResult;
	}
	
	
}
