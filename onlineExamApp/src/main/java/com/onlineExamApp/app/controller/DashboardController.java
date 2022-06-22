package com.onlineExamApp.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.onlineExamApp.app.model.Exam;
import com.onlineExamApp.app.service.ExamService;

@Controller
@RequestMapping("/")
public class DashboardController {
	
	@RequestMapping("")
	public String index(Model model) {
		return "dashboard";
	}
}
