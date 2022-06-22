package com.onlineExamApp.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.onlineExamApp.app.model.Exam;
import com.onlineExamApp.app.model.Search;
import com.onlineExamApp.app.repository.ExamRepository;

@Service
@Transactional
public class ExamService {
	
	@Autowired
	private ExamRepository repo;
	
	public List<Exam> listAll() {
        return repo.findAll();
    }
     
    public void save(Exam exam) {
        repo.save(exam);
    }
     
    public Exam get(Integer id) {
        return repo.findById(id).get();
    }
     
    public void delete(Integer id) {
        repo.deleteById(id);
    }

	public List<Exam> listSearched(Search search) {
		return repo.listSearched(search.getName());
	}
	

}
