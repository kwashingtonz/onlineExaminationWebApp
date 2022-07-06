package com.onlineExamApp.app.config;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExamStudentsStatusId implements Serializable{

	@SuppressWarnings("unused")
	private Integer userId;
	@SuppressWarnings("unused")
	private Integer examId;
	
	public ExamStudentsStatusId(){
	}
	
	public ExamStudentsStatusId(Integer userId,Integer examId) {
		this.userId = userId;
		this.examId = examId;
	}
}
