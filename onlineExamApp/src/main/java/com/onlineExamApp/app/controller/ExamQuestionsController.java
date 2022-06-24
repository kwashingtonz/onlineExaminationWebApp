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

import com.onlineExamApp.app.model.ExamQuestions;
import com.onlineExamApp.app.model.Search;
import com.onlineExamApp.app.service.ExamQuestionsService;
import com.onlineExamApp.app.util.PaginatorHelper;

@Controller
@RequestMapping("exam/questions")
public class ExamQuestionsController {
	
	@Autowired
	private ExamQuestionsService service;
	
	
	@RequestMapping(value= "", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(@ModelAttribute(value = "search") Search search,Model model, 
			@PageableDefault(value = PaginatorHelper.DEFAULT_PAGINATION_SIZE, page = 0) Pageable pageable) {
		List<ExamQuestions> questions = service.listSearched(search);
		Page<ExamQuestions> page=PaginatorHelper.pagiableList(questions, pageable);
		model.addAttribute("questions", questions);
		model.addAttribute("page", page);
		return "exam/questions/list";
	}
	
	@RequestMapping("/add")
	public String add(Model model) {
		ExamQuestions question = new ExamQuestions();
		
	    model.addAttribute("question",question);	  
		return "exam/questions/add";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("questions") ExamQuestions questions) {
	    service.save(questions);
	     
	    return "redirect:/exam/questions";
	}
	
	@RequestMapping("/edit/{id}")
	public String showEditQuestionPage(Model model,@PathVariable(name = "queId") int id) {
	    ExamQuestions questions = service.get(id);
	    
	    model.addAttribute("questions", questions);
	    
	    return "exam/questions/add";
	}
	
	@RequestMapping(value="/delete/{id}")
	public String delete(Model model,@PathVariable(name = "queId") int id) {
	    service.delete(id);
	    
	    return "redirect:/exam/questions";
	}
	
	@GetMapping("/403")
	public String error403() {
		return "403";
	}
}

