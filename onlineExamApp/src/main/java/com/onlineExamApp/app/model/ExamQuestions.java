package com.onlineExamApp.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class ExamQuestions {
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer queId;
	
	private Integer examId;
	
	private String question;
	
	private String answer1;
	
	private String answer2;
	
	private String answer3;
	
	private String answer4;
	
	private String correct_answer;
		
	
	public Integer getQueId() {
		return queId;
	}

	public void setQueId(Integer queId) {
		this.queId = queId;
	}

	public Integer getExamId() {
		return examId;
	}

	public void setExamId(Integer examId) {
		this.examId = examId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer1() {
		return answer1;
	}

	public void setAnswer1(String answer1) {
		this.answer1 = answer1;
	}

	public String getAnswer2() {
		return answer2;
	}

	public void setAnswer2(String answer2) {
		this.answer2 = answer2;
	}

	public String getAnswer3() {
		return answer3;
	}

	public void setAnswer3(String answer3) {
		this.answer3 = answer3;
	}

	public String getAnswer4() {
		return answer4;
	}

	public void setAnswer4(String answer4) {
		this.answer4 = answer4;
	}

	public String getCorrectAnswer() {
		return correct_answer;
	}

	public void setCorrectAnswer(String correct_answer) {
		this.correct_answer = correct_answer;
	}
	
}
