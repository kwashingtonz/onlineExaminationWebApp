package com.onlineExamApp.app.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onlineExamApp.app.model.Exam;

public interface ExamRepository extends JpaRepository<Exam, Integer>{

	
	public static final String lastExamId = "SELECT id FROM Exam ORDER BY id DESC LIMIT 1";
	
	@Query("SELECT i FROM Exam i WHERE i.name LIKE %:name% order by i.id desc")
	List<Exam> listSearched(@Param("name") String name);
	
	@Query("SELECT i FROM Exam i WHERE  i.name LIKE %:name% AND i.addedBy LIKE %:addedBy% order by i.id desc")
	List<Exam> listTeacherSearched(@Param("name") String name ,@Param("addedBy") String addedBy);
	
	@Query(value = lastExamId, nativeQuery = true)
	Integer getLastId();
	
	@Query(value= "SELECT * FROM exam i LEFT JOIN exam_teachers_status e ON i.id = e.exam_id AND i.name LIKE  %:name% WHERE e.publish_status LIKE 'PUBLISHED' OR  e.publish_status LIKE 'ENDED' order by i.id desc", nativeQuery = true)
	List<Exam> listPublishedSearched(@Param("name")String name);
	
	@Query(value="SELECT no_ques FROM exam  WHERE id = :eid",nativeQuery = true)
	Integer getNoQues(@Param("eid") Integer eid);
}
