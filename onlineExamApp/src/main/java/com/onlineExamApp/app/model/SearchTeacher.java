package com.onlineExamApp.app.model;

import org.springframework.security.core.context.SecurityContextHolder;

import com.onlineExamApp.app.service.MyUserDetails;

public class SearchTeacher {
	
	private String name = "";
	private Integer id;
	private String addedBy = "";

	
	public SearchTeacher() {
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof MyUserDetails) {
		  addedBy = ((MyUserDetails)principal).getName();   		
		} else {
		 addedBy = principal.toString();
		}
	}
		
	public String getAddedBy() {
		return addedBy;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
