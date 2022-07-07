package com.onlineExamApp.app.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineExamApp.app.model.ExamResults;
import com.onlineExamApp.app.repository.ExamQuestionsRepository;
import com.onlineExamApp.app.repository.ExamRepository;
import com.onlineExamApp.app.repository.ExamResultsRepository;

@Service
@Transactional
public class ExamResultsService {

	@Autowired
	private ExamResultsRepository repo;
	
	@Autowired
	private ExamQuestionsRepository eqrepo;
	
	@Autowired
	private ExamRepository erepo;
	
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
    	String rstatus=null;
    	
    	String cA = eqrepo.getCorrectAnswer(qid, eid);
    	
    	System.out.println(cA);
    	
    	if(givenAnswer.equals(cA)) {
    		rstatus = "Correct";
    	}else {
    		rstatus = "Wrong";
    	}
    	
    	repo.updateResult(uid,eid,qid,givenAnswer,rstatus);
    }
    
    public String getGivenAnswer(Integer uid,Integer eid, Integer qid) {
    	return repo.getGivenAnswer(uid,eid,qid);
    }
    
    public void deleteByExamId(Integer examId) {
    	repo.deleteByExamId(examId);
    }
    
    public String getCompletion(Integer uid,Integer eid) {
    	Integer noQues = erepo.getNoQues(eid);
    	Integer doneQues = repo.getCount(uid, eid);
    	String completion;
    	
    	if(doneQues == noQues) {
    		completion = "Completed";
    	}else {
    		completion = "Not Completed";
    	}
    	return completion;
    }
    
}
