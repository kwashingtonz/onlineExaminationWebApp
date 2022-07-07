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
	@Query(value="UPDATE exam_results SET given_answer = ?4 , result_status = ?5 WHERE user_id = ?1 AND exam_id = ?2 AND que_id = ?3  ", nativeQuery = true)
	@Transactional
	void updateResult(@Param("uid") Integer uid ,@Param("eid") Integer eid,@Param("qid") Integer qid,@Param("gA") String gA,@Param("rS") String rS);

	@Query(value="SELECT given_answer FROM exam_results  WHERE que_id = :qid AND user_id = :uid AND exam_id = :eid",nativeQuery = true)
	String getGivenAnswer(@Param("uid") Integer uid ,@Param("eid") Integer eid,@Param("qid") Integer qid);
	
	@Modifying
	@Query("DELETE FROM ExamResults e where e.examId = ?1")
	void deleteByExamId(@Param("examId")Integer examId);
	
	@Query(value="SELECT COUNT(result_id) FROM exam_results  WHERE  user_id = :uid AND exam_id = :eid AND given_answer IS NOT NULL",nativeQuery = true)
	Integer getCount(@Param("uid") Integer uid ,@Param("eid") Integer eid);
	
	@Query(value="SELECT result_status FROM exam_results  WHERE que_id = :qid AND user_id = :uid AND exam_id = :eid",nativeQuery = true)
	String getResultStatus(@Param("uid") Integer uid ,@Param("eid") Integer eid,@Param("qid") Integer qid);
	
	@Query(value="SELECT COUNT(result_id) FROM exam_results  WHERE  user_id = :uid AND exam_id = :eid AND result_status = 'Correct'",nativeQuery = true)
	Integer countCorrect(@Param("uid") Integer uid ,@Param("eid") Integer eid);
	
}
