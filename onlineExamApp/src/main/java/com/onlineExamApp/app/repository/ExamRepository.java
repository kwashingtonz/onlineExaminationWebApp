package com.onlineExamApp.app.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onlineExamApp.app.model.Exam;

public interface ExamRepository extends JpaRepository<Exam, Integer>{

	@Query("SELECT i FROM Exam i WHERE i.name LIKE %:name% order by i.id desc")
	List<Exam> listSearched(@Param("name") String name);

}
