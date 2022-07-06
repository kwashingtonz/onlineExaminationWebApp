package com.onlineExamApp.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineExamApp.app.model.ExamStudentsStatus;
import com.onlineExamApp.app.repository.ExamStudentsStatusRepository;

@Service
@Transactional
public class ExamStudentsStatusService {
	
	@Autowired
	private ExamStudentsStatusRepository repo;
	
	public void insertStatus(Integer userId,Integer examId) {
		
		List<ExamStudentsStatus> selectList = repo.selectAllStatus(userId, examId);
		
		if(selectList.isEmpty())
		{
			String status = "ENROLLED";
			repo.insertStatus(userId,examId,status);
		}
		
    }
	
	public String getStatus(Integer userId,Integer examId) {
		String status = repo.getStatus(userId,examId);
		
		return status;
	}
	
	public void updateLastQueNo(Integer userId, Integer examId, Integer lqn) {
		repo.updateLastQueNo(userId, examId, lqn+1);
	}
	
	public Integer getLastQueNo(Integer userId,Integer examId) {
		return repo.getLastQue(userId, examId)-1;
	}
	
	public void deleteByExamId(Integer examId) {
		repo.deleteByExamId(examId);
	}
	
}
