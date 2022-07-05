package com.onlineExamApp.app.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onlineExamApp.app.model.ExamQuestions;

public interface ExamQuestionsRepository extends JpaRepository<ExamQuestions, Integer>{

	@Query("SELECT i FROM ExamQuestions i WHERE i.queId LIKE %:id% order by i.queId")
	List<ExamQuestions> listSearched(@Param("id") Integer id);
	
	@Query("SELECT a FROM ExamQuestions a WHERE a.examId = ?1 order by a.queId")
	List<ExamQuestions> findByExam(@Param("examId") Integer examId);
	
	@Query("SELECT COUNT(q) FROM ExamQuestions q WHERE q.examId = ?1")
	public Integer countQuestions(@Param("examId")Integer examId);
	
	@Modifying
    @Query("DELETE FROM ExamQuestions e where e.examId = ?1")
    void deleteByExamId(@Param("examId")Integer examId);
}
