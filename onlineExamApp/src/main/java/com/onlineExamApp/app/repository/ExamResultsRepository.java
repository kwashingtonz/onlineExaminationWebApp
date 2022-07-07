package com.onlineExamApp.app.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onlineExamApp.app.model.ExamResults;

public interface ExamResultsRepository extends JpaRepository<ExamResults, Integer>{
	
	@Query(value="SELECT * FROM exam_results  WHERE que_id = :qid AND user_id = :uid AND exam_id = :eid",nativeQuery = true)
	ExamResults getResult(@Param("uid") Integer uid ,@Param("eid") Integer eid,@Param("qid") Integer qid);

	@Modifying
	@Query(value="INSERT INTO exam_results (user_id,exam_id,que_id) values (:uid,:eid,:qid)", nativeQuery = true)
	@Transactional
	void insertResult(@Param("uid") Integer uid ,@Param("eid") Integer eid,@Param("qid") Integer qid);
	
	@Modifying
	@Query(value="UPDATE exam_results SET given_answer = ?4 WHERE user_id = ?1 AND exam_id = ?2 AND que_id = ?3  ", nativeQuery = true)
	@Transactional
	void updateResult(@Param("uid") Integer uid ,@Param("eid") Integer eid,@Param("qid") Integer qid,@Param("gA") String gA);
}
