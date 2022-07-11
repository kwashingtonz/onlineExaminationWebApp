package com.onlineExamApp.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineExamApp.app.model.ExamResults;
import com.onlineExamApp.app.model.Users;
import com.onlineExamApp.app.repository.ExamQuestionsRepository;
import com.onlineExamApp.app.repository.ExamRepository;
import com.onlineExamApp.app.repository.ExamResultsRepository;
import com.onlineExamApp.app.repository.UsersRepository;

@Service
@Transactional
public class ExamResultsService {

	@Autowired
	private ExamResultsRepository repo;
	
	@Autowired
	private ExamQuestionsRepository eqrepo;
	
	@Autowired
	private ExamRepository erepo;
	
	@Autowired
	private UsersRepository urepo;
	
	
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
    		if(givenAnswer.equals(""))
    		{
    			givenAnswer = null;
    			rstatus = null;
    		}else {
    			rstatus = "Wrong";
    		}   		
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
    	
    	if(noQues > 0 && doneQues > 0 )
    	{
    		if(doneQues == noQues) {
    			completion = "Completed";
    		}else if(doneQues<noQues){
    			completion = "Partially Completed";
    		}else{
    			completion = "Not Completed";
    		}
    	}else {
    		completion = "Not Completed";
    	}
    		
    	return completion;
    }
    
    public Integer countCompleted(Integer eid) {
    	
    	Integer completedCount=0;
    	
    	List<Users> students = urepo.listStudents();
    	
    	for(int i=0; i<students.size();i++) {
    		Users student = students.get(i);
    		Integer sid = student.getId();
    		
    		Integer noQues = erepo.getNoQues(eid);
        	Integer doneQues = repo.getCount(sid, eid);
        	
        	if(noQues != 0 && doneQues != 0 )
        	{
        		if(doneQues == noQues) {
        			completedCount++;
        		}
        	}
    		
    	}
    	
    	
    	return completedCount;
    }
    
    public String getResultStatus(Integer uid,Integer eid, Integer qid) {
    	
    	String status = repo.getResultStatus(uid, eid, qid);
    	return status;
    }
    
    public String getPoints(Integer uid,Integer eid) {
    	
    	Double points;
    	String grade;
    	
    	Integer noQues = erepo.getNoQues(eid);
    	Integer countCorrect = repo.countCorrect(uid,eid);
    	
    	Double nQ = new Double(noQues);
    	Double cC = new Double(countCorrect);
    	
    	points = ((100/nQ)*cC);
    	
    	if(points>80) {
    		grade = "A";
    	}else if(points>70) {
    		grade = "B";
    	}else if(points>60) {
    		grade = "C";
    	}else if(points>50) {
    		grade = "D";
    	}else {
    		grade = "F";
    	} 		
    	
    	String p = points.toString();
    	
    	return grade+" - "+p+" Points";
    }
    
    public String getPassStatus(Integer uid,Integer eid) {
    	Double points;
    	String pstatus;
    	
    	Integer noQues = erepo.getNoQues(eid);
    	Integer countCorrect = repo.countCorrect(uid,eid);
    	
    	Double nQ = new Double(noQues);
    	Double cC = new Double(countCorrect);
    	
    	points = ((100/nQ)*cC);
    	
    	if(points>50) {
    		pstatus = "Passed";
    	}else {
    		pstatus = "Failed";
    	} 		
    	
    	
    	return pstatus;
    }
    
}
