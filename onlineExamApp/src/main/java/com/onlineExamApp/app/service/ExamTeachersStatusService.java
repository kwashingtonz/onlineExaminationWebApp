package com.onlineExamApp.app.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineExamApp.app.repository.ExamTeachersStatusRepository;

@Service
@Transactional
public class ExamTeachersStatusService {
	
	@Autowired
	private ExamTeachersStatusRepository repo;
	
	public void addSave(Integer uid, Integer eid)
	{
		String status = "DRAFTED";
		repo.addSave(uid,eid,status);
	}
	
	public void addPublish(Integer uid, Integer eid)
	{
		String status = "PUBLISHED";
		repo.addSave(uid,eid,status);
	}
	
	public void editSave(Integer eid)
	{
		
		String status = repo.examStatus(eid);
		String sendStatus;
		
		if(status.equals("DRAFTED")) {
			sendStatus = "DRAFTED";
		}else {
			sendStatus = "PUBLISHED";
		}	
		repo.updateSave(eid,sendStatus);
	}
	
	public void editPublish(Integer eid)
	{
		String status = "PUBLISHED";
		repo.updateSave(eid,status);
	}
	
	public void finishExam(Integer eid)
	{
		String status = "ENDED";
		repo.updateSave(eid,status);
	}
	
	public void deleteByExamId(Integer examId) {
		repo.deleteByExamId(examId);
	}
	
	public String examStatus(Integer examId) {
		return repo.examStatus(examId);
	}

}
