package com.onlineExamApp.app.config;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ExamTeachersStatusId implements Serializable {

	@SuppressWarnings("unused")
	private Integer userId;
	@SuppressWarnings("unused")
	private Integer examId;
	
	public ExamTeachersStatusId(Integer userId,Integer examId) {
		this.userId = userId;
		this.examId = examId;
	}
}
