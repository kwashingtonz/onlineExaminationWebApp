package com.onlineExamApp.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.onlineExamApp.app.model.Exam;
import com.onlineExamApp.app.model.Search;
import com.onlineExamApp.app.service.ExamService;
import com.onlineExamApp.app.util.PaginatorHelper;

@Controller
@RequestMapping("/exam")
public class ExamController {
	
	@Autowired
	private ExamService service;

	@RequestMapping(value= "", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(@ModelAttribute(value = "search") Search search,Model model, 
			@PageableDefault(value = PaginatorHelper.DEFAULT_PAGINATION_SIZE, page = 0) Pageable pageable) {
		List<Exam> exams = service.listSearched(search);
		Page<Exam> page=PaginatorHelper.pagiableList(exams, pageable);
		model.addAttribute("exams", exams);
		model.addAttribute("page", page);
		return "exam/list";
	}
	
	@RequestMapping("/add")
	public String add(Model model) {
		Exam exam = new Exam();
		
	    model.addAttribute("exam", exam);	  
		return "exam/add";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("exam") Exam exam) {
	    service.save(exam);
	     
	    return "redirect:/exam";
	}
	
	@RequestMapping("/edit/{id}")
	public String showEditExamPage(Model model,@PathVariable(name = "id") int id) {
	    Exam exam = service.get(id);
	    
	    model.addAttribute("exam", exam);
	    
	    return "exam/add";
	}
	
	@RequestMapping(value="/delete/{id}")
	public String delete(Model model,@PathVariable(name = "id") int id) {
	    service.delete(id);
	    
	    return "redirect:/exam";
	}
	
	@GetMapping("/403")
	public String error403() {
		return "403";
	}
}
