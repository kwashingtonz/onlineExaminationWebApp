package com.onlineExamApp.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineExamApp.app.model.ExamQuestions;
import com.onlineExamApp.app.model.Search;
import com.onlineExamApp.app.repository.ExamQuestionsRepository;

@Service
@Transactional
public class ExamQuestionsService {
	
	@Autowired
	private ExamQuestionsRepository repo;
	
	public List<ExamQuestions> listAll() {
        return repo.findAll();
    }
     
    public void save(ExamQuestions exam_questions) {
        repo.save(exam_questions);
    }
     
    public ExamQuestions get(Integer id) {
        return repo.findById(id).get();
    }
     
    public void delete(Integer id) {
        repo.deleteById(id);
    }
    
	public List<ExamQuestions> listSearched(Search search) {
		return repo.listSearched(search.getId());
	}

}
