package com.onlineExamApp.app.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onlineExamApp.app.model.ExamQuestions;

public interface ExamQuestionsRepository extends JpaRepository<ExamQuestions, Integer>{

	@Query("SELECT i FROM ExamQuestions i WHERE i.queId LIKE %:queId% order by i.queId desc")
	List<ExamQuestions> listSearched(@Param("queId") Integer que_id);

}
