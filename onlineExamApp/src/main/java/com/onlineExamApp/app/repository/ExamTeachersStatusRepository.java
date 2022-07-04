package com.onlineExamApp.app.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onlineExamApp.app.model.ExamTeachersStatus;


public interface ExamTeachersStatusRepository extends JpaRepository<ExamTeachersStatus, Integer> {

	@Modifying
	@Query(value="INSERT INTO exam_teachers_status (user_id,exam_id,publish_status) values (:uid,:eid,:status)", nativeQuery = true)
	@Transactional
	void addSave(@Param("uid") Integer uid ,@Param("eid") Integer eid,@Param("status") String status);
	
	 @Modifying
	    @Query("DELETE FROM ExamTeachersStatus e where e.examId = ?1")
	    void deleteByExamId(@Param("examId")Integer examId);
	 
	 @Query("SELECT a.publishStatus FROM ExamTeachersStatus a WHERE a.examId = ?1")
	 public String examStatus(@Param("examId")Integer examId);
	 
	 @Modifying
	 @Query(value="UPDATE exam_teachers_status SET publish_status = ?2 WHERE exam_id = ?1  ", nativeQuery = true)
	 @Transactional
	 void updateSave(@Param("eid") Integer eid,@Param("status") String status);
	
}
