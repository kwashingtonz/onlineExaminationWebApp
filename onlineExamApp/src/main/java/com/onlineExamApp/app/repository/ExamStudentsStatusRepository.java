package com.onlineExamApp.app.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onlineExamApp.app.model.ExamStudentsStatus;

public interface ExamStudentsStatusRepository extends JpaRepository<ExamStudentsStatus, Integer> {

	
	@Query("SELECT i FROM ExamStudentsStatus i WHERE i.userId = ?1 AND i.examId = ?2")
	List<ExamStudentsStatus> selectAllStatus(@Param("uid") Integer uid,@Param("eid") Integer eid);
	
	@Query(value="SELECT pending_status FROM exam_students_status  WHERE user_id = :uid AND exam_id = :eid", nativeQuery = true)
	String getStatus(@Param("uid") Integer uid,@Param("eid") Integer eid);
	
	@Modifying
	@Query(value="INSERT INTO exam_students_status (user_id,exam_id,pending_status) values (:uid,:eid,:status)", nativeQuery = true)
	@Transactional
	void insertStatus(@Param("uid") Integer uid ,@Param("eid") Integer eid,@Param("status") String status);
	
	 @Modifying
	 @Query(value="UPDATE exam_students_status SET last_que_id = ?3 WHERE exam_id = ?2 AND user_id = ?1  ", nativeQuery = true)
	 @Transactional
	 void updateLastQueNo(@Param("uid") Integer uid ,@Param("eid") Integer eid,@Param("lqn") Integer lqn);
	 
	 @Query(value="SELECT last_que_id FROM exam_students_status  WHERE user_id = :uid AND exam_id = :eid", nativeQuery = true)
	 Integer getLastQue(@Param("uid") Integer uid,@Param("eid") Integer eid);
	 
	 @Modifying
	 @Query("DELETE FROM ExamStudentsStatus e where e.examId = ?1")
	 void deleteByExamId(@Param("examId")Integer examId);
	
}
