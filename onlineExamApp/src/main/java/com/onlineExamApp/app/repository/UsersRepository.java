package com.onlineExamApp.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onlineExamApp.app.model.Users;

public interface UsersRepository extends JpaRepository<Users, Integer>{

	@Query("SELECT u FROM Users u WHERE u.email LIKE %:email%")
	Users findByEmail(@Param("email") String email);
	
	@Query("SELECT id FROM Users WHERE name LIKE %:name%")
	public Integer getIdByName(@Param("name") String name);
	
	@Query(value="SELECT COUNT(id) FROM Users u LEFT JOIN users_roles r ON u.id = r.user_id WHERE r.role_id = '2'",nativeQuery=true)
	public Integer getCountStudents();
	
	@Query(value="SELECT u.* FROM Users u LEFT JOIN users_roles r ON u.id = r.user_id WHERE r.role_id = '2'",nativeQuery=true)
	List<Users> listStudents();
	
}
