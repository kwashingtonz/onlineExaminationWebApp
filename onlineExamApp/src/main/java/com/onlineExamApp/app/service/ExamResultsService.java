package com.onlineExamApp.app.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineExamApp.app.model.ExamResults;
import com.onlineExamApp.app.repository.ExamResultsRepository;

@Service
@Transactional
public class ExamResultsService {

	@Autowired
	private ExamResultsRepository repo;
	
	public void save(ExamResults exam) {
        repo.save(exam);
    }
     
    public ExamResults get(Integer id) {
        return repo.findById(id).get();
    }
     
    public void delete(Integer id) {
        repo.deleteById(id);
    }
    
    public ExamResults getResult(Integer uid,Integer eid, Integer qid) {
        return repo.getResult(uid, eid, qid);
    }
    
    public void insertResult(Integer uid,Integer eid, Integer qid) {
    	repo.insertResult(uid,eid,qid);
    }
    
    public void updateResult(Integer uid,Integer eid, Integer qid,String givenAnswer) {
    	repo.updateResult(uid,eid,qid,givenAnswer);
    }
}
