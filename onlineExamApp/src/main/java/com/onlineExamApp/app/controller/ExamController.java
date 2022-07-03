package com.onlineExamApp.app.controller;

import java.util.Collection;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.onlineExamApp.app.model.Exam;
import com.onlineExamApp.app.model.ExamQuestions;
import com.onlineExamApp.app.model.Search;
import com.onlineExamApp.app.model.SearchTeacher;
import com.onlineExamApp.app.service.CalculationService;
import com.onlineExamApp.app.service.ExamQuestionsService;
import com.onlineExamApp.app.service.ExamService;
import com.onlineExamApp.app.service.MyUserDetails;
import com.onlineExamApp.app.util.PaginatorHelper;

@Controller
@RequestMapping("/exam")
public class ExamController {
	
	@Autowired
	private ExamService service;
	
	@Autowired
	private ExamQuestionsService qservice;
	
	@Autowired
	private CalculationService cservice;
	
	@RequestMapping(value= "", method = { RequestMethod.GET, RequestMethod.POST })
	public String list(@ModelAttribute(value = "search") Search search,SearchTeacher searchName,SearchTeacher searchTeacher,Model model, 
			@PageableDefault(value = PaginatorHelper.DEFAULT_PAGINATION_SIZE, page = 0) Pageable pageable) {
		
		Collection<? extends GrantedAuthority> authorities;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        authorities = auth.getAuthorities();
        String myRole = authorities.toArray()[0].toString();
        
        List<Exam> exams;
        Page<Exam> page;
        if (myRole.equals("TEACHER")) {
    		exams = service.listTeacherSearched(searchName,searchTeacher);
        }else {       	
        	exams = service.listSearched(search);
        	
       }
        
        page=PaginatorHelper.pagiableList(exams, pageable);
		model.addAttribute("exams", exams);
		model.addAttribute("page", page);
		model.addAttribute("cservice", cservice);
		
        return "exam/list";	
	}
	
	@RequestMapping("/add")
	public String add(@AuthenticationPrincipal MyUserDetails user,@ModelAttribute(value = "search") Search search, Model model,
			@PageableDefault(value = PaginatorHelper.DEFAULT_PAGINATION_SIZE, page = 0) Pageable pageable) {
		Exam exam = new Exam();
		ExamQuestions questions = new ExamQuestions();
		int id = service.getLastIdAndNewId();
		List<ExamQuestions> question = qservice.listQuestionsAll(id);
		Page<ExamQuestions> page=PaginatorHelper.pagiableList(question, pageable);
		model.addAttribute("question", question);
		model.addAttribute("page", page);
		
		model.addAttribute("user", user);
	    model.addAttribute("exam", exam);
	    model.addAttribute("questions", questions);
	    model.addAttribute("service",service);
	    
		return "exam/add";
	}
	
	@RequestMapping(value = "/qsave", method = RequestMethod.POST)
	public String saveQuestion(@ModelAttribute("question") ExamQuestions question) {
		qservice.save(question);
		
		
		return "redirect:/exam/add";
	}
	
	@RequestMapping(value = "/editqsave", method = RequestMethod.POST)
	public String saveEditQuestion(@ModelAttribute("question") ExamQuestions question) {
		Integer exId = question.getExamId();
	    String eid = Integer.toString(exId);
		
		qservice.save(question);
		
		return  "redirect:/exam/edit/"+eid;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(@ModelAttribute("exam") Exam exam) {
	    service.save(exam);
	     
	    return "redirect:/exam";
	}
	
	@RequestMapping("/edit/{id}")
	public String showEditExamPage(@AuthenticationPrincipal MyUserDetails user,Model model, @PathVariable(name = "id") int id,
			@PageableDefault(value = PaginatorHelper.DEFAULT_PAGINATION_SIZE, page = 0) Pageable pageable) {

		List<ExamQuestions> question = qservice.listQuestionsAll(id);
		Page<ExamQuestions> page=PaginatorHelper.pagiableList(question, pageable);
		Exam exam = service.get(id);
		ExamQuestions questions = new ExamQuestions();
		
		
		model.addAttribute("question", question);
		model.addAttribute("page", page); 
	    

	    model.addAttribute("user", user);
	    model.addAttribute("exam", exam);
	    model.addAttribute("questions", questions);
	    
	    
		
	    return "exam/edit";
	}
	
	@RequestMapping("/enroll/{id}")
	public String enrollExam(@AuthenticationPrincipal MyUserDetails user,Model model, @PathVariable(name = "id") int id) {

		List<ExamQuestions> question = qservice.listQuestionsAll(id);
		Exam exam = service.get(id);
		
		model.addAttribute("question", question);

	    model.addAttribute("user", user);
	    model.addAttribute("exam", exam);
	    
		
	    return "exam/enroll";
	}
	
	@RequestMapping("/enroll/{id}/result")
	public String resultExam(@AuthenticationPrincipal MyUserDetails user,Model model, @PathVariable(name = "id") int id) {

		List<ExamQuestions> question = qservice.listQuestionsAll(id);
		Exam exam = service.get(id);
		
		model.addAttribute("question", question);

	    model.addAttribute("user", user);
	    model.addAttribute("exam", exam);
	    
		
	    return "exam/result";
	}
	
	@RequestMapping(value="/delete/{id}")
	public String delete(Model model,@PathVariable(name = "id") int id) {
	    service.delete(id);
	    
	    return "redirect:/exam";
	}
	
	@RequestMapping("/questions/adddelete/{id}")
	public String deleteAddQuestion(Model model,@PathVariable(name = "id") int id) {
		qservice.delete(id);
	    
	    return "redirect:/exam/add/";
	}
	
	@RequestMapping("/questions/editdelete/{id}")
	public String deleteEditQuestion(Model model,@PathVariable(name = "id") int id) {
	    Integer exId = qservice.get(id).getExamId();
	    String eid = Integer.toString(exId);
		qservice.delete(id);
	    
	    return "redirect:/exam/edit/"+eid;
	}
	
	@RequestMapping("/status/{id}")
	public String showExamStatus(Model model,@PathVariable(name = "id") int id) {
		Exam exam = service.get(id);
		
		model.addAttribute("exam", exam);
		
		return "exam/status";
	}
	
	@GetMapping("/403")
	public String error403() {
		return "403";
	}
}
